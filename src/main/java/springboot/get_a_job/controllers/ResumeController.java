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

import java.util.List;

@RestController
@RequestMapping("/api/resumes")
@RequiredArgsConstructor
@Slf4j
public class ResumeController {

    private final ResumeServiceImpl resumeService;
    private final EducationInfoServiceImpl educationInfoService;
    private final WorkExperienceServiceImpl workExperienceService;
    private final ContactInfoServiceImpl contactInfoService;

    @PostMapping("/")
    public ResponseEntity<String> createResume(@Validated(OnCreate.class)  @RequestBody ResumeDto resumeDto) {
        log.info("Received request to create a Resume with a name: {}", resumeDto.getName());
        resumeService.createResume(resumeDto);
        log.debug("Created a Resume with a name: {}", resumeDto.getName());
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body("Resume successfully created");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateResume(@Validated(OnUpdate.class)  @PathVariable Integer id, @RequestBody ResumeDto resumeDto) {
        log.info("Received request to update a Resume(ID): {}", id);
        resumeService.updateResume(id, resumeDto);
        log.debug("Updated a Resume(ID): {}", id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body("Resume successfully updated");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteResume(@PathVariable Integer id) {
        log.info("Received request to delete a Resume(ID): {}", id);
        resumeService.deleteResume(id);
        log.debug("Deleted a Resume(ID): {}", id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body("Resume successfully deleted");
    }

    @GetMapping("/all")
    public ResponseEntity<List<ResumeDto>> getAllActiveResumes() {
        log.info("Received request to get all Resumes");
        return resumeService.getAllActiveResumes()
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());

    }

    @GetMapping("/{id}")
    public ResponseEntity<ResumeDto> findResumeById(@PathVariable Integer id) {
        log.info("Received request to find Resume(ID): {}", id);
        return resumeService.findResumeById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/categoryId/{category_id}")
    public ResponseEntity<List<ResumeDto>> findResumeByCategoryId(@PathVariable Integer category_id) {
        log.info("Received request to find Resume by Category(ID): {}", category_id);
        return resumeService.findResumeByCategory(category_id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<List<ResumeDto>> findResumeByCategory(@PathVariable String category) {
        log.info("Received request to find Resume by Category(name): {}", category);
        return resumeService.findResumeByCategory(category)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/creatorId/{applicant_id}")
    public ResponseEntity<List<ResumeDto>> findResumeByCreatorId(@PathVariable Integer applicant_id) {
        log.info("Received request to find Resume by Creator(ID): {}", applicant_id);
        return resumeService.findResumeByCreator(applicant_id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/creator/{name}")
    public ResponseEntity<List<ResumeDto>> findResumeByCreatorName(@PathVariable String name) {
        log.info("Received request to find Resume by Creator(name): {}", name);
        return resumeService.findResumeByCreator(name)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    //figuring out
    @PostMapping("add/education/{resumeId}")
    public ResponseEntity<String> addEducation(@Validated(OnCreate.class) @PathVariable Integer resumeId, @RequestBody List<EducationDto> educationDto) {
        log.info("Received request to add education for Resume(ID): {}", resumeId);
        educationInfoService.addEducationInfo(resumeId, educationDto);
        log.debug("Added education for Resume(ID): {}", resumeId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body("Education info for Resume " + resumeId + " successfully added");
    }

    @PutMapping("update/education/{educationId}")
    public ResponseEntity<String> updateEducation(@Validated(OnUpdate.class) @PathVariable Integer educationId, @RequestBody List<EducationDto> educationDto) {
        log.info("Received request to update Education(ID): {}", educationId);
        educationInfoService.updateResumesEducationInfo(educationDto);
        log.debug("Updated Education(ID): {}", educationId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body("Education info successfully updated");
    }

    @PostMapping("add/work_exp/{resumeId}")
    public ResponseEntity<String> addWorkExperience(@Validated(OnCreate.class) @PathVariable Integer resumeId, @RequestBody List<WorkExperienceDto> workExperienceDtos) {
        log.info("Received request to add Work experience for Resume(ID): {}", resumeId);
        workExperienceService.addWorkExperienceInfo(resumeId, workExperienceDtos);
        log.debug("Added work experience for Resume(ID): {}", resumeId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body("Work Experience info for Resume " + resumeId + " successfully added");
    }

    @PutMapping("update/work_exp/{workExpId}")
    public ResponseEntity<String> updateWorkExperience(@Validated(OnUpdate.class) @PathVariable Integer workExpId, @RequestBody List<WorkExperienceDto> workExperienceDto) {
        log.info("Received request to update Work Experience(ID): {}", workExpId);
        workExperienceService.updateResumesWorkExperienceInfo(workExperienceDto);
        log.debug("Updated Work Experience(ID): {}", workExpId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body("Work Experience info successfully updated");
    }

    @PostMapping("add/contact_info/{resumeId}")
    public ResponseEntity<String> addContactInfo(@Validated(OnCreate.class) @PathVariable Integer resumeId, @RequestBody List<ContactInfoDto> contactInfoDtos) {
        log.info("Received request to add Contact Info for Resume(ID): {}", resumeId);
        contactInfoService.addContactInfo(resumeId, contactInfoDtos);
        log.debug("Added Contact Info for Resume(ID): {}", resumeId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body("Contact info for Resume " + resumeId + " successfully added");
    }

    @PutMapping("update/contact_info/{contactInfoId}")
    public ResponseEntity<String> updateContactInfo(@Validated(OnUpdate.class) @PathVariable Integer contactInfoId, @RequestBody List<ContactInfoDto> contactInfoDtos) {
        log.info("Received request to update Contact Info(ID): {}", contactInfoId);
        contactInfoService.updateContactInfo(contactInfoDtos);
        log.debug("Updated Contact Info(ID): {}", contactInfoId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body("Contact info successfully updated");
    }
}