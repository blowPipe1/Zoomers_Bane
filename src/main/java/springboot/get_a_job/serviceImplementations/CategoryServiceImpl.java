package springboot.get_a_job.serviceImplementations;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import springboot.get_a_job.models.Category;
import springboot.get_a_job.repositories.CategoryRepository;
import springboot.get_a_job.services.CategoryService;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    public Optional<Integer> findIdByName(String name) {
        return categoryRepository.findIdByNameIgnoreCase(name);
    }

    @Override
    public String findNameById(Integer id) {

        return categoryRepository.findById(id)
                .map(Category::getName)
                .orElse(null);
    }

    @Override
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }
}
