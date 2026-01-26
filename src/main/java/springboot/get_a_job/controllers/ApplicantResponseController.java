package springboot.get_a_job.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import springboot.get_a_job.dto.ApplicantResponseDto;
import springboot.get_a_job.services.RespondedApplicantService;

@Slf4j
@Controller
@RequestMapping("/api/responses")
@RequiredArgsConstructor
public class ApplicantResponseController {
    private final RespondedApplicantService applicantService;

    @PostMapping
    public String submitApplication(@ModelAttribute ApplicantResponseDto applyRequestDTO) {
        applicantService.applyToVacancy(applyRequestDTO);
        return "redirect:/vacancies/" + applyRequestDTO.getVacancyId() + "?success=true";
    }
}
