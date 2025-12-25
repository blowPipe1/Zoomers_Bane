package springboot.get_a_job.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springboot.get_a_job.dto.ResumeDto;
import springboot.get_a_job.dto.VacancyDto;
import springboot.get_a_job.models.Vacancy;
import springboot.get_a_job.services.VacancyService;

import java.util.List;

@RestController
@RequestMapping("/api/vacancies")
@RequiredArgsConstructor
@Slf4j
public class VacancyController {

    private final VacancyService vacancyService;

    @PostMapping("/")
    public ResponseEntity<String> createVacancy(@RequestBody VacancyDto vacancyDto) {
        log.info("Received request to create a Vacancy with a name: {}", vacancyDto.getName());
        vacancyService.createVacancy(vacancyDto);
        log.debug("Created a Vacancy with a name: {}", vacancyDto.getName());
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body("Vacancy has been created");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateVacancy(@PathVariable Integer id, @RequestBody VacancyDto vacancyDto) {
        log.info("Received request to update a Vacancy with a id: {}", id);
        vacancyService.updateVacancy(id, vacancyDto);
        log.debug("Updated a Vacancy with a id: {}", id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body("Vacancy has been updated");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteVacancy(@PathVariable Integer id) {
        log.info("Received request to delete a Vacancy with a id: {}", id);
        vacancyService.deleteVacancy(id);
        log.debug("Deleted a Vacancy with a id: {}", id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body("Vacancy has been deleted");
    }

    @PostMapping("/{vacancyId}/respond")
    public ResponseEntity<String> respondToVacancy(@PathVariable Integer vacancyId, @RequestParam Integer resumeId) {
        log.info("Received request to register a response of Resume with a id: {} to Vacancy with a id: {} ", resumeId, vacancyId);
        vacancyService.respondToVacancy(vacancyId, resumeId);
        log.debug("Registered a response of Resume with a id: {} to Vacancy with a id: {} ", resumeId, vacancyId);
        return ResponseEntity.ok("Successfully registered a response");
    }

    @GetMapping("/all")
    public ResponseEntity<List<VacancyDto>> searchAllActiveVacancies() {
        log.info("Received request to search all vacancies");
        return vacancyService.getAllActiveVacancies()
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<VacancyDto> findVacancyById(@PathVariable Integer id) {
        log.info("Received request to find a Vacancy with a id: {}", id);
        return vacancyService.findVacancyById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/categoryId/{category_id}")
    public ResponseEntity<List<VacancyDto>> findVacancyByCategoryId(@PathVariable Integer category_id) {
        log.info("Received request to find Vacancies by Category(ID): {}", category_id);
        return vacancyService.findVacancyByCategory(category_id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<List<VacancyDto>> findVacancyByCategory(@PathVariable String category) {
        log.info("Received request to find Vacancies by Category(name): {}", category);
        return vacancyService.findVacancyByCategory(category)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/creatorId/{applicant_id}")
    public ResponseEntity<List<VacancyDto>> findVacancyByCreatorId(@PathVariable Integer applicant_id) {
        log.info("Received request to find Vacancies created by User(ID): {}", applicant_id);
        return vacancyService.findVacancyByCreator(applicant_id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/creator/{name}")
    public ResponseEntity<List<VacancyDto>> findVacancyByCreatorName(@PathVariable String name) {
        log.info("Received request to find Vacancies created by User(name): {}", name);
        return vacancyService.findVacancyByCreator(name)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/responded/{applicant_id}")
    public ResponseEntity<List<VacancyDto>> findRespondedVacancies(@PathVariable Integer applicant_id) {
        log.info("Received request to find Vacancies Responded by User(ID): {}", applicant_id);
        return vacancyService.findRespondedVacancies(applicant_id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

}
