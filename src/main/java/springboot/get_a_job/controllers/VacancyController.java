package springboot.get_a_job.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import springboot.get_a_job.services.CategoryService;
import springboot.get_a_job.services.VacancyService;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/api/vacancies")
@RequiredArgsConstructor
public class VacancyController {
    private final VacancyService vacancyService;
    private final CategoryService categoryService;

    @PostMapping("/create")
    public String createVacancy(
            @Validated @ModelAttribute("vacancyDto") VacancyDto vacancyDto,
            BindingResult bindingResult,
            Model model,
            @AuthenticationPrincipal CustomUserDetails currentUserA) {

        if (bindingResult.hasErrors()) {
            Map<String, String> categories = categoryService.findAll().stream()
                    .collect(Collectors.toMap(Category::getName, Category::getName, (existing, replacement) -> existing));

            model.addAttribute("categories", categories);

            return "resume-create";
        }
        vacancyDto.setAuthor(currentUserA.getUsername());

        vacancyService.createVacancy(vacancyDto);
        return "redirect:/api/users/dashboard";
    }

    @GetMapping("/form")
    public String showCreateForm(@AuthenticationPrincipal CustomUserDetails userDetails, Model model) {
        VacancyDto vacancyDto = new VacancyDto();


        Map<String, String> categories = categoryService.findAll().stream()
                .collect(Collectors.toMap(
                        Category::getName,
                        Category::getName,
                        (existing, replacement) -> existing
                ));

        vacancyDto.setIsActive(true);
        vacancyDto.setAuthor(userDetails.getUsername());

        model.addAttribute("vacancyDto", vacancyDto);
        model.addAttribute("categories", categories);


        return "vacancy-create";
    }

//    @PutMapping("/update/{id}")
//    public ResponseEntity<String> updateVacancy(@Validated(OnUpdate.class) @PathVariable Integer id, @RequestBody VacancyDto vacancyDto) {
//        vacancyService.updateVacancy(id, vacancyDto);
//        return ResponseEntity
//                .status(HttpStatus.OK)
//                .body("Vacancy has been updated");
//    }
//
//    @DeleteMapping("/delete/{id}")
//    public ResponseEntity<String> deleteVacancy(@PathVariable Integer id) {
//        vacancyService.deleteVacancy(id);
//        return ResponseEntity
//                .status(HttpStatus.OK)
//                .body("Vacancy has been deleted");
//    }
//
//    @PostMapping("/{vacancyId}/respond")
//    public ResponseEntity<String> respondToVacancy(@PathVariable Integer vacancyId, @RequestParam Integer resumeId) {
//        vacancyService.respondToVacancy(vacancyId, resumeId);
//        return ResponseEntity.ok("Successfully registered a response");
//    }

    @GetMapping("/all")
    public String getAllActiveResumes(Model model) {
        List<VacancyDto> vacancies = vacancyService.getAllActiveVacancies().orElseGet(null);
        model.addAttribute("vacancies", vacancies);
        return "vacancy-list";
    }


//    @GetMapping("/{id}")
//    public ResponseEntity<VacancyDto> findVacancyById(@PathVariable Integer id) {
//        return vacancyService.findVacancyById(id)
//                .map(ResponseEntity::ok)
//                .orElseGet(() -> ResponseEntity.notFound().build());
//    }
//
//    @GetMapping("/categoryId/{category_id}")
//    public ResponseEntity<List<VacancyDto>> findVacancyByCategoryId(@PathVariable Integer category_id) {
//        return vacancyService.findVacancyByCategory(category_id)
//                .map(ResponseEntity::ok)
//                .orElseGet(() -> ResponseEntity.notFound().build());
//    }
//
//    @GetMapping("/category/{category}")
//    public ResponseEntity<List<VacancyDto>> findVacancyByCategory(@PathVariable String category) {
//        return vacancyService.findVacancyByCategory(category)
//                .map(ResponseEntity::ok)
//                .orElseGet(() -> ResponseEntity.notFound().build());
//    }
//
//    @GetMapping("/creatorId/{applicant_id}")
//    public ResponseEntity<List<VacancyDto>> findVacancyByCreatorId(@PathVariable Integer applicant_id) {
//        return vacancyService.findVacancyByCreator(applicant_id)
//                .map(ResponseEntity::ok)
//                .orElseGet(() -> ResponseEntity.notFound().build());
//    }
//
//    @GetMapping("/creator/{name}")
//    public ResponseEntity<List<VacancyDto>> findVacancyByCreatorName(@PathVariable String name) {
//        return vacancyService.findVacancyByCreator(name)
//                .map(ResponseEntity::ok)
//                .orElseGet(() -> ResponseEntity.notFound().build());
//    }
//
//    @GetMapping("/responded/{applicant_id}")
//    public ResponseEntity<List<VacancyDto>> findRespondedVacancies(@PathVariable Integer applicant_id) {
//        return vacancyService.findRespondedVacancies(applicant_id)
//                .map(ResponseEntity::ok)
//                .orElseGet(() -> ResponseEntity.notFound().build());
//    }

}
