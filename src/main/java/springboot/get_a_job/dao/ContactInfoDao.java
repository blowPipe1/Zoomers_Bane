package springboot.get_a_job.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;
import springboot.get_a_job.dto.ContactInfoDto;
import springboot.get_a_job.dto.EducationDto;
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

    public void addContactInfo(ContactInfo contactInfo, Integer resumeId) {
        String sql = "insert into CONTACT_INFO(resume_id, type_id, value)" +
                "values (?, ?, ?);";
        jdbcTemplate.update(sql, resumeId, contactInfo.getTypeId(), contactInfo.getValue());
    }

    public Integer findIdByName(String name) {
        String sql = "SELECT id FROM CONTACT_TYPES WHERE type ilike ?";
        try {
            return jdbcTemplate.queryForObject(sql, Integer.class, name);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public String findNameById(Integer id) {
        String sql = "SELECT type FROM CONTACT_TYPES WHERE id ilike ?";
        try {
            return jdbcTemplate.queryForObject(sql, String.class, id);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public void deleteContactInfo(Integer resumeId) {
        String sql = "delete from CONTACT_INFO where resume_id = ?;";
        jdbcTemplate.update(sql, resumeId);
    }

    public void updateContactInfo(ContactInfo contactInfo, Integer contactId) {
        String sql = "update CONTACT_INFO set TYPE_ID = ?, RESUME_ID = ?, VALUE = ? where id = ?";
        jdbcTemplate.update(sql, contactInfo.getTypeId(), contactInfo.getResumeId(), contactInfo.getValue(), contactId);
    }

    public ContactInfo findInfoByID(Integer id) {
        String sql = "select * from CONTACT_INFO where ID = ?;";
        try {
            return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(ContactInfo.class), id);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }
}
