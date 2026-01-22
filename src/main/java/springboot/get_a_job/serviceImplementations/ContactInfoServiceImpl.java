package springboot.get_a_job.serviceImplementations;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import springboot.get_a_job.dto.ContactInfoDto;
import springboot.get_a_job.exceptions.ContactInfoNotFoundException;
import springboot.get_a_job.exceptions.ResumeNotFoundException;
import springboot.get_a_job.models.ContactInfo;
import springboot.get_a_job.models.ContactType;
import springboot.get_a_job.models.Resume;
import springboot.get_a_job.repositories.ContactInfoRepository;
import springboot.get_a_job.services.ContactInfoService;
import springboot.get_a_job.services.ContactTypeService;
import springboot.get_a_job.services.ResumeService;


import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ContactInfoServiceImpl implements ContactInfoService {
    private final ContactInfoRepository contactInfoRepository;
    @Autowired
    @Lazy
    private ContactTypeService contactTypeService;
    @Autowired
    @Lazy
    private ResumeService resumeService;

    @Override
    @Transactional
    public void addContactInfo(Integer resumeId, List<ContactInfoDto> contacts) {
        if (contacts == null || contacts.isEmpty()) return;

        Resume resume = resumeService.findById(resumeId)
                .orElseThrow(() -> new ResumeNotFoundException("Resume with id: " + resumeId + " not found"));

        for (ContactInfoDto dto : contacts) {
            ContactType type = contactTypeService.findByTypeIgnoreCase(dto.getType())
                    .orElseThrow(() -> new ContactInfoNotFoundException("Type " + dto.getType() + " not found"));

            ContactInfo entity = new ContactInfo();
            entity.setResume(resume);
            entity.setType(type);
            entity.setValue(dto.getValue());

            contactInfoRepository.save(entity);
        }
    }

    @Override
    @Transactional
    public void updateContactInfo(List<ContactInfoDto> contactInfoDtos) {
        if (contactInfoDtos == null || contactInfoDtos.isEmpty()) return;

        for (ContactInfoDto dto : contactInfoDtos) {
            contactInfoRepository.findById(dto.getId()).ifPresentOrElse(entity -> {
                if (dto.getValue() != null) entity.setValue(dto.getValue());

                if (dto.getType() != null) {
                    ContactType type = contactTypeService.findByTypeIgnoreCase(dto.getType())
                            .orElseThrow(() -> new ContactInfoNotFoundException("Type not found"));
                    entity.setType(type);
                }
            }, () -> {
                throw new ContactInfoNotFoundException("Contact with id " + dto.getId() + " not found");
            });
        }
    }

    @Override
    @Transactional
    public void deleteContactInfo(Integer resumeId) {
        contactInfoRepository.deleteByResumeId(resumeId);
    }

    @Override
    public List<ContactInfoDto> getResumesContacts(Integer resumeId) {
        return contactInfoRepository.findByResumeId(resumeId).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<String> findAll() {
        return contactTypeService.findAll().stream()
                .map(ContactType::getType)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void updateOrAddContactInfo(Integer resumeId, List<ContactInfoDto> contacts) {
        if (contacts == null || contacts.isEmpty()) {
            log.debug("No contacts provided for Resume(ID): {}", resumeId);
            return;
        }

        Resume resume = resumeService.findById(resumeId)
                .orElseThrow(() -> new ResumeNotFoundException("Resume not found: " + resumeId));

        for (ContactInfoDto dto : contacts) {
            ContactInfo entity = (dto.getId() != null && dto.getId() != 0)
                    ? contactInfoRepository.findById(dto.getId()).orElse(new ContactInfo())
                    : new ContactInfo();

            entity.setResume(resume);
            entity.setValue(dto.getValue());

            ContactType type = contactTypeService.findByTypeIgnoreCase(dto.getType())
                    .orElseThrow(() -> new ContactInfoNotFoundException("Type " + dto.getType() + " not found"));
            entity.setType(type);

            contactInfoRepository.save(entity);
            log.info("Processed contact: {} (id: {}) for Resume: {}", dto.getType(), entity.getId(), resumeId);
        }
    }

    private ContactInfoDto convertToDto(ContactInfo entity) {
        ContactInfoDto dto = new ContactInfoDto();
        dto.setId(entity.getId());
        dto.setType(entity.getType().getType());
        dto.setValue(entity.getValue());
        dto.setResume(entity.getResume().getName());
        return dto;
    }
}
