package springboot.get_a_job.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import springboot.get_a_job.models.Resume;

import java.util.List;
import java.util.Optional;

@Repository
public interface ResumeRepository extends JpaRepository<Resume, Integer> {

    List<Resume> findAllByIsActiveTrue();

    List<Resume> findAllByCategoryNameContainingIgnoreCase(String categoryName);

    List<Resume> findAllByCategoryId(Integer categoryId);

    List<Resume> findAllByApplicantId(Integer applicantId);

    List<Resume> findAllByApplicantNameContainingIgnoreCase(String name);

    @Query("SELECT r.name FROM Resume r WHERE r.id = :id")
    Optional<String> findResumeNameById(@Param("id") Integer id);

    @Query("SELECT r.id FROM Resume r WHERE r.name ILIKE :name")
    Optional<Integer> findIdByName(@Param("name") String name);
}
