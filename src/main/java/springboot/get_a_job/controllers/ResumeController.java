package springboot.get_a_job.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springboot.get_a_job.dto.*;
import springboot.get_a_job.dto.validation.OnCreate;
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
    private final EducationInfoService educationInfoService;
    private final WorkExperienceService workExperienceService;
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
    public String getAllActiveResumes(Model model) {
        List<ResumeDto> resumes = resumeService.getAllActiveResumes().orElseGet(null);
        model.addAttribute("resumes", resumes);
        return "resume-list";
    }

    @GetMapping("/{resumeId}")
    public String getRusumeById(@PathVariable Integer resumeId, Model model) {
        ResumeDto resume = resumeService.findResumeById(resumeId).orElseGet(null);
        model.addAttribute("resume", resume);
        return "resume";
    }



    //    @PutMapping("/update/{id}")
//    public ResponseEntity<String> updateResume(@Validated(OnUpdate.class)  @PathVariable Integer id, @RequestBody ResumeDto resumeDto) {
//        resumeService.updateResume(id, resumeDto);
//        return ResponseEntity
//                .status(HttpStatus.OK)
//                .body("Resume successfully updated");
//    }
//
//    @DeleteMapping("/delete/{id}")
//    public ResponseEntity<String> deleteResume(@PathVariable Integer id) {
//        resumeService.deleteResume(id);
//        return ResponseEntity
//                .status(HttpStatus.OK)
//                .body("Resume successfully deleted");
//    }
//
//    @GetMapping("/{id}")
//    public ResponseEntity<ResumeDto> findResumeById(@PathVariable Integer id) {
//        return resumeService.findResumeById(id)
//                .map(ResponseEntity::ok)
//                .orElseGet(() -> ResponseEntity.notFound().build());
//    }
//
//    @GetMapping("/categoryId/{category_id}")
//    public ResponseEntity<List<ResumeDto>> findResumeByCategoryId(@PathVariable Integer category_id) {
//        return resumeService.findResumeByCategory(category_id)
//                .map(ResponseEntity::ok)
//                .orElseGet(() -> ResponseEntity.notFound().build());
//    }
//
//    @GetMapping("/category/{category}")
//    public ResponseEntity<List<ResumeDto>> findResumeByCategory(@PathVariable String category) {
//        return resumeService.findResumeByCategory(category)
//                .map(ResponseEntity::ok)
//                .orElseGet(() -> ResponseEntity.notFound().build());
//    }
//
//    @GetMapping("/creatorId/{applicant_id}")
//    public ResponseEntity<List<ResumeDto>> findResumeByCreatorId(@PathVariable Integer applicant_id) {
//        return resumeService.findResumeByCreator(applicant_id)
//                .map(ResponseEntity::ok)
//                .orElseGet(() -> ResponseEntity.notFound().build());
//    }
//
//    @GetMapping("/creator/{name}")
//    public ResponseEntity<List<ResumeDto>> findResumeByCreatorName(@PathVariable String name) {
//        return resumeService.findResumeByCreator(name)
//                .map(ResponseEntity::ok)
//                .orElseGet(() -> ResponseEntity.notFound().build());
//    }
//
//    //figuring out
//    @PostMapping("add/education/{resumeId}")
//    public ResponseEntity<String> addEducation(@Validated(OnCreate.class) @PathVariable Integer resumeId, @RequestBody List<EducationDto> educationDto) {
//        educationInfoService.addEducationInfo(resumeId, educationDto);
//        return ResponseEntity
//                .status(HttpStatus.OK)
//                .body("Education info for Resume " + resumeId + " successfully added");
//    }
//
//    @PutMapping("update/education/{educationId}")
//    public ResponseEntity<String> updateEducation(@Validated(OnUpdate.class) @PathVariable Integer educationId, @RequestBody List<EducationDto> educationDto) {
//        educationInfoService.updateResumesEducationInfo(educationDto);
//        return ResponseEntity
//                .status(HttpStatus.OK)
//                .body("Education info successfully updated");
//    }
//
//    @PostMapping("add/work_exp/{resumeId}")
//    public ResponseEntity<String> addWorkExperience(@Validated(OnCreate.class) @PathVariable Integer resumeId, @RequestBody List<WorkExperienceDto> workExperienceDtos) {
//        workExperienceService.addWorkExperienceInfo(resumeId, workExperienceDtos);
//        return ResponseEntity
//                .status(HttpStatus.OK)
//                .body("Work Experience info for Resume " + resumeId + " successfully added");
//    }
//
//    @PutMapping("update/work_exp/{workExpId}")
//    public ResponseEntity<String> updateWorkExperience(@Validated(OnUpdate.class) @PathVariable Integer workExpId, @RequestBody List<WorkExperienceDto> workExperienceDto) {
//        workExperienceService.updateResumesWorkExperienceInfo(workExperienceDto);
//        return ResponseEntity
//                .status(HttpStatus.OK)
//                .body("Work Experience info successfully updated");
//    }
//
//    @PostMapping("add/contact_info/{resumeId}")
//    public ResponseEntity<String> addContactInfo(@Validated(OnCreate.class) @PathVariable Integer resumeId, @RequestBody List<ContactInfoDto> contactInfoDtos) {
//        contactInfoService.addContactInfo(resumeId, contactInfoDtos);
//        return ResponseEntity
//                .status(HttpStatus.OK)
//                .body("Contact info for Resume " + resumeId + " successfully added");
//    }
//
//    @PutMapping("update/contact_info/{contactInfoId}")
//    public ResponseEntity<String> updateContactInfo(@Validated(OnUpdate.class) @PathVariable Integer contactInfoId, @RequestBody List<ContactInfoDto> contactInfoDtos) {
//        contactInfoService.updateContactInfo(contactInfoDtos);
//        return ResponseEntity
//                .status(HttpStatus.OK)
//                .body("Contact info successfully updated");
//    }
}