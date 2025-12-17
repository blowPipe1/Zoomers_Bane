package springboot.get_a_job.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;
import springboot.get_a_job.models.Resume;


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
        return jdbcTemplate.queryForObject(sql,new BeanPropertyRowMapper<>(Resume.class), id);
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
        String sql = "SELECT r.* FROM resumes r JOIN users u ON r.category_id = u.id WHERE u.name ilike :name;";
        return namedParameterJdbcTemplate.query(
                sql,
                new MapSqlParameterSource()
                        .addValue("name", creatorName),
                new BeanPropertyRowMapper<>(Resume.class));
    }
}
