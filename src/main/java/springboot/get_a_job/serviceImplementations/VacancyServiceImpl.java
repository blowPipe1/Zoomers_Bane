package springboot.get_a_job.serviceImplementations;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import springboot.get_a_job.dto.VacancyDto;
import springboot.get_a_job.exceptions.CategoryNotFoundException;
import springboot.get_a_job.exceptions.UserNotFoundException;
import springboot.get_a_job.exceptions.VacancyNotFoundException;
import springboot.get_a_job.models.Category;
import springboot.get_a_job.models.User;
import springboot.get_a_job.models.Vacancy;
import springboot.get_a_job.repositories.VacancyRepository;
import springboot.get_a_job.services.CategoryService;
import springboot.get_a_job.services.UserAccountService;
import springboot.get_a_job.services.VacancyService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class VacancyServiceImpl implements VacancyService {
    private final VacancyRepository vacancyRepository;
    @Autowired
    @Lazy
    private CategoryService categoryService;
    @Autowired
    @Lazy
    private UserAccountService userAccountService;

    @Override
    @Transactional
    public void createVacancy(VacancyDto vacancyDto) {
        if (vacancyDto == null) {
            throw new VacancyNotFoundException("Vacancy cannot be null");
        }
        Vacancy vacancy = convertIntoModel(vacancyDto);
        vacancyRepository.save(vacancy);
        log.info("Server Successfully created New Vacancy (ID: {})", vacancy.getId());
    }

    @Override
    @Transactional
    public void updateVacancy(Integer id, VacancyDto vacancyDto) {
        Vacancy vacancy = vacancyRepository.findById(id)
                .orElseThrow(() -> new VacancyNotFoundException("Vacancy not found"));

        vacancy.setName(vacancyDto.getName());
        vacancy.setDescription(vacancyDto.getDescription());
        vacancy.setSalary(vacancyDto.getSalary());
        vacancy.setExpFrom(vacancyDto.getExpFrom());
        vacancy.setExpTo(vacancyDto.getExpTo());
        vacancy.setIsActive(vacancyDto.getIsActive());
        vacancy.setUpdateTime(LocalDateTime.now());


        if (vacancyDto.getCategory() != null) {
            Category category = categoryService.findByNameIgnoreCase(vacancyDto.getCategory())
                    .orElseThrow(() -> new CategoryNotFoundException("Category not found"));
            vacancy.setCategory(category);
        }

        log.info("Server Successfully updated Vacancy(ID: {})", id);
    }

    @Override
    public Page<VacancyDto>getAllActiveVacancies(Pageable pageable) {
        Page<Vacancy>vacancyPage = vacancyRepository.findAllByIsActiveTrue(pageable);
        return vacancyPage.map(this::convertToDto);
    }

    @Override
    public Optional<VacancyDto> findVacancyById(Integer id) {
        return vacancyRepository.findById(id).map(this::convertToDto);
    }

    @Override
    public Page<VacancyDto> findVacancyByCreator(Integer authorId, Pageable pageable) {
        Page<Vacancy> vacancies = vacancyRepository.findAllByAuthorId(authorId, pageable);
        return vacancies.map(this::convertToDto);
    }

    @Override
    public List<VacancyDto> findAllByAuthorId(Integer authorId){
        return vacancyRepository.findAllByAuthorId(authorId)
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    private List<VacancyDto> convertList(List<Vacancy> vacancies) {
        return vacancies.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    private VacancyDto convertToDto(Vacancy vacancy) {
        return new VacancyDto(
                vacancy.getId(),
                vacancy.getName(),
                vacancy.getDescription(),
                vacancy.getCategory() != null ? vacancy.getCategory().getName() : null,
                vacancy.getSalary(),
                vacancy.getExpFrom(),
                vacancy.getExpTo(),
                vacancy.getIsActive(),
                vacancy.getAuthor() != null ? vacancy.getAuthor().getEmail() : null
        );
    }

    @Override
    public Vacancy convertIntoModel(VacancyDto dto) {
        Category category = categoryService.findByNameIgnoreCase(dto.getCategory())
                .orElseThrow(() -> new CategoryNotFoundException("Category not found: " + dto.getCategory()));

        User author = userAccountService.findByEmail(dto.getAuthor())
                .orElseThrow(() -> new UserNotFoundException("User not found: " + dto.getAuthor()));

        Vacancy vacancy = new Vacancy();
        vacancy.setName(dto.getName());
        vacancy.setDescription(dto.getDescription());
        vacancy.setCategory(category);
        vacancy.setAuthor(author);
        vacancy.setSalary(dto.getSalary());
        vacancy.setExpFrom(dto.getExpFrom());
        vacancy.setExpTo(dto.getExpTo());
        vacancy.setIsActive(dto.getIsActive() != null ? dto.getIsActive() : true);
        vacancy.setCreatedDate(LocalDateTime.now());
        vacancy.setUpdateTime(LocalDateTime.now());

        return vacancy;
    }
}


