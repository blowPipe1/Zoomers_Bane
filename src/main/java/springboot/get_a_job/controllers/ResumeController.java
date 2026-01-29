package springboot.get_a_job.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springboot.get_a_job.dto.*;
import springboot.get_a_job.dto.validation.OnUpdate;
import springboot.get_a_job.models.Category;
import springboot.get_a_job.models.CustomUserDetails;
import springboot.get_a_job.services.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Controller
@RequestMapping("/api/resumes")
@RequiredArgsConstructor
public class ResumeController {
    private final CategoryService categoryService;
    private final ResumeService resumeService;
    private final ContactInfoService contactInfoService;

    @GetMapping("/form")
    public String showCreateForm(Model model) {
        ResumeDto resumeDto = new ResumeDto();

        Map<String, String> categories = categoryService.findAll().stream()
                .collect(Collectors.toMap(
                        Category::getName,
                        Category::getName,
                        (existing, replacement) -> existing
                ));

        Map<String, String> contactTypesMap = contactInfoService.findAll().stream()
                .collect(Collectors.toMap(
                        type -> type,
                        type -> type,
                        (existing, replacement) -> existing
                ));

        resumeDto.setEducation(new ArrayList<>(List.of(new EducationDto())));
        resumeDto.setWorkExperience(new ArrayList<>(List.of(new WorkExperienceDto())));
        resumeDto.setContactInfo(new ArrayList<>(List.of(new ContactInfoDto())));
        resumeDto.setIsActive(true);

        model.addAttribute("resumeDto", resumeDto);
        model.addAttribute("categories", categories);
        model.addAttribute("contactTypesMap", contactTypesMap);
        return "resume-create";
    }

    @PostMapping("/create-resume")
    public String registerResume(
            @Validated @ModelAttribute("resumeDto") ResumeDto resumeDto,
            BindingResult bindingResult,
            Model model,
            @AuthenticationPrincipal CustomUserDetails currentUserA) {

        if (bindingResult.hasErrors()) {
            Map<String, String> categories = categoryService.findAll().stream()
                    .collect(Collectors.toMap(Category::getName, Category::getName, (existing, replacement) -> existing));

            Map<String, String> contactTypesMap = contactInfoService.findAll().stream()
                    .collect(Collectors.toMap(type -> type, type -> type, (existing, replacement) -> existing));

            model.addAttribute("categories", categories);
            model.addAttribute("contactTypesMap", contactTypesMap);

            return "resume-create";
        }
        resumeDto.setApplicantEmail(currentUserA.getUsername());

        resumeService.createResume(resumeDto);
        return "redirect:/api/users/dashboard";
    }


    @GetMapping("/edit/{resumeId}")
    public String editResume(@PathVariable Integer resumeId, Model model) {
        ResumeDto resumeDto = resumeService.findResumeById(resumeId).orElseThrow();
        Map<String, String> categories = categoryService.findAll().stream()
                .collect(Collectors.toMap(
                        Category::getName,
                        Category::getName,
                        (existing, replacement) -> existing
                ));
        Map<String, String> contactTypesMap = contactInfoService.findAll().stream()
                .collect(Collectors.toMap(type -> type, type -> type, (existing, replacement) -> existing));

        model.addAttribute("categories", categories);
        model.addAttribute("resumeDto", resumeDto);
        model.addAttribute("contactTypesMap", contactTypesMap);

        return "edit-resume";
    }

    @PostMapping("/update/{resumeId}")
    public String updateResume(
            @PathVariable Integer resumeId,
            @Validated(OnUpdate.class) @ModelAttribute("resumeDto") ResumeDto resumeDto,
            BindingResult bindingResult,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        if (resumeDto.getEducation() == null) {
            resumeDto.setEducation(new ArrayList<>());
        }

        if (bindingResult.hasErrors()) {
            return "edit-resume";
        }

        resumeDto.setApplicantEmail(userDetails.getUsername());
        resumeService.updateResume(resumeId, resumeDto);


        return "redirect:/api/users/dashboard";
    }


    @GetMapping("/all")
    public String getAllActiveResumes(
            Model model,
            Pageable pageable,
            @RequestParam(defaultValue = "id") String sort,
            @RequestParam(defaultValue = "asc") String dir) {

        Sort sortOrder = dir.equalsIgnoreCase("desc") ? Sort.by(sort).descending() : Sort.by(sort).ascending();
        pageable = PageRequest.of(1, 15, sortOrder);

        Page<ResumeDto> resumePage = resumeService.getAllActiveResumes(pageable);

        model.addAttribute("resumes", resumePage.getContent());
        model.addAttribute("currentPage", resumePage.getNumber());
        model.addAttribute("totalPages", resumePage.getTotalPages());
        model.addAttribute("totalItems", resumePage.getTotalElements());

        return "resume-list";
    }

    @GetMapping("/{resumeId}")
    public String getRusumeById(@PathVariable Integer resumeId, Model model) {
        ResumeDto resume = resumeService.findResumeById(resumeId).orElseGet(null);
        model.addAttribute("resume", resume);
        return "resume";
    }
}