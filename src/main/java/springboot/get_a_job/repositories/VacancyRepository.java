package springboot.get_a_job.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import springboot.get_a_job.models.Vacancy;

import java.util.List;

@Repository
public interface VacancyRepository extends JpaRepository<Vacancy, Integer> {
    Page<Vacancy> findAllByIsActiveTrue(Pageable pageable);

    List<Vacancy> findAllByCategoryNameContainingIgnoreCase(String categoryName);

    List<Vacancy> findAllByCategoryId(Integer categoryId);

    @Query("SELECT v FROM Vacancy v " +
            "JOIN RespondedApplicant ra ON v.id = ra.vacancy.id " +
            "JOIN Resume r ON ra.resume.id = r.id " +
            "WHERE r.applicant.id = :applicantId")
    List<Vacancy> findRespondedVacanciesByApplicantId(@Param("applicantId") Integer applicantId);

    Page<Vacancy> findAllByAuthorId(Integer authorId, Pageable pageable);

    List<Vacancy> findAllByAuthorId(Integer authorId);

    List<Vacancy> findAllByAuthorNameContainingIgnoreCase(String name);
}
