package springboot.get_a_job.serviceImplementations;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import springboot.get_a_job.dao.*;
import springboot.get_a_job.dto.EducationDto;
import springboot.get_a_job.exceptions.EducationInfoNotFoundException;
import springboot.get_a_job.exceptions.ResumeNotFoundException;
import springboot.get_a_job.services.EducationInfoService;
import springboot.get_a_job.services.ResumeService;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class EducationInfoServiceImpl implements EducationInfoService {
    @Autowired
    @Lazy
    private ResumeService resumeService;
    private final EducationInfoDao educationDao;

    @Override
    public void addEducationInfo(Integer resumeId, List<EducationDto> educationDtos) {
        if (resumeService.findResumeById(resumeId).isEmpty()) {
            throw new ResumeNotFoundException("Resume with id: " + resumeId + " not found");
        }

        if (educationDtos != null  || !educationDtos.isEmpty()) {
            for (EducationDto edu : educationDtos){
                educationDao.addEducationInfo(edu, resumeId);
                log.info("Server Successfully created new Education Info for Resume(ID: {}) added", resumeId);
            }
        }

    }

    @Override
    public void updateOrAddEducationInfo(Integer resumeId, List<EducationDto> educationDtos) {
        if (educationDtos != null || !educationDtos.isEmpty()) {
            if (getResumesEducationInfo(resumeId).isEmpty() || getResumesEducationInfo(resumeId) == null) {
                log.debug("No Education Info Was Found to Update, Saving new record for Resume(ID): {}", resumeId);
                addEducationInfo(resumeId, educationDtos);
            } else {
                updateResumesEducationInfo(educationDtos);
            }
        } else {
            addEducationInfo(resumeId, educationDtos);
        }
    }


    @Override
    public void updateResumesEducationInfo(List<EducationDto> educationDto) {
        if (educationDto == null || educationDto.isEmpty()) {
            log.debug("No education dto to update");
            return;
        }
        for (EducationDto edu : educationDto){
            educationDao.updateEducationInfo(checkedEducation(edu), edu.getId());
            log.info("Server Successfully updated Education Info(ID: {})", edu.getId());
        }
    }

    @Override
    public void deleteEducationInfo(Integer resumeId) {
        if (educationDao.getResumesEducationInfo(resumeId) == null){
            throw new EducationInfoNotFoundException("Education info for Resume " + resumeId + " not found");
        }
        educationDao.deleteEducationInfo(resumeId);
        log.info("Server Successfully deleted Education Info of Resume(ID: {})", resumeId);
    }

    @Override
    public List<EducationDto> getResumesEducationInfo(Integer resumeId) {
        return educationDao.getResumesEducationInfo(resumeId);
    }

    private EducationDto checkedEducation(EducationDto newEducation) {
        // 1. Проверка на null самого объекта и его ID
        if (newEducation == null || newEducation.getId() == null) {
            throw new IllegalArgumentException("Education ID must not be null for update");
        }

        // 2. Получаем старые данные ОДИН раз (не вызывайте метод дважды для производительности)
        EducationDto oldEducation = educationDao.findInfoById(newEducation.getId());

        if (oldEducation == null) {
            throw new EducationInfoNotFoundException("Education info with id: " + newEducation.getId() + " not found");
        }

        EducationDto result = new EducationDto();
        // Сохраняем ID в новом объекте
        result.setId(newEducation.getId());

        // Используем тернарные операторы для краткости
        result.setInstitution(isBlank(newEducation.getInstitution()) ? oldEducation.getInstitution() : newEducation.getInstitution());
        result.setProgram(isBlank(newEducation.getProgram()) ? oldEducation.getProgram() : newEducation.getProgram());
        result.setStartDate(newEducation.getStartDate() == null ? oldEducation.getStartDate() : newEducation.getStartDate());
        result.setEndDate(newEducation.getEndDate() == null ? oldEducation.getEndDate() : newEducation.getEndDate());
        result.setDegree(isBlank(newEducation.getDegree()) ? oldEducation.getDegree() : newEducation.getDegree());

        return result;
    }

    // Вспомогательный метод (или используйте StringUtils)
    private boolean isBlank(String s) {
        return s == null || s.trim().isEmpty();
    }



}
