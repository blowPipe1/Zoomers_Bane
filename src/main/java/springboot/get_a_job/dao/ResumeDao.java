package springboot.get_a_job.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import springboot.get_a_job.dto.ResumeDto;
import springboot.get_a_job.models.Resume;


import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ResumeDao {
    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public List<Resume> getAllActiveResumes() {
        String sql = "select * from RESUMES";
        return jdbcTemplate.query(sql,new BeanPropertyRowMapper<>(Resume.class));
    }

    public Resume findResumeById(Integer id) {
        String sql = "select * from resumes where id = ?;";
        try {
            return jdbcTemplate.queryForObject(sql,new BeanPropertyRowMapper<>(Resume.class), id);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }

    }

    public List<Resume> findResumeByCategory(String category) {
        String sql = "SELECT r.* FROM resumes r JOIN categories c ON r.category_id = c.id WHERE c.name ilike :category;";
        return namedParameterJdbcTemplate.query(
                sql,
                new MapSqlParameterSource()
                        .addValue("category", category),
                new BeanPropertyRowMapper<>(Resume.class));
    }


    public List<Resume> findResumeByCategory(Integer category_id) {
        String sql = "SELECT * FROM resumes WHERE category_id = :id;";
        return namedParameterJdbcTemplate.query(
                sql,
                new MapSqlParameterSource()
                        .addValue("id", category_id),
                new BeanPropertyRowMapper<>(Resume.class));
    }

    public List<Resume> findResumeByCreator(Integer applicant_id) {
        String sql = "SELECT * FROM resumes WHERE applicant_id = :id;";
        return namedParameterJdbcTemplate.query(
                sql,
                new MapSqlParameterSource()
                        .addValue("id", applicant_id),
                new BeanPropertyRowMapper<>(Resume.class));
    }

    public List<Resume> findResumeByCreator(String creatorName) {
        String sql = "SELECT r.* FROM resumes r JOIN users u ON r.APPLICANT_ID = u.id WHERE u.name ilike :name;";
        return namedParameterJdbcTemplate.query(
                sql,
                new MapSqlParameterSource()
                        .addValue("name", creatorName),
                new BeanPropertyRowMapper<>(Resume.class));
    }

    public Integer saveResume(Integer applicantId, String name, Integer categoryId, Double salary, boolean isActive) {
        String sql = "INSERT INTO resumes (applicant_id, name, category_id, salary, is_active, created_date, update_time) VALUES (?, ?, ?, ?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
            ps.setInt(1, applicantId);
            ps.setString(2, name);
            ps.setInt(3, categoryId);
            ps.setDouble(4, salary);
            ps.setBoolean(5, isActive);
            ps.setTimestamp(6, Timestamp.valueOf(LocalDateTime.now()));
            ps.setTimestamp(7, Timestamp.valueOf(LocalDateTime.now()));
            return ps;
        }, keyHolder);

        Number key = keyHolder.getKey();
        return (key != null) ? key.intValue() : null;
    }

    public Integer updateResume(Integer resumeId, Integer applicantId, String name, Integer categoryId, Double salary, boolean isActive) {

        String sql = "update RESUMES set name = ?, CATEGORY_ID = ?, salary = ?, is_active = ?, UPDATE_TIME = ? where id = ?";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
            ps.setString(1, name);
            ps.setInt(2, categoryId);
            ps.setDouble(3, salary);
            ps.setBoolean(4, isActive);
            ps.setTimestamp(5, Timestamp.valueOf(LocalDateTime.now()));
            ps.setInt(6, resumeId);
            return ps;
        }, keyHolder);

        Number key = keyHolder.getKey();
        return (key != null) ? key.intValue() : null;
    }

    public void deleteResume(Integer resumeId) {
        String sql = "DELETE FROM resumes WHERE Id = ?;";
        jdbcTemplate.update(sql, resumeId);
    }

}
