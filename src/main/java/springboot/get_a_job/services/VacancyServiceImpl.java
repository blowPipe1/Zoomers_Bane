package springboot.get_a_job.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import springboot.get_a_job.dao.CategoryDao;
import springboot.get_a_job.dao.UserDao;
import springboot.get_a_job.dao.VacancyDao;
import springboot.get_a_job.dto.ResumeDto;
import springboot.get_a_job.dto.VacancyDto;
import springboot.get_a_job.exceptions.CategoryNotFoundException;
import springboot.get_a_job.exceptions.VacancyNotFoundException;
import springboot.get_a_job.models.Vacancy;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class VacancyServiceImpl implements VacancyService {
    private final VacancyDao vacancyDao;
    private final CategoryDao categoryDao;
    private final UserDao userDao;

    @Override
    public void createVacancy(VacancyDto vacancy) {
        if (vacancy == null) {
            throw new VacancyNotFoundException("Vacancy cannot be null");
        }
        Integer categoryId = categoryDao.findIdByName(vacancy.getCategory())
                .orElseThrow(() -> new CategoryNotFoundException("Category not found: " + vacancy.getCategory()));

        // TODO check for null & empty string etc.
        String[] name = vacancy.getAuthor().split(" ");
        Integer authorId = Integer.valueOf(userDao.findIdBySurname(name[1]));
        Vacancy result = new Vacancy(
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

        vacancyDao.createVacancy(result);
    }

    @Override
    public void updateVacancy(Integer id, VacancyDto vacancy) {
        if (vacancy == null) {
            throw new VacancyNotFoundException("Vacancy cannot be null");
        }
        Integer categoryId = categoryDao.findIdByName(vacancy.getCategory())
                .orElseThrow(() -> new CategoryNotFoundException("Category not found: " + vacancy.getCategory()));

        String[] name = vacancy.getAuthor().split(" ");
        Integer authorId = Integer.valueOf(userDao.findIdBySurname(name[1]));

        Vacancy result = new Vacancy(
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
        vacancyDao.updateVacancy(id, result);
    }

    @Override
    public void deleteVacancy(Integer id) {
        if (vacancyDao.findVacancyById(id) == null) {
            throw new VacancyNotFoundException("Vacancy cannot be null");
        } else {
            vacancyDao.deleteVacancy(id);
        }
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
                            categoryDao.findNameById(vacancy.getCategoryId()),
                            vacancy.getSalary(),
                            vacancy.getExpFrom(),
                            vacancy.getExpTo(),
                            vacancy.getIsActive(),
                            userDao.findNameById(vacancy.getAuthorId())
                    )
            );
        }
        return Optional.of(vacancyDtos);
    }

    private Optional<VacancyDto>convert(Vacancy vacancy) {
        if (vacancy == null) {
            throw new VacancyNotFoundException("Vacancy not found");
        }
        return Optional.of(new VacancyDto(
                vacancy.getName(),
                vacancy.getDescription(),
                categoryDao.findNameById(vacancy.getCategoryId()),
                vacancy.getSalary(),
                vacancy.getExpFrom(),
                vacancy.getExpTo(),
                vacancy.getIsActive(),
                userDao.findNameById(vacancy.getAuthorId())
        ));
    }


}


