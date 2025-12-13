package springboot.get_a_job.services;

import springboot.get_a_job.models.RespondedApplicant;
import springboot.get_a_job.models.Resume;
import springboot.get_a_job.models.Vacancy;

import java.util.List;

public interface VacancyService {
    Vacancy createVacancy(Vacancy vacancy);
    Vacancy updateVacancy(Integer id, Vacancy vacancyDetails);
    void deleteVacancy(Integer id);
    List<Resume> findAllActiveResumes();
    List<Resume> findResumesByCategoryId(Integer categoryId);
    List<RespondedApplicant> findApplicantsForVacancy(Integer vacancyId);
}
