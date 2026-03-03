package springboot.get_a_job.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import springboot.get_a_job.dto.ResumeDto;
import springboot.get_a_job.dto.UserDto;
import springboot.get_a_job.dto.VacancyDto;
import springboot.get_a_job.dto.validation.OnUpdate;
import springboot.get_a_job.exceptions.UserNotFoundException;
import springboot.get_a_job.models.CustomUserDetails;
import springboot.get_a_job.services.ResumeService;
import springboot.get_a_job.services.UserAccountService;
import springboot.get_a_job.services.VacancyService;

import java.io.IOException;


@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserAccountController {
    private final UserAccountService userAccountService;
    private final ResumeService resumeService;
    private final VacancyService vacancyService;


    @GetMapping("/dashboard")
    public String dashboard(
            Model model,
            @AuthenticationPrincipal CustomUserDetails currentUserA,
            @PageableDefault(size = 6, sort = "id") Pageable pageable) {
        UserDto currentUser = userAccountService.findUserById(currentUserA.getId())
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + currentUserA.getId()));

        model.addAttribute("user", currentUser);

        if (currentUser.getAccountType().equalsIgnoreCase("applicant")) {
            Page<ResumeDto> resumePage = resumeService.findResumeByCreator(currentUserA.getId(), pageable);
            model.addAttribute("itemsList", resumePage.getContent());
            model.addAttribute("currentPage", resumePage.getNumber());
            model.addAttribute("totalPages", resumePage.getTotalPages());
            model.addAttribute("totalItems", resumePage.getTotalElements());
        } else if (currentUser.getAccountType().equalsIgnoreCase("employer")) {
            Page<VacancyDto> vacancyPage = vacancyService.findVacancyByCreator(currentUserA.getId(), pageable);
            model.addAttribute("itemsList", vacancyPage.getContent());
            model.addAttribute("currentPage", vacancyPage.getNumber());
            model.addAttribute("totalPages", vacancyPage.getTotalPages());
            model.addAttribute("totalItems", vacancyPage.getTotalElements());
        }

        return "dashboard";
    }


    @GetMapping("/edit")
    public String editPage(@AuthenticationPrincipal CustomUserDetails userDetails, Model model) {
        UserDto userDto = userAccountService.findUserById(userDetails.getId())
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + userDetails.getId()));

        model.addAttribute("userDto", userDto);

        return "edit-profile";
    }


    @PostMapping("/update")
    public String updateProfile(
            @AuthenticationPrincipal CustomUserDetails currentUser,
            @Validated(OnUpdate.class) @ModelAttribute("userDto") UserDto userDto,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            return "edit-profile";
        }

        Integer userId = currentUser.getId();
        userAccountService.updateUser(userId, userDto);

        return "redirect:/api/users/dashboard";
    }


    @PostMapping("/avatar")
    public String uploadAvatar(@AuthenticationPrincipal CustomUserDetails currentUserA, @RequestParam("file") MultipartFile file) {
        try {
            userAccountService.saveAvatar(currentUserA.getId(), file);
            return "redirect:/api/users/dashboard";
        } catch (IOException e) {
            return ("Error while uploading avatar");
        } catch (IllegalArgumentException e) {
            return (e.getMessage());
        }
    }
}

