package springboot.get_a_job.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CategoryDao {
    private final JdbcTemplate jdbcTemplate;

    public Optional<Integer> findIdByName(String name) {
        String sql = "SELECT id FROM categories WHERE name = ?";
        try {
            Integer id = jdbcTemplate.queryForObject(sql, Integer.class, name);
            return Optional.ofNullable(id);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public String findNameById(Integer id) {
        String sql = "SELECT name FROM categories WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, String.class, id);
    }
}
