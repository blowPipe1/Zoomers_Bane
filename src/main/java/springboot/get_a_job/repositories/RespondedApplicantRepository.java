package springboot.get_a_job.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import springboot.get_a_job.models.RespondedApplicant;

@Repository
public interface RespondedApplicantRepository extends JpaRepository<RespondedApplicant, Integer> {
}
