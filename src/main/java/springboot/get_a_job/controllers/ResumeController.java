package springboot.get_a_job.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springboot.get_a_job.dto.ContactInfoDto;
import springboot.get_a_job.dto.EducationDto;
import springboot.get_a_job.dto.ResumeDto;
import springboot.get_a_job.dto.WorkExperienceDto;
import springboot.get_a_job.dto.validation.OnCreate;
import springboot.get_a_job.dto.validation.OnUpdate;
import springboot.get_a_job.serviceImplementations.ContactInfoServiceImpl;
import springboot.get_a_job.serviceImplementations.EducationInfoServiceImpl;
import springboot.get_a_job.serviceImplementations.ResumeServiceImpl;
import springboot.get_a_job.serviceImplementations.WorkExperienceServiceImpl;
import springboot.get_a_job.services.ContactInfoService;
import springboot.get_a_job.services.EducationInfoService;
import springboot.get_a_job.services.ResumeService;
import springboot.get_a_job.services.WorkExperienceService;

import java.util.List;

@RestController
@RequestMapping("/api/resumes")
@RequiredArgsConstructor
public class ResumeController {

    private final ResumeService resumeService;
    private final EducationInfoService educationInfoService;
    private final WorkExperienceService workExperienceService;
    private final ContactInfoService contactInfoService;

    @PostMapping("/create")
    public ResponseEntity<String> createResume(@Validated(OnCreate.class)  @RequestBody ResumeDto resumeDto) {
        resumeService.createResume(resumeDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body("Resume successfully created");
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateResume(@Validated(OnUpdate.class)  @PathVariable Integer id, @RequestBody ResumeDto resumeDto) {
        resumeService.updateResume(id, resumeDto);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body("Resume successfully updated");
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteResume(@PathVariable Integer id) {
        resumeService.deleteResume(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body("Resume successfully deleted");
    }

    @GetMapping("/all")
    public ResponseEntity<List<ResumeDto>> getAllActiveResumes() {
        return resumeService.getAllActiveResumes()
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());

    }

    @GetMapping("/{id}")
    public ResponseEntity<ResumeDto> findResumeById(@PathVariable Integer id) {
        return resumeService.findResumeById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/categoryId/{category_id}")
    public ResponseEntity<List<ResumeDto>> findResumeByCategoryId(@PathVariable Integer category_id) {
        return resumeService.findResumeByCategory(category_id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<List<ResumeDto>> findResumeByCategory(@PathVariable String category) {
        return resumeService.findResumeByCategory(category)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/creatorId/{applicant_id}")
    public ResponseEntity<List<ResumeDto>> findResumeByCreatorId(@PathVariable Integer applicant_id) {
        return resumeService.findResumeByCreator(applicant_id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/creator/{name}")
    public ResponseEntity<List<ResumeDto>> findResumeByCreatorName(@PathVariable String name) {
        return resumeService.findResumeByCreator(name)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    //figuring out
    @PostMapping("add/education/{resumeId}")
    public ResponseEntity<String> addEducation(@Validated(OnCreate.class) @PathVariable Integer resumeId, @RequestBody List<EducationDto> educationDto) {
        educationInfoService.addEducationInfo(resumeId, educationDto);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body("Education info for Resume " + resumeId + " successfully added");
    }

    @PutMapping("update/education/{educationId}")
    public ResponseEntity<String> updateEducation(@Validated(OnUpdate.class) @PathVariable Integer educationId, @RequestBody List<EducationDto> educationDto) {
        educationInfoService.updateResumesEducationInfo(educationDto);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body("Education info successfully updated");
    }

    @PostMapping("add/work_exp/{resumeId}")
    public ResponseEntity<String> addWorkExperience(@Validated(OnCreate.class) @PathVariable Integer resumeId, @RequestBody List<WorkExperienceDto> workExperienceDtos) {
        workExperienceService.addWorkExperienceInfo(resumeId, workExperienceDtos);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body("Work Experience info for Resume " + resumeId + " successfully added");
    }

    @PutMapping("update/work_exp/{workExpId}")
    public ResponseEntity<String> updateWorkExperience(@Validated(OnUpdate.class) @PathVariable Integer workExpId, @RequestBody List<WorkExperienceDto> workExperienceDto) {
        workExperienceService.updateResumesWorkExperienceInfo(workExperienceDto);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body("Work Experience info successfully updated");
    }

    @PostMapping("add/contact_info/{resumeId}")
    public ResponseEntity<String> addContactInfo(@Validated(OnCreate.class) @PathVariable Integer resumeId, @RequestBody List<ContactInfoDto> contactInfoDtos) {
        contactInfoService.addContactInfo(resumeId, contactInfoDtos);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body("Contact info for Resume " + resumeId + " successfully added");
    }

    @PutMapping("update/contact_info/{contactInfoId}")
    public ResponseEntity<String> updateContactInfo(@Validated(OnUpdate.class) @PathVariable Integer contactInfoId, @RequestBody List<ContactInfoDto> contactInfoDtos) {
        contactInfoService.updateContactInfo(contactInfoDtos);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body("Contact info successfully updated");
    }
}