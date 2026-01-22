package springboot.get_a_job.serviceImplementations;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import springboot.get_a_job.dto.EducationDto;
import springboot.get_a_job.exceptions.EducationInfoNotFoundException;
import springboot.get_a_job.exceptions.ResumeNotFoundException;
import springboot.get_a_job.models.EducationInfo;
import springboot.get_a_job.models.Resume;
import springboot.get_a_job.repositories.EducationInfoRepository;
import springboot.get_a_job.repositories.ResumeRepository;
import springboot.get_a_job.services.EducationInfoService;


import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class EducationInfoServiceImpl implements EducationInfoService {

    private final EducationInfoRepository educationRepository;
    private final ResumeRepository resumeRepository;

    @Override
    @Transactional
    public void addEducationInfo(Integer resumeId, List<EducationDto> educationDtos) {
        if (educationDtos == null || educationDtos.isEmpty()) return;

        Resume resume = resumeRepository.findById(resumeId)
                .orElseThrow(() -> new ResumeNotFoundException("Resume with id: " + resumeId + " not found"));

        for (EducationDto dto : educationDtos) {
            EducationInfo edu = new EducationInfo();
            edu.setResume(resume);
            mapDtoToEntity(dto, edu);
            educationRepository.save(edu);
        }
        log.info("Successfully added {} education records for Resume ID: {}", educationDtos.size(), resumeId);
    }

    @Override
    @Transactional
    public void updateOrAddEducationInfo(Integer resumeId, List<EducationDto> dtos) {
        if (dtos == null || dtos.isEmpty()) return;
        for (EducationDto dto : dtos) {
            if (dto.getId() != null && dto.getId() != 0) {
                educationRepository.findById(dto.getId()).ifPresentOrElse(
                        entity -> {
                            mapDtoToEntity(dto, entity);
                            log.info("Updated Education Info ID: {}", dto.getId());
                        },
                        () -> addSingleEducation(resumeId, dto)
                );
            } else {
                addSingleEducation(resumeId, dto);
            }
        }
    }

    @Override
    @Transactional
    public void updateResumesEducationInfo(List<EducationDto> educationDtos) {
        if (educationDtos == null || educationDtos.isEmpty()) return;

        for (EducationDto dto : educationDtos) {
            EducationInfo entity = educationRepository.findById(dto.getId())
                    .orElseThrow(() -> new EducationInfoNotFoundException("Education ID " + dto.getId() + " not found"));

            mapDtoToEntity(dto, entity);
            log.info("Successfully updated Education Info ID: {}", dto.getId());
        }
    }

    @Override
    @Transactional
    public void deleteEducationInfo(Integer resumeId) {
        if (educationRepository.findByResumeId(resumeId).isEmpty()) {
            throw new EducationInfoNotFoundException("No education info for Resume ID " + resumeId);
        }
        educationRepository.deleteByResumeId(resumeId);
        log.info("Deleted all Education Info for Resume ID: {}", resumeId);
    }

    @Override
    public List<EducationDto> getResumesEducationInfo(Integer resumeId) {
        return educationRepository.findByResumeId(resumeId).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    private void addSingleEducation(Integer resumeId, EducationDto dto) {
        Resume resume = resumeRepository.findById(resumeId).orElseThrow();
        EducationInfo edu = new EducationInfo();
        edu.setResume(resume);
        mapDtoToEntity(dto, edu);
        educationRepository.save(edu);
    }

    private void mapDtoToEntity(EducationDto dto, EducationInfo entity) {
        if (dto.getInstitution() != null) entity.setInstitution(dto.getInstitution());
        if (dto.getProgram() != null) entity.setProgram(dto.getProgram());
        if (dto.getStartDate() != null) entity.setStartDate(dto.getStartDate());
        if (dto.getEndDate() != null) entity.setEndDate(dto.getEndDate());
        if (dto.getDegree() != null) entity.setDegree(dto.getDegree());
    }

    private EducationDto convertToDto(EducationInfo entity) {
        EducationDto dto = new EducationDto();
        dto.setId(entity.getId());
        dto.setInstitution(entity.getInstitution());
        dto.setProgram(entity.getProgram());
        dto.setStartDate(entity.getStartDate());
        dto.setEndDate(entity.getEndDate());
        dto.setDegree(entity.getDegree());
        return dto;
    }
}
