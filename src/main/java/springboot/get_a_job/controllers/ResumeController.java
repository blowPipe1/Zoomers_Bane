package springboot.get_a_job.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springboot.get_a_job.models.Resume;
import springboot.get_a_job.models.Vacancy;
import springboot.get_a_job.services.ResumeServiceImpl;

import java.util.List;

@RestController
@RequestMapping("/api/applicant")
@RequiredArgsConstructor
public class ResumeController {

    private final ResumeServiceImpl resumeService;

    @PostMapping("/resumes")
    public ResponseEntity<Resume> createResume(@RequestBody Resume resume) {
        Resume createdResume = resumeService.createResume(resume);
        return ResponseEntity.ok(createdResume);
    }

    @PutMapping("/resumes/{id}")
    public ResponseEntity<Resume> updateResume(@PathVariable Integer id, @RequestBody Resume resumeDetails) {
        Resume updatedResume = resumeService.updateResume(id, resumeDetails);
        return ResponseEntity.ok(updatedResume);
    }

    @DeleteMapping("/resumes/{id}")
    public ResponseEntity<Void> deleteResume(@PathVariable Integer id) {
        resumeService.deleteResume(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/vacancies/all")
    public ResponseEntity<List<Vacancy>> searchAllActiveVacancies() {
        List<Vacancy> vacancies = resumeService.findAllActiveVacancies();
        return ResponseEntity.ok(vacancies);
    }

    @GetMapping("/vacancies/category/{categoryId}")
    public ResponseEntity<List<Vacancy>> searchVacanciesByCategory(@PathVariable Integer categoryId) {
        List<Vacancy> vacancies = resumeService.findVacanciesByCategoryId(categoryId);
        return ResponseEntity.ok(vacancies);
    }

    @PostMapping("/vacancies/{vacancyId}/respond")
    public ResponseEntity<String> respondToVacancy(@PathVariable Integer vacancyId, @RequestParam Integer resumeId) {
        resumeService.respondToVacancy(vacancyId, resumeId);
        return ResponseEntity.ok("отклик зарегистрирован");
    }
}
