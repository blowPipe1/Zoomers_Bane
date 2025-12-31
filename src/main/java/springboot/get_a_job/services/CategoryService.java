package springboot.get_a_job.services;

import java.util.Optional;

public interface CategoryService {
    Optional<Integer> findIdByName(String name);
    String findNameById(Integer id);
}
