package springboot.get_a_job.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import springboot.get_a_job.models.RespondedApplicant;

import java.util.List;
import java.util.Optional;

@Repository
public interface RespondedApplicantRepository extends JpaRepository<RespondedApplicant, Integer> {
    List<RespondedApplicant> findByResumeId(Integer id);
    List<RespondedApplicant> findByVacancy_Id(Integer id);
    List<RespondedApplicant> findByResume_Applicant_Id(Integer userId);
    List<RespondedApplicant> findByVacancy_Author_Id(Integer userId);
    Optional<RespondedApplicant> findById(Integer id);
}
