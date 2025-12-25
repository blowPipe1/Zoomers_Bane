package springboot.get_a_job.dao;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
public class CategoryDao {
    private final JdbcTemplate jdbcTemplate;

    public Optional<Integer> findIdByName(String name) {
        String sql = "SELECT id FROM categories WHERE name ilike ?";
        try {
            Integer id = jdbcTemplate.queryForObject(sql, Integer.class, name);
            log.debug("Fetching an ID: {} of Category with name: {}", id, name);
            return Optional.ofNullable(id);
        } catch (EmptyResultDataAccessException e) {
            log.warn("ID for Category with name {} not found", name);
            return Optional.empty();
        }
    }

    public String findNameById(Integer id) {
        String sql = "SELECT name FROM categories WHERE id = ?";
        try {
            log.debug("Fetching a name of Category with ID: {}", id);
            return jdbcTemplate.queryForObject(sql, String.class, id);
        } catch (EmptyResultDataAccessException e) {
            log.warn("Category with id {} not found", id);
            return null;
        }

    }
}
