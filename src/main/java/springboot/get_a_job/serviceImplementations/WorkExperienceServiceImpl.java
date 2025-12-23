package springboot.get_a_job.serviceImplementations;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import springboot.get_a_job.dao.*;
import springboot.get_a_job.dto.WorkExperienceDto;
import springboot.get_a_job.exceptions.ResourceNotFoundException;
import springboot.get_a_job.exceptions.ResumeNotFoundException;
import springboot.get_a_job.services.WorkExperienceService;

import java.util.List;

@Service
@RequiredArgsConstructor
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
        }

    }

    @Override
    public void updateResumesWorkExperienceInfo(Integer workExpID, WorkExperienceDto workExperienceDto) {
        if (workExperienceDao.findInfoById(workExpID) == null) {
            throw new ResourceNotFoundException("WorkExperience with id: " + workExpID + " not found");
        }
        workExperienceDao.updateWorkExperience(workExperienceDto, workExpID);
    }
}
