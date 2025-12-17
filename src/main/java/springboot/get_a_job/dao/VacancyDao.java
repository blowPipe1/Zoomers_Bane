package springboot.get_a_job.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;
import springboot.get_a_job.models.Vacancy;

import java.util.List;

@Component
@RequiredArgsConstructor
public class VacancyDao {
    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public List<Vacancy> getAllActiveVacancies() {
        String sql = "select * from VACANCIES";
        return jdbcTemplate.query(sql,new BeanPropertyRowMapper<>(Vacancy.class));
    }

    public Vacancy findVacancyById(Integer id) {
        String sql = "select * from vacancies where id = ?;";
        return jdbcTemplate.queryForObject(sql,new BeanPropertyRowMapper<>(Vacancy.class), id);
    }

    public List<Vacancy> findVacancyByCategory(String category) {
        String sql = "SELECT r.* FROM vacancies r JOIN categories c ON r.category_id = c.id WHERE c.name ilike :category;";
        return namedParameterJdbcTemplate.query(
                sql,
                new MapSqlParameterSource()
                        .addValue("category", category),
                new BeanPropertyRowMapper<>(Vacancy.class));
    }


    public List<Vacancy> findVacancyByCategory(Integer category_id) {
        String sql = "SELECT * FROM vacancies WHERE category_id = :id;";
        return namedParameterJdbcTemplate.query(
                sql,
                new MapSqlParameterSource()
                        .addValue("id", category_id),
                new BeanPropertyRowMapper<>(Vacancy.class));
    }



}
