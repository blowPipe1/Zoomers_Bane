package springboot.get_a_job.serviceImplementations;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import springboot.get_a_job.dao.CategoryDao;
import springboot.get_a_job.dao.UserDao;
import springboot.get_a_job.dao.VacancyDao;
import springboot.get_a_job.dto.ResumeDto;
import springboot.get_a_job.dto.VacancyDto;
import springboot.get_a_job.exceptions.CategoryNotFoundException;
import springboot.get_a_job.exceptions.ResumeNotFoundException;
import springboot.get_a_job.exceptions.VacancyNotFoundException;
import springboot.get_a_job.models.Vacancy;
import springboot.get_a_job.services.CategoryService;
import springboot.get_a_job.services.UserAccountService;
import springboot.get_a_job.services.VacancyService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class VacancyServiceImpl implements VacancyService {
    private final VacancyDao vacancyDao;
    private final CategoryService categoryService;
    private final UserAccountService userAccountService;

    @Override
    public void createVacancy(VacancyDto vacancy) {
        if (vacancy == null) {
            throw new VacancyNotFoundException("Vacancy cannot be null");
        }
        vacancyDao.createVacancy(convertIntoModel(vacancy));
        log.info("Server Successfully created New Vacancy with name: {} & category: {}", vacancy.getName(), vacancy.getCategory());
    }

    @Override
    public void updateVacancy(Integer id, VacancyDto vacancy) {
        if (vacancy == null) {
            throw new VacancyNotFoundException("Vacancy cannot be null");
        }
        VacancyDto checkedVacancy = checkFieldsForNullOrEmpty(id, vacancy);
        vacancyDao.updateVacancy(id, convertIntoModel(checkedVacancy));
        log.info("Server Successfully updated Vacancy(ID: {})", id);
    }

    @Override
    public void deleteVacancy(Integer id) {
        if (vacancyDao.findVacancyById(id) == null) {
            throw new VacancyNotFoundException("Vacancy cannot be null");
        }
        vacancyDao.deleteVacancy(id);
        log.info("Server Successfully deleted Vacancy(ID: {})", id);
    }

    @Override
    public void respondToVacancy(Integer vacancyId, Integer resumeId) {}

    @Override
    public Optional<List<VacancyDto>> getAllActiveVacancies() {
        return convert(vacancyDao.getAllActiveVacancies());
    }

    @Override
    public Optional<VacancyDto> findVacancyById(Integer id) {
        return convert(vacancyDao.findVacancyById(id));
    }

    @Override
    public Optional<List<VacancyDto>> findVacancyByCategory(Integer category_id) {
        return convert(vacancyDao.findVacancyByCategory(category_id));
    }

    @Override
    public Optional<List<VacancyDto>> findVacancyByCategory(String category) {
        return convert(vacancyDao.findVacancyByCategory(category));
    }

    @Override
    public Optional<List<VacancyDto>> findVacancyByCreator(Integer applicant_id) {
        return convert(vacancyDao.findVacancyByCreator(applicant_id));
    }

    @Override
    public Optional<List<VacancyDto>> findVacancyByCreator(String creatorName) {
        return convert(vacancyDao.findVacancyByCreator(creatorName));
    }

    @Override
    public Optional<List<VacancyDto>> findRespondedVacancies(Integer applicant_id) {
        return convert(vacancyDao.findRespondedVacancies(applicant_id));
    }

    private Optional<List<VacancyDto>>convert(List<Vacancy> vacancies) {
        if (vacancies == null || vacancies.isEmpty()) {
            throw new VacancyNotFoundException("Vacancies not found");
        }
        List<VacancyDto>vacancyDtos = new ArrayList<>();
        for (Vacancy vacancy : vacancies){
            vacancyDtos.add(new VacancyDto(
                            vacancy.getName(),
                            vacancy.getDescription(),
                            categoryService.findNameById(vacancy.getCategoryId()),
                            vacancy.getSalary(),
                            vacancy.getExpFrom(),
                            vacancy.getExpTo(),
                            vacancy.getIsActive(),
                    userAccountService.findNameById(vacancy.getAuthorId())
                    )
            );
            log.info("Mapping (ID: {})Vacancy into Vacancy Model", vacancy.getId());
        }
        return Optional.of(vacancyDtos);
    }

    private Optional<VacancyDto>convert(Vacancy vacancy) {
        if (vacancy == null) {
            throw new VacancyNotFoundException("Vacancy not found");
        }
        log.info("Mapping (ID: {})Vacancy created by (creator ID: {}) into Vacancy DTO", vacancy.getId(), vacancy.getAuthorId());
        return Optional.of(new VacancyDto(
                vacancy.getName(),
                vacancy.getDescription(),
                categoryService.findNameById(vacancy.getCategoryId()),
                vacancy.getSalary(),
                vacancy.getExpFrom(),
                vacancy.getExpTo(),
                vacancy.getIsActive(),
                userAccountService.findNameById(vacancy.getAuthorId())
        ));
    }

    private Vacancy convertIntoModel(VacancyDto vacancy) {
        log.info("Fetching category's id by category name {}", vacancy.getCategory());
        Integer categoryId = categoryService.findIdByName(vacancy.getCategory())
                .orElseThrow(() -> new CategoryNotFoundException("Category not found: " + vacancy.getCategory()));

        // TODO check for null & empty string etc.
        String[] name = vacancy.getAuthor().split(" ");
        Integer authorId = userAccountService.findIdBySurname(name[1]);
        log.info("Mapping Vacancy created by {} {} into Vacancy Model", name[0], name[1]);
        return new Vacancy(
                0,
                vacancy.getName(),
                vacancy.getDescription(),
                categoryId,
                vacancy.getSalary(),
                vacancy.getExpFrom(),
                vacancy.getExpTo(),
                vacancy.getIsActive(),
                authorId,
                LocalDateTime.now(),
                LocalDateTime.now()
        );

    }

    private VacancyDto checkFieldsForNullOrEmpty(Integer id, VacancyDto newVacancy){
        Optional<VacancyDto> oldVacancy = convert(vacancyDao.findVacancyById(id));
        if (oldVacancy.isEmpty()){
            throw new VacancyNotFoundException("Vacancy with id: " + id + " not found");
        }
        VacancyDto result = new VacancyDto();

        if (newVacancy.getName() == null || newVacancy.getName().isEmpty()) {
            result.setName(oldVacancy.get().getName());
        } else {
            result.setName(newVacancy.getName());
        }
        if (newVacancy.getDescription() == null || newVacancy.getDescription().isEmpty()) {
            result.setDescription(oldVacancy.get().getDescription());
        } else {
            result.setDescription(newVacancy.getDescription());
        }
        if (newVacancy.getCategory() == null || newVacancy.getCategory().isEmpty()) {
            result.setCategory(oldVacancy.get().getCategory());
        } else {
            result.setCategory(newVacancy.getCategory());
        }
        if (newVacancy.getSalary() == null || newVacancy.getSalary() == 0){
            result.setSalary(oldVacancy.get().getSalary());
        } else {
            result.setSalary(newVacancy.getSalary());
        }
        if (newVacancy.getExpFrom() == null || newVacancy.getExpFrom() == 0){
            result.setExpFrom(oldVacancy.get().getExpFrom());
        } else {
            result.setExpFrom(newVacancy.getExpFrom());
        }
        if (newVacancy.getExpTo() == null || newVacancy.getExpTo() == 0){
            result.setExpTo(oldVacancy.get().getExpTo());
        } else {
            result.setExpTo(newVacancy.getExpTo());
        }
        if (newVacancy.getAuthor() == null || newVacancy.getAuthor().isEmpty()){
            result.setAuthor(oldVacancy.get().getAuthor());
        } else {
            result.setAuthor(newVacancy.getAuthor());
        }
        if (newVacancy.getIsActive() == null){
            result.setIsActive(oldVacancy.get().getIsActive());
        } else {
            result.setIsActive(newVacancy.getIsActive());
        }
        return result;
    }

}


