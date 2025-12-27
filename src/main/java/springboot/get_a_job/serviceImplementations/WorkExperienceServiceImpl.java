package springboot.get_a_job.serviceImplementations;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import springboot.get_a_job.dao.*;
import springboot.get_a_job.dto.EducationDto;
import springboot.get_a_job.dto.WorkExperienceDto;
import springboot.get_a_job.exceptions.EducationInfoNotFoundException;
import springboot.get_a_job.exceptions.ResumeNotFoundException;
import springboot.get_a_job.exceptions.WorkExpNotFoundException;
import springboot.get_a_job.services.ResumeService;
import springboot.get_a_job.services.WorkExperienceService;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class WorkExperienceServiceImpl implements WorkExperienceService {
    private final ResumeService resumeService;
    private final WorkExperienceDao workExperienceDao;

    @Override
    public void addWorkExperienceInfo(Integer resumeId, List<WorkExperienceDto> workExperienceDtos) {
        if (resumeService.findResumeById(resumeId).isEmpty()) {
            throw new ResumeNotFoundException("Resume with id: " + resumeId + " not found");
        }
        for (WorkExperienceDto workExp : workExperienceDtos){
            workExperienceDao.addWorkExperience(workExp, resumeId);
            log.info("Server Successfully added Work Experience(ID {}) for Resume(ID: {})", workExp.getId(), resumeId);
        }
    }

    @Override
    public void updateOrAddWorkExp(Integer resumeId, List<WorkExperienceDto> workExperienceDtos) {
        if (workExperienceDtos != null || !workExperienceDtos.isEmpty()) {
            if (getResumesWorkExperience(resumeId).isEmpty() || getResumesWorkExperience(resumeId) == null) {
                log.debug("No Work Experience Info Was Found to Update, Saving new record for Resume(ID): {}", resumeId);
                addWorkExperienceInfo(resumeId, workExperienceDtos);
            } else {
                updateResumesWorkExperienceInfo(workExperienceDtos);
            }
        } else {
            addWorkExperienceInfo(resumeId, workExperienceDtos);
        }
    }

    @Override
    public void updateResumesWorkExperienceInfo(List<WorkExperienceDto> workExperienceDto) {
        if (workExperienceDto == null || workExperienceDto.isEmpty()) {
            log.debug("No Work Experience dto to update");
            return;
        }
        for (WorkExperienceDto workExp : workExperienceDto) {
            workExperienceDao.updateWorkExperience(checkedWorkExp(workExp), workExp.getId());
            log.info("Server Successfully updated Work Experience(ID: {})", workExp.getId());
        }
    }

    @Override
    public void deleteWorkExperienceInfo(Integer resumeId) {
        if (workExperienceDao.getResumesWorkExperience(resumeId) == null){
            throw new WorkExpNotFoundException("Work Experience " + resumeId + " not found");
        }
        workExperienceDao.deleteWorkExperienceInfo(resumeId);
        log.info("Server Successfully deleted Work Experience of Resume(ID: {})", resumeId);
    }

    @Override
    public List<WorkExperienceDto> getResumesWorkExperience(Integer resumeId) {
        return workExperienceDao.getResumesWorkExperience(resumeId);
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
