package springboot.get_a_job.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import springboot.get_a_job.models.Category;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {
    Optional<Integer> findIdByNameIgnoreCase(String name);

    @Query("SELECT c.name FROM Category c WHERE c.id = :id")
    String findCategoryNameById(Integer id);
}
