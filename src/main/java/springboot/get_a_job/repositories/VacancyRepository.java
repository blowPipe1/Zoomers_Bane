package springboot.get_a_job.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import springboot.get_a_job.models.Vacancy;

import java.util.List;

@Repository
public interface VacancyRepository extends JpaRepository<Vacancy, Integer> {
    // 1. getAllActiveVacancies
    List<Vacancy> findAllByIsActiveTrue();

    // 2. findVacancyByCategory (по имени категории)
    List<Vacancy> findAllByCategoryNameContainingIgnoreCase(String categoryName);

    // 3. findVacancyByCategory (по ID категории)
    List<Vacancy> findAllByCategoryId(Integer categoryId);

    // 4. findRespondedVacancies (Вакансии, на которые откликнулся соискатель)
    @Query("SELECT v FROM Vacancy v " +
            "JOIN RespondedApplicant ra ON v.id = ra.vacancy.id " +
            "JOIN Resume r ON ra.resume.id = r.id " +
            "WHERE r.applicant.id = :applicantId")
    List<Vacancy> findRespondedVacanciesByApplicantId(@Param("applicantId") Integer applicantId);

    // 5. findVacancyByCreator (по ID автора)
    List<Vacancy> findAllByAuthorId(Integer authorId);

    // 6. findVacancyByCreator (по имени автора)
    List<Vacancy> findAllByAuthorNameContainingIgnoreCase(String name);
}
