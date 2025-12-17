package springboot.get_a_job.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import springboot.get_a_job.dao.VacancyDao;
import springboot.get_a_job.models.Vacancy;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class VacancyServiceImpl implements VacancyService {
    private final VacancyDao vacancyDao;

    @Override
    public Vacancy createVacancy(Vacancy vacancy) {
        System.out.println("Creating Vacancy: " + vacancy.getName());
        //TODO логика сохранения в БД и возврата сохраненного объекта

        // заглушка
        return vacancy;
    }

    @Override
    public Vacancy updateVacancy(Integer id, Vacancy vacancyDetails) {
        System.out.println("Updating Vacancy (ID) " + id);
        //TODO Логика обновления записи в БД

        //заглушка
        vacancyDetails.setId(id);
        vacancyDetails.setUpdateTime(LocalDateTime.now());
        return vacancyDetails;
    }

    @Override
    public void deleteVacancy(Integer id) {
        System.out.println("Deleting Vacancy (ID) " + id);
        //TODO Логика удаления записи из БД
    }

    @Override
    public void respondToVacancy(Integer vacancyId, Integer resumeId) {

    }


    @Override
    public Optional<List<Vacancy>> getAllActiveVacancies() {
        if (vacancyDao.getAllActiveVacancies().isEmpty()) {
            return Optional.empty();
        } else {
            return Optional.of(vacancyDao.getAllActiveVacancies());
        }
    }

    @Override
    public Optional<Vacancy> findVacancyById(Integer id) {
        if (vacancyDao.findVacancyById(id) == null) {
            return Optional.empty();
        } else {
            return Optional.of(vacancyDao.findVacancyById(id));
        }
    }

    @Override
    public Optional<List<Vacancy>> findVacancyByCategory(Integer category_id) {
        if (vacancyDao.findVacancyByCategory(category_id).isEmpty()) {
            return Optional.empty();
        } else {
            return Optional.of(vacancyDao.findVacancyByCategory(category_id));
        }
    }

    @Override
    public Optional<List<Vacancy>> findVacancyByCategory(String category) {
        if (vacancyDao.findVacancyByCategory(category).isEmpty()) {
            return Optional.empty();
        } else {
            return Optional.of(vacancyDao.findVacancyByCategory(category));
        }
    }


}


