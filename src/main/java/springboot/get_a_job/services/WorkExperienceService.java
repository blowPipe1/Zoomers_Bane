package springboot.get_a_job.services;

import springboot.get_a_job.dto.WorkExperienceDto;

import java.util.List;

public interface WorkExperienceService {
    void addWorkExperienceInfo(Integer resumeId, List<WorkExperienceDto> workExperienceDtos);
    void updateResumesWorkExperienceInfo(Integer workExpId, WorkExperienceDto workExperienceDto);
}
