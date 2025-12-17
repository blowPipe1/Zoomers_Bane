package springboot.get_a_job.services;

import springboot.get_a_job.models.Vacancy;

import java.util.List;
import java.util.Optional;

public interface VacancyService {
    Vacancy createVacancy(Vacancy vacancy);
    Vacancy updateVacancy(Integer id, Vacancy vacancyDetails);
    void deleteVacancy(Integer id);
    void respondToVacancy(Integer vacancyId, Integer resumeId);
    Optional<List<Vacancy>>getAllActiveVacancies();
    Optional<Vacancy>findVacancyById(Integer id);
    Optional<List<Vacancy>> findVacancyByCategory(Integer category_id);
    Optional<List<Vacancy>> findVacancyByCategory(String category);
    Optional<List<Vacancy>> findRespondedVacancies(Integer applicant_id);

}
