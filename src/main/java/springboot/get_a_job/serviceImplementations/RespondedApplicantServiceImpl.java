package springboot.get_a_job.serviceImplementations;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import springboot.get_a_job.models.RespondedApplicant;
import springboot.get_a_job.repositories.RespondedApplicantRepository;
import springboot.get_a_job.services.RespondedApplicantService;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RespondedApplicantServiceImpl implements RespondedApplicantService {
    private final RespondedApplicantRepository respondedApplicantRepository;

    @Override
    public List<RespondedApplicant> getApplicantByResumeId(Integer resumeId) {
        return respondedApplicantRepository.findByResumeId(resumeId);
    }

    @Override
    public List<RespondedApplicant> getApplicantByVacancy_Id(Integer vacancyId){
        return respondedApplicantRepository.findByVacancy_Id(vacancyId);
    }
}
