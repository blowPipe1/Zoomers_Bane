package springboot.get_a_job.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springboot.get_a_job.models.RespondedApplicant;
import springboot.get_a_job.models.Resume;
import springboot.get_a_job.models.Vacancy;
import springboot.get_a_job.services.VacancyService;

import java.util.List;

@RestController
@RequestMapping("/api/employer/vacancies")
@RequiredArgsConstructor
public class VacancyController {

    private final VacancyService vacancyService;

    @PostMapping
    public ResponseEntity<Vacancy> createVacancy(@RequestBody Vacancy vacancy) {
        Vacancy createdVacancy = vacancyService.createVacancy(vacancy);
        return ResponseEntity.ok(createdVacancy);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Vacancy> updateVacancy(@PathVariable Integer id, @RequestBody Vacancy vacancyDetails) {
        Vacancy updatedVacancy = vacancyService.updateVacancy(id, vacancyDetails);
        return ResponseEntity.ok(updatedVacancy);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVacancy(@PathVariable Integer id) {
        vacancyService.deleteVacancy(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/resumes/all")
    public ResponseEntity<List<Resume>> searchAllResumes() {
        List<Resume> resumes = vacancyService.findAllActiveResumes();
        return ResponseEntity.ok(resumes);
    }

    @GetMapping("/resumes/category/{categoryId}")
    public ResponseEntity<List<Resume>> searchResumesByCategory(@PathVariable Integer categoryId) {
        List<Resume> resumes = vacancyService.findResumesByCategoryId(categoryId);
        return ResponseEntity.ok(resumes);
    }

    @GetMapping("/{vacancyId}/applicants")
    public ResponseEntity<List<RespondedApplicant>> findRespondedApplicants(@PathVariable Integer vacancyId) {
        List<RespondedApplicant> applicants = vacancyService.findApplicantsForVacancy(vacancyId);
        return ResponseEntity.ok(applicants);
    }
}
