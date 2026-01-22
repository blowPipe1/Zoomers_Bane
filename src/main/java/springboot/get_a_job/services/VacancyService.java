package springboot.get_a_job.services;

import springboot.get_a_job.dto.VacancyDto;
import springboot.get_a_job.models.Vacancy;

import java.util.List;
import java.util.Optional;

public interface VacancyService {
    void createVacancy(VacancyDto vacancy);
    void updateVacancy(Integer id, VacancyDto vacancy);
    Optional<List<VacancyDto>>getAllActiveVacancies();
    Optional<VacancyDto>findVacancyById(Integer id);
    Optional<List<VacancyDto>> findVacancyByCreator(Integer applicant_id);

}
