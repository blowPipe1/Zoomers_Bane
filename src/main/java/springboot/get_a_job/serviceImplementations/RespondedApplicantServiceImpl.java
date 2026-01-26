package springboot.get_a_job.serviceImplementations;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import springboot.get_a_job.dto.ApplicantResponseDto;
import springboot.get_a_job.dto.ResumeDto;
import springboot.get_a_job.dto.VacancyDto;
import springboot.get_a_job.models.Message;
import springboot.get_a_job.models.RespondedApplicant;
import springboot.get_a_job.models.Vacancy;
import springboot.get_a_job.repositories.MessageRepository;
import springboot.get_a_job.repositories.RespondedApplicantRepository;
import springboot.get_a_job.services.RespondedApplicantService;
import springboot.get_a_job.services.ResumeService;
import springboot.get_a_job.services.VacancyService;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RespondedApplicantServiceImpl implements RespondedApplicantService {
    private final RespondedApplicantRepository respondedApplicantRepository;
    private final MessageRepository messageRepository;
    private final VacancyService vacancyService;
    private final ResumeService resumeService;

    @Override
    @Transactional
    public void applyToVacancy(ApplicantResponseDto dto) {
        RespondedApplicant application = new RespondedApplicant();


        VacancyDto vacancyDto = vacancyService.findVacancyById(dto.getVacancyId()).orElseGet(null);
        ResumeDto resumeDto = resumeService.findResumeById(dto.getResumeId()).orElseGet(null);

        application.setVacancy(vacancyService.convertIntoModel(vacancyDto));
        application.setResume(resumeService.convertIntoModel(resumeDto));
        application.setConfirmation(false);

        RespondedApplicant savedApp = respondedApplicantRepository.save(application);

        if (dto.getMessage() != null && !dto.getMessage().isEmpty()) {
            Message message = new Message();
            message.setApplication(savedApp);
            message.setContent(dto.getMessage());
            message.setTimestamp(LocalDateTime.now());
            messageRepository.save(message);
        }
    }

    @Override
    public List<RespondedApplicant> getApplicantByResumeId(Integer resumeId) {
        return respondedApplicantRepository.findByResumeId(resumeId);
    }

    @Override
    public List<RespondedApplicant> getApplicantByVacancy_Id(Integer vacancyId){
        return respondedApplicantRepository.findByVacancy_Id(vacancyId);
    }
}
