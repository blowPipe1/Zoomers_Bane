package springboot.get_a_job.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import springboot.get_a_job.models.Resume;

import java.util.List;
import java.util.Optional;

@Repository
public interface ResumeRepository extends JpaRepository<Resume, Integer> {

    Page<Resume> findAllByIsActiveTrue(Pageable pageable);

    List<Resume> findAllByCategoryNameContainingIgnoreCase(String categoryName);

    List<Resume> findAllByCategoryId(Integer categoryId);

    Page<Resume> findAllByApplicantId(Integer applicantId, Pageable pageable);

    Page<Resume> findAllByApplicantIdAndIsActiveTrue(Integer applicantId, Pageable pageable);

    List<Resume> findAllByApplicantId(Integer applicantId);

    List<Resume> findAllByApplicantNameContainingIgnoreCase(String name);

    @Query("SELECT r.name FROM Resume r WHERE r.id = :id")
    Optional<String> findResumeNameById(@Param("id") Integer id);

    @Query("SELECT r.id FROM Resume r WHERE r.name ILIKE :name")
    Optional<Integer> findIdByName(@Param("name") String name);

    Page<Resume> findAllByIsActiveTrueAndNameContainingIgnoreCase(Pageable pageable, String name);
}
