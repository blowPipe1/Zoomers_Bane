package springboot.get_a_job.serviceImplementations;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import springboot.get_a_job.dto.*;
import springboot.get_a_job.exceptions.ResumeNotFoundException;
import springboot.get_a_job.exceptions.UserNotFoundException;
import springboot.get_a_job.exceptions.VacancyNotFoundException;
import springboot.get_a_job.models.Message;
import springboot.get_a_job.models.RespondedApplicant;
import springboot.get_a_job.models.Vacancy;
import springboot.get_a_job.repositories.MessageRepository;
import springboot.get_a_job.repositories.RespondedApplicantRepository;
import springboot.get_a_job.services.RespondedApplicantService;
import springboot.get_a_job.services.ResumeService;
import springboot.get_a_job.services.UserAccountService;
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
    private final UserAccountService userAccountService;

    @Override
    @Transactional
    public void applyToVacancy(ApplicantResponseDto dto) {
        VacancyDto vacancyDto = vacancyService.findVacancyById(dto.getVacancyId())
                .orElseThrow(() -> new VacancyNotFoundException("Vacancy not found"));

        ResumeDto resumeDto = resumeService.findResumeById(dto.getResumeId())
                .orElseThrow(() -> new ResumeNotFoundException("Resume not found"));

        RespondedApplicant application = new RespondedApplicant();
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

    @Override
    public List<RespondedApplicant> getApplicationsForUser(Integer userId){
        UserDto user = userAccountService.findUserById(userId).orElseThrow();
        if (user.getAccountType().equalsIgnoreCase("applicant")) {
            return respondedApplicantRepository.findByResume_Applicant_Id(userId);
        } else {
            return respondedApplicantRepository.findByVacancy_Author_Id(userId);
        }
    }

    @Override
    public List<RespondedApplicant> getApplicationsForEmployer(Integer employerId){
        return respondedApplicantRepository.findByVacancy_Author_Id(employerId);
    }

    @Override
    public RespondedApplicant getById(Integer applicantId){
        return respondedApplicantRepository.findById(applicantId).orElseThrow();
    }

    @Override
    public RespondedApplicantDto getDtoById(Integer applicantId){
        return convertToDto(respondedApplicantRepository.findById(applicantId).orElseThrow());
    }

    private RespondedApplicantDto convertToDto(RespondedApplicant application) {
        return new RespondedApplicantDto(
                application.getId(),
                application.getVacancy().getId(),
                application.getResume().getId(),
                application.getConfirmation()
        );
    }
}
