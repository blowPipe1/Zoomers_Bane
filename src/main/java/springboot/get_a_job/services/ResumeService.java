package springboot.get_a_job.services;

import springboot.get_a_job.dto.EducationDto;
import springboot.get_a_job.dto.ResumeDto;
import springboot.get_a_job.dto.WorkExperienceDto;
import springboot.get_a_job.models.Resume;

import java.util.List;
import java.util.Optional;

public interface ResumeService {
    void updateResume(Integer id, ResumeDto resumeDto);
    void deleteResume(Integer id);
    Optional<List<ResumeDto>>getAllActiveResumes();
    Optional<ResumeDto>findResumeById(Integer id);
    Optional<List<ResumeDto>> findResumeByCategory(Integer category_id);
    Optional<List<ResumeDto>> findResumeByCategory(String category);
    Optional<List<ResumeDto>> findResumeByCreator(Integer applicant_id);
    Optional<List<ResumeDto>> findResumeByCreator(String creatorName);
    void createResume(ResumeDto resumeDto);
    void addEducationInfo(Integer resumeId, List<EducationDto> educationDtos);
    void updateResumesEducationInfo(Integer educationID, EducationDto educationDto);
    void addWorkExperienceInfo(Integer resumeId, List<WorkExperienceDto> workExperienceDtos);
    void updateResumesWorkExperienceInfo(Integer workExpId, WorkExperienceDto workExperienceDto);
}