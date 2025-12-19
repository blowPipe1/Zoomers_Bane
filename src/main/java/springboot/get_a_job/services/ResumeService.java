package springboot.get_a_job.services;

import springboot.get_a_job.dto.ResumeDto;
import springboot.get_a_job.models.Resume;

import java.util.List;
import java.util.Optional;

public interface ResumeService {
    void updateResume(Integer id, ResumeDto resumeDto);
    void deleteResume(Integer id);
    Optional<List<Resume>>getAllActiveResumes();
    Optional<Resume>findResumeById(Integer id);
    Optional<List<Resume>> findResumeByCategory(Integer category_id);
    Optional<List<Resume>> findResumeByCategory(String category);
    Optional<List<Resume>> findResumeByCreator(Integer applicant_id);
    Optional<List<Resume>> findResumeByCreator(String creatorName);
    void createResume(ResumeDto resumeDto);
}
