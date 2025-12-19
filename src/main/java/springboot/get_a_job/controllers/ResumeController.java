package springboot.get_a_job.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springboot.get_a_job.dto.ResumeDto;
import springboot.get_a_job.models.Resume;
import springboot.get_a_job.services.ResumeServiceImpl;

import java.util.List;

@RestController
@RequestMapping("/api/resumes")
@RequiredArgsConstructor
public class ResumeController {

    private final ResumeServiceImpl resumeService;

    @PostMapping("/")
    public ResponseEntity<String> createResume(@RequestBody ResumeDto resume) {
        resumeService.createResume(resume);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body("Резюме успешно создано");
    }

    @PutMapping("/{id}")
    public ResponseEntity<Resume> updateResume(@PathVariable Integer id, @RequestBody Resume resumeDetails) {
        Resume updatedResume = resumeService.updateResume(id, resumeDetails);
        return ResponseEntity.ok(updatedResume);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteResume(@PathVariable Integer id) {
        resumeService.deleteResume(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/all")
    public ResponseEntity<List<Resume>> getAllActiveResumes() {
        return resumeService.getAllActiveResumes()
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Resume> findResumeById(@PathVariable Integer id) {
        return resumeService.findResumeById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/categoryId/{category_id}")
    public ResponseEntity<List<Resume>> findResumeByCategoryId(@PathVariable Integer category_id) {
        return resumeService.findResumeByCategory(category_id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<List<Resume>> findResumeByCategory(@PathVariable String category) {
        return resumeService.findResumeByCategory(category)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/creatorId/{applicant_id}")
    public ResponseEntity<List<Resume>> findResumeByCreatorId(@PathVariable Integer applicant_id) {
        return resumeService.findResumeByCreator(applicant_id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/creator/{name}")
    public ResponseEntity<List<Resume>> findResumeByCreatorName(@PathVariable String name) {
        return resumeService.findResumeByCreator(name)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
