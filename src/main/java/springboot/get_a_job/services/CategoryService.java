package springboot.get_a_job.services;

import springboot.get_a_job.models.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryService {
    Optional<Integer> findIdByName(String name);
    String findNameById(Integer id);
    List<Category> findAll();
}
