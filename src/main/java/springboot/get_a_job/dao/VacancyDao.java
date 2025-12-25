package springboot.get_a_job.dao;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;
import springboot.get_a_job.models.Vacancy;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class VacancyDao {
    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public List<Vacancy> getAllActiveVacancies() {
        String sql = "select * from VACANCIES";
        try {
            log.debug("Fetching all vacancies");
            return jdbcTemplate.query(sql,new BeanPropertyRowMapper<>(Vacancy.class));
        } catch (EmptyResultDataAccessException e) {
            log.warn("No vacancies were found");
            return null;
        }
    }

    public Vacancy findVacancyById(Integer id) {
        String sql = "select * from vacancies where id = ?;";
        try {
            log.debug("Fetching vacancy with id {}", id);
            return jdbcTemplate.queryForObject(sql,new BeanPropertyRowMapper<>(Vacancy.class), id);
        } catch (EmptyResultDataAccessException e) {
            log.warn("No vacancy with ID: {} was found", id);
            return null;
        }
    }

    public List<Vacancy> findVacancyByCategory(String category) {
        String sql = "SELECT r.* FROM vacancies r JOIN categories c ON r.category_id = c.id WHERE c.name ilike :category;";
        try {
            log.debug("Fetching vacancies with Category: {}", category);
            return namedParameterJdbcTemplate.query(
                    sql,
                    new MapSqlParameterSource()
                            .addValue("category", category),
                    new BeanPropertyRowMapper<>(Vacancy.class));
        } catch (EmptyResultDataAccessException e) {
            log.warn("No vacancies with Category: {} were found", category);
            return null;
        }
    }

    public List<Vacancy> findVacancyByCategory(Integer category_id) {
        String sql = "SELECT * FROM vacancies WHERE category_id = :id;";
        try {
            log.debug("Fetching vacancies with Category(ID): {}", category_id);
            return namedParameterJdbcTemplate.query(
                    sql,
                    new MapSqlParameterSource()
                            .addValue("id", category_id),
                    new BeanPropertyRowMapper<>(Vacancy.class));
        } catch (EmptyResultDataAccessException e) {
            log.warn("No vacancies with Category(ID): {} were found", category_id);
            return null;
        }
    }

    public List<Vacancy> findRespondedVacancies(Integer applicant_id) {
        String sql = "SELECT v.* FROM vacancies v\n" +
                "JOIN responded_applicants ra ON v.id = ra.vacancy_id\n" +
                "JOIN resumes r ON ra.resume_id = r.id\n" +
                "WHERE r.applicant_id = :id;";
        try {
            log.debug("Fetching responded vacancies with Applicant ID: {}", applicant_id);
            return namedParameterJdbcTemplate.query(
                    sql,
                    new MapSqlParameterSource()
                            .addValue("id", applicant_id),
                    new BeanPropertyRowMapper<>(Vacancy.class));
        } catch (EmptyResultDataAccessException e) {
            log.warn("No responded vacancies with Applicant ID: {} were found", applicant_id);
            return null;
        }
    }

    public List<Vacancy> findVacancyByCreator(Integer author_id) {
        String sql = "SELECT * FROM VACANCIES WHERE AUTHOR_ID = :id;";
        try {
            log.debug("Fetching vacancies created by User(ID): {}", author_id);
            return namedParameterJdbcTemplate.query(
                    sql,
                    new MapSqlParameterSource()
                            .addValue("id", author_id),
                    new BeanPropertyRowMapper<>(Vacancy.class));
        } catch (EmptyResultDataAccessException e) {
            log.warn("No vacancies created by User(ID): {} were found", author_id);
            return null;
        }
    }

    public List<Vacancy> findVacancyByCreator(String creatorName) {
        String sql = "SELECT r.* FROM VACANCIES r JOIN users u ON r.AUTHOR_ID = u.id WHERE u.name ilike :name;";
        try {
            log.debug("Fetching vacancies created by User with name: {}", creatorName);
            return namedParameterJdbcTemplate.query(
                    sql,
                    new MapSqlParameterSource()
                            .addValue("name", creatorName),
                    new BeanPropertyRowMapper<>(Vacancy.class));
        } catch (EmptyResultDataAccessException e) {
            log.warn("No vacancies created by User with name: {} were found", creatorName);
            return null;
        }

    }

    public void createVacancy(Vacancy vacancy) {
        String sql = "insert into VACANCIES(name, description, category_id, salary, exp_from, exp_To, is_Active, author_Id, created_date, update_time)" +
                "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
        log.debug("Executing SQL to Save Vacancy: {}", sql);
        jdbcTemplate.update(sql,
                vacancy.getName(),
                vacancy.getDescription(),
                vacancy.getCategoryId(),
                vacancy.getSalary(),
                vacancy.getExpFrom(),
                vacancy.getExpTo(),
                vacancy.getIsActive(),
                vacancy.getAuthorId(),
                Timestamp.valueOf(LocalDateTime.now()),
                Timestamp.valueOf(LocalDateTime.now()));
    }

    public void updateVacancy(Integer vacancyId, Vacancy vacancy){
        String sql = "update VACANCIES set " +
                "name = ?," +
                "description = ?, " +
                "CATEGORY_ID = ?, " +
                "salary = ?," +
                "exp_from = ?," +
                "exp_to = ?, " +
                "is_active = ?, " +
                "update_time = ? where id = ?;";

        log.debug("Executing SQL to Update Vacancy: {}", sql);
        jdbcTemplate.update(sql,
                vacancy.getName(),
                vacancy.getDescription(),
                vacancy.getCategoryId(),
                vacancy.getSalary(),
                vacancy.getExpFrom(),
                vacancy.getExpTo(),
                vacancy.getIsActive(),
                Timestamp.valueOf(LocalDateTime.now()),
                vacancyId);

    }

    public void deleteVacancy(Integer vacancyId){
        String sql = "delete from VACANCIES where id = ?;";
        log.debug("Executing SQL to Delete Vacancy: {}", sql);
        jdbcTemplate.update(sql, vacancyId);
    }


}
