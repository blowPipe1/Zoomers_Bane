package springboot.get_a_job.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springboot.get_a_job.dto.*;
import springboot.get_a_job.dto.validation.OnUpdate;
import springboot.get_a_job.models.Category;
import springboot.get_a_job.models.CustomUserDetails;
import springboot.get_a_job.models.User;
import springboot.get_a_job.services.CategoryService;
import springboot.get_a_job.services.ResumeService;
import springboot.get_a_job.services.UserAccountService;
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
    private final ResumeService resumeService;
    private final UserAccountService userAccountService;

    @PostMapping("/create")
    public String createVacancy(
            @Validated @ModelAttribute("vacancyDto") VacancyDto vacancyDto,
            BindingResult bindingResult,
            Model model,
            @AuthenticationPrincipal CustomUserDetails currentUserA) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("categories", getCategoriesMap());

            return "vacancy-create";
        }
        vacancyDto.setAuthor(currentUserA.getUsername());

        vacancyService.createVacancy(vacancyDto);
        return "redirect:/api/users/dashboard";
    }

    @GetMapping("/form")
    public String showCreateForm(@AuthenticationPrincipal CustomUserDetails userDetails, Model model) {
        VacancyDto vacancyDto = new VacancyDto();

        vacancyDto.setIsActive(true);
        vacancyDto.setAuthor(userDetails.getUsername());

        model.addAttribute("vacancyDto", vacancyDto);
        model.addAttribute("categories", getCategoriesMap());


        return "vacancy-create";
    }


    @GetMapping("/edit/{vacancyId}")
    public String editVacancy(@PathVariable Integer vacancyId, Model model) {
        VacancyDto vacancyDto = vacancyService.findVacancyById(vacancyId).orElseThrow();

        model.addAttribute("categories", getCategoriesMap());
        model.addAttribute("vacancyDto", vacancyDto);

        return "edit-vacancy";
    }

    @PostMapping("/update/{vacancyId}")
    public String updateVacancy(
            @PathVariable Integer vacancyId,
            @Validated(OnUpdate.class) @ModelAttribute("vacancyDto") VacancyDto vacancyDto,
            @AuthenticationPrincipal CustomUserDetails userDetails,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            return "edit-vacancy";
        }

        vacancyDto.setAuthor(userDetails.getUsername());
        vacancyService.updateVacancy(vacancyId, vacancyDto);

        return "redirect:/api/users/dashboard";
    }

    @GetMapping("/{vacancyId}")
    public String getById(@PathVariable Integer vacancyId,
                          Model model,
                          @AuthenticationPrincipal CustomUserDetails currentUser){
        VacancyDto vacancy = vacancyService.findVacancyById(vacancyId).orElseGet(null);
        Integer creatorId = userAccountService.findByEmail(vacancy.getAuthor()).orElse(new User()).getId();
        model.addAttribute("vacancy", vacancy);
        model.addAttribute("creatorId", creatorId);

        List<ResumeDto> userResumes = resumeService.findAllByApplicantId(currentUser.getId());
        model.addAttribute("userResumes", userResumes);
        return "vacancy";
    }

    @GetMapping("/all")
    public String getAllActiveVacancies(
            Model model,
            @PageableDefault(size = 9) Pageable pageable,
            @RequestParam(defaultValue = "createdDate") String sort,
            @RequestParam(defaultValue = "desc") String dir,
            @RequestParam(required = false) String name) {

        Sort sortOrder = dir.equalsIgnoreCase("desc")
                ? Sort.by(sort).descending()
                : Sort.by(sort).ascending();

        Pageable pageRequest = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sortOrder);

        Page<VacancyDto> vacancyPage = vacancyService.getAllActiveVacancies(pageRequest, name);

        model.addAttribute("vacancies", vacancyPage.getContent());
        model.addAttribute("currentPage", vacancyPage.getNumber());
        model.addAttribute("totalPages", vacancyPage.getTotalPages());
        model.addAttribute("totalItems", vacancyPage.getTotalElements());

        model.addAttribute("sort", sort);
        model.addAttribute("dir", dir);
        model.addAttribute("name", name);

        return "vacancy-list";
    }

    private Map<String, String> getCategoriesMap() {
        return categoryService.findAll().stream()
                .collect(Collectors.toMap(
                        Category::getName,
                        Category::getName,
                        (existing, replacement) -> existing
                ));
    }
}
