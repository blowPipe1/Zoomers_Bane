package springboot.get_a_job.services;

import springboot.get_a_job.models.Resume;
import springboot.get_a_job.models.Vacancy;

import java.util.List;

public interface ResumeService {
    Resume createResume(Resume resume);
    Resume updateResume(Integer id, Resume resumeDetails);
    void deleteResume(Integer id);
    List<Vacancy> findAllActiveVacancies();
    List<Vacancy> findVacanciesByCategoryId(Integer categoryId);
    void respondToVacancy(Integer vacancyId, Integer resumeId);
}
