package springboot.get_a_job.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
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

        resumeDto.setEducation(new ArrayList<>(List.of(new EducationDto())));
        resumeDto.setWorkExperience(new ArrayList<>(List.of(new WorkExperienceDto())));
        resumeDto.setContactInfo(new ArrayList<>(List.of(new ContactInfoDto())));
        resumeDto.setIsActive(true);

        model.addAttribute("resumeDto", resumeDto);
        model.addAttribute("categories", getCategoriesMap());
        model.addAttribute("contactTypesMap", getContactTypesMap());
        return "resume-create";
    }

    @PostMapping("/create-resume")
    @ResponseBody
    public ResponseEntity<?> registerResume(
            @Validated @RequestBody ResumeDto resumeDto,
            BindingResult bindingResult,
            @AuthenticationPrincipal CustomUserDetails currentUserA) {

        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(bindingResult.getAllErrors());
        }

        if (currentUserA != null) {
            resumeDto.setApplicantEmail(currentUserA.getUsername());
        }

        resumeService.createResume(resumeDto);

        return ResponseEntity.ok().body(Map.of("redirectUrl", "/api/users/dashboard"));
    }


    @GetMapping("/edit/{resumeId}")
    public String editResume(@PathVariable Integer resumeId, Model model) {

        ResumeDto resumeDto = resumeService.findResumeById(resumeId).orElseThrow();

        model.addAttribute("categories", getCategoriesMap());
        model.addAttribute("resumeDto", resumeDto);
        model.addAttribute("contactTypesMap", getContactTypesMap());

        return "edit-resume";
    }

    @PostMapping("/update/{resumeId}")
    public String updateResume(
            @PathVariable Integer resumeId,
            @Validated(OnUpdate.class) @ModelAttribute("resumeDto") ResumeDto resumeDto,
            BindingResult bindingResult,
            @AuthenticationPrincipal CustomUserDetails userDetails,
            Model model
    ) {
        ResumeDto original = resumeService.findResumeById(resumeId).orElseThrow();
        if (!original.getApplicantEmail().equals(userDetails.getUsername())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        if (bindingResult.hasErrors()) {
            resumeDto.setId(resumeId);
            resumeDto.setEducation(original.getEducation());
            resumeDto.setWorkExperience(original.getWorkExperience());
            resumeDto.setContactInfo(original.getContactInfo());
            model.addAttribute("categories", getCategoriesMap());
            model.addAttribute("contactTypesMap", getContactTypesMap());
            return "edit-resume";
        }

        resumeDto.setApplicantEmail(userDetails.getUsername());
        resumeService.updateResume(resumeId, resumeDto);

        return "redirect:/api/users/dashboard";
    }


    @GetMapping("/all")
    public String getAllActiveResumes(
            Model model,
            @PageableDefault(size = 9) Pageable pageable,
            @RequestParam(defaultValue = "createdDate") String sort,
            @RequestParam(defaultValue = "desc") String dir,
            @RequestParam(required = false) String name) {

        Sort sortOrder = dir.equalsIgnoreCase("desc")
                ? Sort.by(sort).descending()
                : Sort.by(sort).ascending();

        Pageable pageRequest = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sortOrder);

        Page<ResumeDto> resumePage = resumeService.getAllActiveResumes(pageRequest, name);

        model.addAttribute("resumes", resumePage.getContent());
        model.addAttribute("currentPage", resumePage.getNumber());
        model.addAttribute("totalPages", resumePage.getTotalPages());
        model.addAttribute("totalItems", resumePage.getTotalElements());

        model.addAttribute("sort", sort);
        model.addAttribute("dir", dir);
        model.addAttribute("name", name);

        return "resume-list";
    }

    @GetMapping("/{resumeId}")
    public String getRusumeById(@PathVariable Integer resumeId, Model model) {
        ResumeDto resume = resumeService.findResumeById(resumeId).orElseGet(null);
        model.addAttribute("resume", resume);
        return "resume";
    }

    private Map<String, String> getContactTypesMap() {
        return  contactInfoService.findAll().stream()
                .collect(Collectors.toMap(type -> type, type -> type, (existing, replacement) -> existing));
    }

    private Map<String, String> getCategoriesMap() {
        return categoryService.findAll().stream()
                .collect(Collectors.toMap(
                        Category::getName,
                        Category::getName,
                        (existing, replacement) -> existing
                ));
    }
}