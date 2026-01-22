package springboot.get_a_job.serviceImplementations;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import springboot.get_a_job.dto.WorkExperienceDto;
import springboot.get_a_job.exceptions.ResumeNotFoundException;
import springboot.get_a_job.exceptions.WorkExpNotFoundException;
import springboot.get_a_job.models.Resume;
import springboot.get_a_job.models.WorkExperienceInfo;
import springboot.get_a_job.repositories.ResumeRepository;
import springboot.get_a_job.repositories.WorkExperienceInfoRepository;

import springboot.get_a_job.services.ResumeService;
import springboot.get_a_job.services.WorkExperienceService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class WorkExperienceServiceImpl implements WorkExperienceService {

    private final WorkExperienceInfoRepository workExperienceRepository;
    private final ResumeService resumeService;

    @Override
    @Transactional
    public void addWorkExperienceInfo(Integer resumeId, List<WorkExperienceDto> dtos) {
        if (dtos == null || dtos.isEmpty()) return;

        Resume resume = resumeService.findById(resumeId)
                .orElseThrow(() -> new ResumeNotFoundException("Resume with id: " + resumeId + " not found"));

        for (WorkExperienceDto dto : dtos) {
            WorkExperienceInfo info = new WorkExperienceInfo();
            info.setResume(resume);
            mapDtoToEntity(dto, info);
            workExperienceRepository.save(info);
        }
        log.info("Successfully added {} work experience records for Resume ID: {}", dtos.size(), resumeId);
    }

    @Override
    @Transactional
    public void updateOrAddWorkExp(Integer resumeId, List<WorkExperienceDto> dtos) {
        if (dtos == null || dtos.isEmpty()) return;

        for (WorkExperienceDto dto : dtos) {
            if (dto.getId() != null && dto.getId() != 0) {
                workExperienceRepository.findById(dto.getId()).ifPresentOrElse(
                        entity -> {
                            mapDtoToEntity(dto, entity);
                            log.info("Updated Work Experience ID: {}", dto.getId());
                        },
                        () -> addSingleWorkExp(resumeId, dto)
                );
            } else {
                addSingleWorkExp(resumeId, dto);
            }
        }
    }

    @Override
    @Transactional
    public void updateResumesWorkExperienceInfo(List<WorkExperienceDto> dtos) {
        if (dtos == null || dtos.isEmpty()) return;

        for (WorkExperienceDto dto : dtos) {
            WorkExperienceInfo entity = workExperienceRepository.findById(dto.getId())
                    .orElseThrow(() -> new WorkExpNotFoundException("Work Experience ID " + dto.getId() + " not found"));

            mapDtoToEntity(dto, entity);
            log.info("Successfully updated Work Experience ID: {}", dto.getId());
        }
    }

    @Override
    @Transactional
    public void deleteWorkExperienceInfo(Integer resumeId) {
        List<WorkExperienceInfo> info = workExperienceRepository.findAllByResumeId(resumeId);
        if (info.isEmpty()) {
            throw new WorkExpNotFoundException("Work Experience for Resume ID " + resumeId + " not found");
        }
        workExperienceRepository.deleteAllByResumeId(resumeId);
        log.info("Deleted all Work Experience for Resume ID: {}", resumeId);
    }

    @Override
    public List<WorkExperienceDto> getResumesWorkExperience(Integer resumeId) {
        return workExperienceRepository.findAllByResumeId(resumeId).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }


    private void addSingleWorkExp(Integer resumeId, WorkExperienceDto dto) {
        Resume resume = resumeService.findById(resumeId)
                .orElseThrow(() -> new ResumeNotFoundException("Resume ID " + resumeId + " not found"));
        WorkExperienceInfo info = new WorkExperienceInfo();
        info.setResume(resume);
        mapDtoToEntity(dto, info);
        workExperienceRepository.save(info);
    }

    private void mapDtoToEntity(WorkExperienceDto dto, WorkExperienceInfo entity) {
        if (dto.getYears() != 0) entity.setYears(dto.getYears());
        if (isNotBlank(dto.getCompanyName())) entity.setCompanyName(dto.getCompanyName());
        if (isNotBlank(dto.getPosition())) entity.setPosition(dto.getPosition());
        if (isNotBlank(dto.getResponsibilities())) entity.setResponsibilities(dto.getResponsibilities());
    }

    private WorkExperienceDto convertToDto(WorkExperienceInfo entity) {
        WorkExperienceDto dto = new WorkExperienceDto();
        dto.setId(entity.getId());
        dto.setYears(entity.getYears());
        dto.setCompanyName(entity.getCompanyName());
        dto.setPosition(entity.getPosition());
        dto.setResponsibilities(entity.getResponsibilities());
        return dto;
    }

    private boolean isNotBlank(String s) {
        return s != null && !s.trim().isEmpty();
    }
}
