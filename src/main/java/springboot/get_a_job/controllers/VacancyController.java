package springboot.get_a_job.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springboot.get_a_job.dto.VacancyDto;
import springboot.get_a_job.models.Vacancy;
import springboot.get_a_job.services.VacancyService;

import java.util.List;

@RestController
@RequestMapping("/api/vacancies")
@RequiredArgsConstructor
public class VacancyController {

    private final VacancyService vacancyService;

    @PostMapping("/")
    public ResponseEntity<String> createVacancy(@RequestBody VacancyDto vacancy) {
        vacancyService.createVacancy(vacancy);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body("Vacancy has been created");
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

    @PostMapping("/{vacancyId}/respond")
    public ResponseEntity<String> respondToVacancy(@PathVariable Integer vacancyId, @RequestParam Integer resumeId) {
        vacancyService.respondToVacancy(vacancyId, resumeId);
        return ResponseEntity.ok("отклик зарегистрирован");
    }

    @GetMapping("/all")
    public ResponseEntity<List<Vacancy>> searchAllActiveVacancies() {
        return vacancyService.getAllActiveVacancies()
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Vacancy> findVacancyById(@PathVariable Integer id) {
        return vacancyService.findVacancyById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/categoryId/{category_id}")
    public ResponseEntity<List<Vacancy>> findVacancyByCategoryId(@PathVariable Integer category_id) {
        return vacancyService.findVacancyByCategory(category_id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<List<Vacancy>> findVacancyByCategory(@PathVariable String category) {
        return vacancyService.findVacancyByCategory(category)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/responded/{applicant_id}")
    public ResponseEntity<List<Vacancy>> findRespondedVacancies(@PathVariable Integer applicant_id) {
        return vacancyService.findRespondedVacancies(applicant_id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

}
