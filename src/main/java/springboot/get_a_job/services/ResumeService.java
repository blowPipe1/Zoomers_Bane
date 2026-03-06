package springboot.get_a_job.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import springboot.get_a_job.dto.ResumeDto;
import springboot.get_a_job.models.Resume;

import java.util.List;
import java.util.Optional;

public interface ResumeService {
    void updateResume(Integer id, ResumeDto resumeDto);
    void deleteResume(Integer id);
    Page<ResumeDto> getAllActiveResumes(Pageable pageable, String name, String category);
    Optional<ResumeDto>findResumeById(Integer id);
    Page<ResumeDto>findResumeByCreator(Integer applicant_id, Pageable pageable);
    Page<ResumeDto> findActiveResumesByCreator(Integer applicantId, Pageable pageable);
    void createResume(ResumeDto resumeDto);
    Optional<Resume>findById(Integer id);
    List<ResumeDto> findAllByApplicantId(Integer applicantId);
    Resume convertIntoModel(ResumeDto dto);
    void refreshResume(Integer resumeId);
}