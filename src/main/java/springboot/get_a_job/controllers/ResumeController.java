package springboot.get_a_job.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springboot.get_a_job.dto.ContactInfoDto;
import springboot.get_a_job.dto.EducationDto;
import springboot.get_a_job.dto.ResumeDto;
import springboot.get_a_job.dto.WorkExperienceDto;
import springboot.get_a_job.models.Resume;
import springboot.get_a_job.services.ResumeServiceImpl;

import java.util.List;

@RestController
@RequestMapping("/api/resumes")
@RequiredArgsConstructor
public class ResumeController {

    private final ResumeServiceImpl resumeService;

    @PostMapping("/")
    public ResponseEntity<String> createResume(@RequestBody ResumeDto resumeDto) {
        resumeService.createResume(resumeDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body("Resume successfully created");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateResume(@PathVariable Integer id, @RequestBody ResumeDto resumeDto) {
        resumeService.updateResume(id, resumeDto);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body("Resume successfully updated");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteResume(@PathVariable Integer id) {
        resumeService.deleteResume(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body("Resume successfully deleted");
    }

    @PostMapping("add/education/{resumeId}")
    public ResponseEntity<String> addEducation(@PathVariable Integer resumeId, @RequestBody List<EducationDto> educationDto) {
        resumeService.addEducationInfo(resumeId, educationDto);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body("Education info for Resume " + resumeId + " successfully added");
    }

    @PutMapping("update/education/{educationId}")
    public ResponseEntity<String> updateEducation(@PathVariable Integer educationId, @RequestBody EducationDto educationDto) {
        resumeService.updateResumesEducationInfo(educationId, educationDto);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body("Education info successfully updated");
    }

    @PostMapping("add/work_exp/{resumeId}")
    public ResponseEntity<String> addWorkExperience(@PathVariable Integer resumeId, @RequestBody List<WorkExperienceDto> workExperienceDtos) {
        resumeService.addWorkExperienceInfo(resumeId, workExperienceDtos);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body("Work Experience info for Resume " + resumeId + " successfully added");
    }

    @PutMapping("update/work_exp/{workExpId}")
    public ResponseEntity<String> updateWorkExperience(@PathVariable Integer workExpId, @RequestBody WorkExperienceDto workExperienceDto) {
        resumeService.updateResumesWorkExperienceInfo(workExpId, workExperienceDto);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body("Work Experience info successfully updated");
    }

    @PostMapping("add/contact_info/{resumeId}")
    public ResponseEntity<String> addContactInfo(@PathVariable Integer resumeId, @RequestBody List<ContactInfoDto> contactInfoDtos) {
        resumeService.addContactInfo(resumeId, contactInfoDtos);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body("Contact info for Resume " + resumeId + " successfully added");
    }

    @PutMapping("update/contact_info/{contactInfoId}")
    public ResponseEntity<String> updateContactInfo(@PathVariable Integer contactInfoId, @RequestBody ContactInfoDto contactInfoDto) {
        resumeService.updateContactInfo(contactInfoId, contactInfoDto);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body("Contact info successfully updated");
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
}