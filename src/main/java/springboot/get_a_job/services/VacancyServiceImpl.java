package springboot.get_a_job.services;

import org.springframework.stereotype.Service;
import springboot.get_a_job.models.RespondedApplicant;
import springboot.get_a_job.models.Resume;
import springboot.get_a_job.models.Vacancy;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@Service
public class VacancyServiceImpl implements VacancyService {

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
    public List<Resume> findAllActiveResumes() {
        System.out.println("Active Resumes");
        //TODO Логика запроса к БД

        // заглушка
        return Collections.emptyList();
    }

    @Override
    public List<Resume> findResumesByCategoryId(Integer categoryId) {
        System.out.println("Resume by Category (ID): " + categoryId);
        //TODO Логика запроса к БД с фильтром по категории

        // заглушка
        return Collections.emptyList();
    }

    @Override
    public List<RespondedApplicant> findApplicantsForVacancy(Integer vacancyId) {
        System.out.println("Replies for Vacancy (ID): " + vacancyId);
        //TODO Логика запроса к БД с объединением таблиц откликов и соискателей

        // заглушка
        return Collections.emptyList();
    }
}


