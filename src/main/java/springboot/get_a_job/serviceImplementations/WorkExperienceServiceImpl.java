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
    public void updateResumesWorkExperienceInfo(Integer workExpID, WorkExperienceDto workExperienceDto) {
        if (workExperienceDao.findInfoById(workExpID) == null) {
            throw new WorkExpNotFoundException("WorkExperience with id: " + workExpID + " not found");
        }
        workExperienceDao.updateWorkExperience(workExperienceDto, workExpID);
        log.info("Updated Work Experience(ID: {})", workExpID);
    }
}
