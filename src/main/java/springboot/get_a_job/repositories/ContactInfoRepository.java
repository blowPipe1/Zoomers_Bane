package springboot.get_a_job.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import springboot.get_a_job.models.ContactInfo;

import java.util.List;

@Repository
public interface ContactInfoRepository extends JpaRepository<ContactInfo, Integer> {
    List<ContactInfo> findByResumeId(Integer resumeId);

    @Modifying
    @Transactional
    void deleteByResumeId(Integer resumeId);
}
