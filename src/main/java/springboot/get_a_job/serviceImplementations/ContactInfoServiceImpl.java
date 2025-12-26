package springboot.get_a_job.serviceImplementations;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import springboot.get_a_job.dao.*;
import springboot.get_a_job.dto.ContactInfoDto;
import springboot.get_a_job.exceptions.ContactInfoNotFoundException;
import springboot.get_a_job.exceptions.EducationInfoNotFoundException;
import springboot.get_a_job.exceptions.ResumeNotFoundException;
import springboot.get_a_job.models.ContactInfo;
import springboot.get_a_job.services.ContactInfoService;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ContactInfoServiceImpl implements ContactInfoService {
    private final ResumeDao resumeDao;
    private final ContactInfoDao contactInfoDao;

    @Override
    public void addContactInfo(Integer resumeId, List<ContactInfoDto> contacts){
        if (resumeDao.findResumeById(resumeId) == null) {
            throw new ResumeNotFoundException("Resume with id: " + resumeId + " not found");
        }
        if (contacts == null || contacts.isEmpty()) {
            log.warn("No Contact info dto to add");
            return;
        }
        for (ContactInfoDto contact : contacts){
            contactInfoDao.addContactInfo(new ContactInfo(
                    0, contactInfoDao.findIdByName(contact.getType()), resumeId, contact.getValue()
            ), resumeId);
            log.info("Server Successfully added contact info({} {}) for Resume(ID: {})", contact.getType(), contact.getValue(), resumeId);
        }
    }

    @Override
    public void updateContactInfo(List<ContactInfoDto> contactInfo){
        if (contactInfo == null || contactInfo.isEmpty()) {
            log.warn("No Contact info dto to update");
            return;
        }
        for (ContactInfoDto contact : contactInfo){
            if (contactInfoDao.findIdByName(contact.getType()) == null ) {
                throw new ContactInfoNotFoundException("Contact info with type: " + contact.getType() + " not found");
            }
            contactInfoDao.updateContactInfo(convert(checkedContact(contact)), contact.getId());
            log.info("Server Successfully updated contact info(ID: {})",  contact.getId());
        }
    }

    @Override
    public  void deleteContactInfo(Integer resumeId) {
        if (contactInfoDao.getResumesContacts(resumeId) == null){
            throw new EducationInfoNotFoundException("Contact info " + resumeId + " not found");
        }
        contactInfoDao.deleteContactInfo(resumeId);
        log.info("Server Successfully deleted Contact Info of Resume(ID: {})", resumeId);
    }

    @Override
    public List<ContactInfoDto>getResumesContacts(Integer resumeId){
        return contactInfoDao.getResumesContacts(resumeId);
    }

    private ContactInfoDto checkedContact(ContactInfoDto newContactInfo) {
        ContactInfo old = contactInfoDao.findInfoById(newContactInfo.getId());

        if (old == null) {
            throw new ContactInfoNotFoundException("Contact info with id: " + newContactInfo.getId() + " not found");
        }

        ContactInfoDto result = new ContactInfoDto();
        result.setId(0);

        result.setType(isInvalid(newContactInfo.getType())
                ? contactInfoDao.findNameById(old.getTypeId())
                : newContactInfo.getType());

        result.setResume(isInvalid(newContactInfo.getResume())
                ? resumeDao.findResumeNameById(old.getResumeId())
                : newContactInfo.getResume());

        result.setValue(isInvalid(newContactInfo.getValue())
                ? old.getValue()
                : newContactInfo.getValue());

        return result;
    }

    private boolean isInvalid(String str) {
        return str == null || str.isEmpty();
    }

    private ContactInfo convert(ContactInfoDto contactInfo){
        return new ContactInfo(
                contactInfo.getId(),
                contactInfoDao.findIdByName(contactInfo.getType()),
                resumeDao.findResumeIdByName(contactInfo.getResume()),
                contactInfo.getValue()
        );
    }
}
