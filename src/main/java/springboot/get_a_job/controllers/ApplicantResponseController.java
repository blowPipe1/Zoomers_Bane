package springboot.get_a_job.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import springboot.get_a_job.dto.ApplicantResponseDto;
import springboot.get_a_job.models.CustomUserDetails;
import springboot.get_a_job.models.RespondedApplicant;
import springboot.get_a_job.services.RespondedApplicantService;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/api/responses")
@RequiredArgsConstructor
public class ApplicantResponseController {
    private final RespondedApplicantService applicantService;

    @PostMapping("/apply")
    public String submitApplication(@ModelAttribute ApplicantResponseDto applyRequestDTO) {
        applicantService.applyToVacancy(applyRequestDTO);
        return "redirect:/api/responses/";
    }

    @GetMapping("/")
    public String showUserApplications(Model model,
                                       @AuthenticationPrincipal CustomUserDetails currentUser) {

        List<RespondedApplicant> apps = applicantService.getApplicationsForUser(currentUser.getId());
        model.addAttribute("applications", apps);
        return "user_applications";
    }
}
