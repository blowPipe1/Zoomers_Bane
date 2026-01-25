package springboot.get_a_job.services;

import springboot.get_a_job.dto.VacancyDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface VacancyService {
    void createVacancy(VacancyDto vacancy);
    void updateVacancy(Integer id, VacancyDto vacancy);
    Page<VacancyDto>getAllActiveVacancies(Pageable pageable);
    Optional<VacancyDto>findVacancyById(Integer id);
    Page<VacancyDto> findVacancyByCreator(Integer applicant_id, Pageable pageable);
    List<VacancyDto> findAllByAuthorId(Integer authorId);
}
