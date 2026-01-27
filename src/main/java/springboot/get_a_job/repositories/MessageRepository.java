package springboot.get_a_job.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import springboot.get_a_job.models.Message;

import java.util.List;


@Repository
public interface MessageRepository extends JpaRepository<Message, Integer> {
    List<Message> findByApplication_IdOrderByTimestampAsc(Integer applicationId);
}
