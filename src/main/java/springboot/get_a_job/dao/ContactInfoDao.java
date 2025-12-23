package springboot.get_a_job.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;
import springboot.get_a_job.dto.ContactInfoDto;
import springboot.get_a_job.models.ContactInfo;
import springboot.get_a_job.models.User;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor

public class ContactInfoDao {
    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public List<ContactInfoDto> getResumesContacts(Integer resumeId) {
        String sql = "SELECT " +
                "  ci.ID, " +
                "  ct.TYPE as type, " +
                "  r.NAME as resume, " +
                "  ci.VALUE " +
                "FROM CONTACT_INFO ci " +
                "JOIN CONTACT_TYPES ct ON ct.ID = ci.TYPE_ID " +
                "JOIN RESUMES r ON r.ID = ci.RESUME_ID " +
                "WHERE ci.RESUME_ID = :id";

        return namedParameterJdbcTemplate.query(
                sql,
                new MapSqlParameterSource().addValue("id", resumeId),
                new BeanPropertyRowMapper<>(ContactInfoDto.class)); // Используем DTO
    }
}
