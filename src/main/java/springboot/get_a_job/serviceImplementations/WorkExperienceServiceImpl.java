package springboot.get_a_job.serviceImplementations;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import springboot.get_a_job.dao.*;
import springboot.get_a_job.dto.WorkExperienceDto;
import springboot.get_a_job.exceptions.ResumeNotFoundException;
import springboot.get_a_job.exceptions.WorkExpNotFoundException;
import springboot.get_a_job.services.WorkExperienceService;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class WorkExperienceServiceImpl implements WorkExperienceService {
    private final ResumeDao resumeDao;
    private final WorkExperienceDao workExperienceDao;

    @Override
    public void addWorkExperienceInfo(Integer resumeId, List<WorkExperienceDto> workExperienceDtos) {
        if (resumeDao.findResumeById(resumeId) == null) {
            throw new ResumeNotFoundException("Resume with id: " + resumeId + " not found");
        }
        for (WorkExperienceDto workExp : workExperienceDtos){
            workExperienceDao.addWorkExperience(workExp, resumeId);
            log.info("Added work experience(ID {}) for Resume(ID: {})", workExp.getId(), resumeId);
        }
    }

    @Override
    public void updateResumesWorkExperienceInfo(List<WorkExperienceDto> workExperienceDto) {
        if (workExperienceDto == null || workExperienceDto.isEmpty()) {
            return;
        }
        for (WorkExperienceDto workExp : workExperienceDto) {
            workExperienceDao.updateWorkExperience(checkedWorkExp(workExp), workExp.getId());
            log.info("Updated Work Experience(ID: {})", workExp.getId());
        }

    }

    private WorkExperienceDto checkedWorkExp(WorkExperienceDto newWorkExp) {
        if (workExperienceDao.findInfoById(newWorkExp.getId()) == null) {
            throw new WorkExpNotFoundException("Work Experience with id: " + newWorkExp.getId() + " not found");
        }
        WorkExperienceDto oldWorkExp = workExperienceDao.findInfoById(newWorkExp.getId());
        WorkExperienceDto result = new WorkExperienceDto();

        if (newWorkExp.getYears() == 0){
            result.setYears(oldWorkExp.getYears());
        } else {
            result.setYears(newWorkExp.getYears());
        }
        if (newWorkExp.getCompanyName() == null || newWorkExp.getCompanyName().isEmpty()) {
            result.setCompanyName(oldWorkExp.getCompanyName());
        } else {
            result.setCompanyName(newWorkExp.getCompanyName());
        }
        if (newWorkExp.getPosition() == null || newWorkExp.getPosition().isEmpty()) {
            result.setPosition(oldWorkExp.getPosition());
        } else {
            result.setPosition(newWorkExp.getPosition());
        }
        if (newWorkExp.getResponsibilities() == null || newWorkExp.getResponsibilities().isEmpty()) {
            result.setResponsibilities(oldWorkExp.getResponsibilities());
        } else {
            result.setResponsibilities(newWorkExp.getResponsibilities());
        }
        return result;
    }
}
