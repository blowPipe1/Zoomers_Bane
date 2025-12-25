package springboot.get_a_job.dao;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import springboot.get_a_job.models.Resume;


import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class ResumeDao {
    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public List<Resume> getAllActiveResumes() {
        String sql = "select * from RESUMES";
        try {
            log.debug("Fetching Active Resumes");
            return jdbcTemplate.query(sql,new BeanPropertyRowMapper<>(Resume.class));
        } catch (EmptyResultDataAccessException e) {
            log.warn("No active Resumes found");
            return null;
        }

    }

    public Resume findResumeById(Integer id) {
        String sql = "select * from resumes where id = ?;";
        try {
            log.debug("Fetching Resume with ID: {}", id);
            return jdbcTemplate.queryForObject(sql,new BeanPropertyRowMapper<>(Resume.class), id);
        } catch (EmptyResultDataAccessException e) {
            log.warn("No Resume with ID: {} was found", id);
            return null;
        }

    }

    public List<Resume> findResumeByCategory(String category) {
        String sql = "SELECT r.* FROM resumes r JOIN categories c ON r.category_id = c.id WHERE c.name ilike :category;";
        try {
            log.debug("Fetching Resumes with Category: {}", category);
            return namedParameterJdbcTemplate.query(
                    sql,
                    new MapSqlParameterSource()
                            .addValue("category", category),
                    new BeanPropertyRowMapper<>(Resume.class));
        } catch (EmptyResultDataAccessException e) {
            log.warn("No Resumes with Category: {} were found", category);
            return null;
        }

    }


    public List<Resume> findResumeByCategory(Integer category_id) {
        String sql = "SELECT * FROM resumes WHERE category_id = :id;";
        try {
            log.debug("Fetching Resumes with Category(ID): {}", category_id);
            return namedParameterJdbcTemplate.query(
                    sql,
                    new MapSqlParameterSource()
                            .addValue("id", category_id),
                    new BeanPropertyRowMapper<>(Resume.class));
        } catch (EmptyResultDataAccessException e) {
            log.warn("No Resumes with Category(ID): {} were found", category_id);
            return null;
        }

    }

    public List<Resume> findResumeByCreator(Integer applicant_id) {
        String sql = "SELECT * FROM resumes WHERE applicant_id = :id;";
        try {
            log.debug("Fetching Resumes created by User(ID:) {}", applicant_id);
            return namedParameterJdbcTemplate.query(
                    sql,
                    new MapSqlParameterSource()
                            .addValue("id", applicant_id),
                    new BeanPropertyRowMapper<>(Resume.class));
        } catch (EmptyResultDataAccessException e) {
            log.warn("No Resumes created by User(ID): {} were found", applicant_id);
            return null;
        }

    }

    public List<Resume> findResumeByCreator(String creatorName) {
        String sql = "SELECT r.* FROM resumes r JOIN users u ON r.APPLICANT_ID = u.id WHERE u.name ilike :name;";
        try {
            log.debug("Fetching Resumes created by User: {}", creatorName);
            return namedParameterJdbcTemplate.query(
                    sql,
                    new MapSqlParameterSource()
                            .addValue("name", creatorName),
                    new BeanPropertyRowMapper<>(Resume.class));
        } catch (EmptyResultDataAccessException e) {
            log.warn("No Resumes created by User: {} were found", creatorName);
            return null;
        }

    }

    public Integer saveResume(Resume resume) {
        String sql = "INSERT INTO resumes (applicant_id, name, category_id, salary, is_active, created_date, update_time) VALUES (?, ?, ?, ?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        log.debug("Executing SQL to Save Resume: {}", sql);
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
            ps.setInt(1, resume.getApplicantId());
            ps.setString(2, resume.getName());
            ps.setInt(3, resume.getCategoryId());
            ps.setDouble(4, resume.getSalary());
            ps.setBoolean(5, resume.getIsActive());
            ps.setTimestamp(6, Timestamp.valueOf(LocalDateTime.now()));
            ps.setTimestamp(7, Timestamp.valueOf(LocalDateTime.now()));
            return ps;
        }, keyHolder);

        Number key = keyHolder.getKey();
        return (key != null) ? key.intValue() : null;
    }

    public Integer updateResume(Integer resumeId, Resume resume) {
        String sql = "update RESUMES set name = ?, CATEGORY_ID = ?, salary = ?, is_active = ?, UPDATE_TIME = ? where id = ?";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        log.debug("Executing SQL to Update Resume: {}", sql);
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
            ps.setString(1, resume.getName());
            ps.setInt(2, resume.getCategoryId());
            ps.setDouble(3, resume.getSalary());
            ps.setBoolean(4, resume.getIsActive());
            ps.setTimestamp(5, Timestamp.valueOf(LocalDateTime.now()));
            ps.setInt(6, resumeId);
            return ps;
        }, keyHolder);
        Number key = keyHolder.getKey();
        return (key != null) ? key.intValue() : null;
    }

    public void deleteResume(Integer resumeId) {
        String sql = "DELETE FROM resumes WHERE Id = ?;";
        log.debug("Executing SQL to Delete Resume: {}", sql);
        jdbcTemplate.update(sql, resumeId);
    }

    public String findResumeNameById(Integer id) {
        String sql = "SELECT name FROM RESUMES WHERE id ilike ?";
        try {
            log.debug("Fetching Resume's name with ID: {}", id);
            return jdbcTemplate.queryForObject(sql, String.class, id);
        } catch (EmptyResultDataAccessException e) {
            log.warn("No Matching Resume's name with ID: {} was found", id);
            return null;
        }
    }

    public Integer findResumeIdByName(String name) {
        String sql = "SELECT id FROM RESUMES WHERE NAME ilike ?";
        try {
            log.debug("Fetching Resume's id with ID: {}", name);
            return jdbcTemplate.queryForObject(sql, Integer.class, name);
        } catch (EmptyResultDataAccessException e) {
            log.warn("No Matching Resume's id with ID: {} was found", name);
            return null;
        }
    }

}
