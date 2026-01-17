package springboot.get_a_job.serviceImplementations;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import springboot.get_a_job.dao.CategoryDao;
import springboot.get_a_job.models.Category;
import springboot.get_a_job.services.CategoryService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryDao categoryDao;

    @Override
    public Optional<Integer> findIdByName(String name) {
        return categoryDao.findIdByName(name);
    }

    @Override
    public String findNameById(Integer id) {
        return categoryDao.findNameById(id);
    }

    @Override
    public List<Category> findAll() {
        if (categoryDao.findAll() == null) {
            return new ArrayList<>();
        }
        return categoryDao.findAll();
    }
}
