package springboot.get_a_job.services;

import springboot.get_a_job.dto.VacancyDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import springboot.get_a_job.models.Vacancy;

import java.util.List;
import java.util.Optional;

public interface VacancyService {
    void createVacancy(VacancyDto vacancy);
    void updateVacancy(Integer id, VacancyDto vacancy);
    Page<VacancyDto> getAllActiveVacancies(Pageable pageable, String name);
    Optional<VacancyDto>findVacancyById(Integer id);
    Page<VacancyDto> findVacancyByCreator(Integer applicant_id, Pageable pageable);
    Page<VacancyDto> findActiveVacanciesByCreator(Integer authorId, Pageable pageable);
    List<VacancyDto> findAllByAuthorId(Integer authorId);
    Vacancy convertIntoModel(VacancyDto dto);
}
