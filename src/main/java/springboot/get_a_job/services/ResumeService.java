package springboot.get_a_job.services;

import springboot.get_a_job.dto.ResumeDto;


import java.util.List;
import java.util.Optional;

public interface ResumeService {
    void updateResume(Integer id, ResumeDto resumeDto);
    void deleteResume(Integer id);
    Optional<List<ResumeDto>>getAllActiveResumes();
    Optional<ResumeDto>findResumeById(Integer id);
    Optional<List<ResumeDto>> findResumeByCreator(Integer applicant_id);
    void createResume(ResumeDto resumeDto);
}