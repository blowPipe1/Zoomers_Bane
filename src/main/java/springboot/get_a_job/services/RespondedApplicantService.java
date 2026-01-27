package springboot.get_a_job.services;

import springboot.get_a_job.dto.ApplicantResponseDto;
import springboot.get_a_job.models.RespondedApplicant;

import java.util.List;

public interface RespondedApplicantService {
    List<RespondedApplicant> getApplicantByResumeId(Integer resumeId);
    List<RespondedApplicant> getApplicantByVacancy_Id(Integer vacancyId);
    void applyToVacancy(ApplicantResponseDto dto);
    List<RespondedApplicant> getApplicationsForUser(Integer userId);
    List<RespondedApplicant> getApplicationsForEmployer(Integer userId);
}
