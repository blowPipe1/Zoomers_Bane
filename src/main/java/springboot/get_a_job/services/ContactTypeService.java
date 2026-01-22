package springboot.get_a_job.services;

import springboot.get_a_job.models.ContactType;

import java.util.List;
import java.util.Optional;

public interface ContactTypeService {
    Optional<ContactType> findByTypeIgnoreCase(String type);
    Optional<Integer> findIdByTypeIgnoreCase(String type);
    String findTypeNameById(Integer id);
    List<ContactType>findAll();
}
