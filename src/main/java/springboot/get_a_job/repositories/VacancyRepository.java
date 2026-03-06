package springboot.get_a_job.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import springboot.get_a_job.models.Vacancy;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface VacancyRepository extends JpaRepository<Vacancy, Integer> {
    Page<Vacancy> findAllByIsActiveTrue(Pageable pageable);

    List<Vacancy> findAllByCategoryId(Integer categoryId);

    @Query("SELECT v FROM Vacancy v " +
            "JOIN RespondedApplicant ra ON v.id = ra.vacancy.id " +
            "JOIN Resume r ON ra.resume.id = r.id " +
            "WHERE r.applicant.id = :applicantId")
    List<Vacancy> findRespondedVacanciesByApplicantId(@Param("applicantId") Integer applicantId);

    Page<Vacancy> findAllByAuthorId(Integer authorId, Pageable pageable);

    Page<Vacancy> findAllByAuthorIdAndIsActiveTrue(Integer authorId, Pageable pageable);

    List<Vacancy> findAllByAuthorId(Integer authorId);

    List<Vacancy> findAllByAuthorNameContainingIgnoreCase(String name);

    Page<Vacancy> findAllByIsActiveTrueAndNameContainingIgnoreCase(String name, Pageable pageable);

    @Query("SELECT v FROM Vacancy v " +
            "LEFT JOIN v.category c " +
            "WHERE v.isActive = true " +
            "AND (:name IS NULL OR LOWER(v.name) LIKE LOWER(CONCAT('%', :name, '%'))) " +
            "AND (:category IS NULL OR LOWER(c.name) LIKE LOWER(CONCAT('%', :category, '%')))")
    Page<Vacancy> searchVacancies(
            @Param("name") String name,
            @Param("category") String category,
            Pageable pageable
    );

    @Modifying
    @Transactional
    @Query("UPDATE Vacancy r SET r.updateTime = :now WHERE r.id = :id")
    void refreshVacancy(@Param("id") Integer id, @Param("now") LocalDateTime now);
}
