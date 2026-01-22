package springboot.get_a_job.repositories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import springboot.get_a_job.models.ContactType;

import java.util.Optional;

@Repository
public interface ContactTypeRepository extends JpaRepository<ContactType, Integer> {
    Optional<Integer> findIdByTypeIgnoreCase(String type);

    @Query("SELECT ct.type FROM ContactType ct WHERE ct.id = :id")
    String findTypeNameById(Integer id);

    Optional<ContactType> findByTypeIgnoreCase(String type);
}
