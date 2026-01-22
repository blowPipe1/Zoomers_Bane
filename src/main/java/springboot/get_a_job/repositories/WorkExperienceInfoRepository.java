package springboot.get_a_job.repositories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import springboot.get_a_job.models.WorkExperienceInfo;

import java.util.List;

@Repository
public interface WorkExperienceInfoRepository extends JpaRepository<WorkExperienceInfo, Integer> {
    List<WorkExperienceInfo> findAllByResumeId(Integer resumeId);

    void deleteAllByResumeId(Integer resumeId);
}
