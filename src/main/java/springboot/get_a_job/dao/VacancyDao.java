package springboot.get_a_job.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import springboot.get_a_job.models.Vacancy;

import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
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

    public List<Vacancy> findRespondedVacancies(Integer applicant_id) {
        String sql = "SELECT v.* FROM vacancies v\n" +
                "JOIN responded_applicants ra ON v.id = ra.vacancy_id\n" +
                "JOIN resumes r ON ra.resume_id = r.id\n" +
                "WHERE r.applicant_id = :id;";
        return namedParameterJdbcTemplate.query(
                sql,
                new MapSqlParameterSource()
                        .addValue("id", applicant_id),
                new BeanPropertyRowMapper<>(Vacancy.class));
    }

    public void createVacancy(String name, String description, Integer categoryId, Double salary, Integer expFrom, Integer expTo, Boolean isActive, Integer authorId) {
        String sql = "insert into VACANCIES(name, description, category_id, salary, exp_from, exp_To, is_Active, author_Id, created_date, update_time)" +
                "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
        jdbcTemplate.update(sql,
                name,
                description,
                categoryId,
                salary,
                expFrom,
                expTo,
                isActive,
                authorId,
                Timestamp.valueOf(LocalDateTime.now()),
                Timestamp.valueOf(LocalDateTime.now()));
    }

    public void updateVacancy(Integer vacancyId, String name, String description, Integer categoryId, Double salary, Integer expFrom, Integer expTo, Boolean isActive){
        String sql = "update VACANCIES set " +
                "name = ?," +
                "description = ?, " +
                "CATEGORY_ID = ?, " +
                "salary = ?," +
                "exp_from = ?," +
                "exp_to = ?, " +
                "is_active = ?, " +
                "update_time = ? where id = ?;";

        jdbcTemplate.update(sql,
                name,
                description,
                categoryId,
                salary,
                expFrom,
                expTo,
                isActive,
                Timestamp.valueOf(LocalDateTime.now()),
                vacancyId);

    }


}
