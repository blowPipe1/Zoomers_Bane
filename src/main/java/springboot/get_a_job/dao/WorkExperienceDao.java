
package springboot.get_a_job.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;
import springboot.get_a_job.dto.EducationDto;
import springboot.get_a_job.dto.ResumeDto;
import springboot.get_a_job.dto.WorkExperienceDto;

import java.util.List;

@Component
@RequiredArgsConstructor
public class WorkExperienceDao {
    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public List<WorkExperienceDto> getResumesWorkExperience(Integer resumeId) {
        String sql = "SELECT * FROM work_experience_info WHERE resume_id = :id;";
        return namedParameterJdbcTemplate.query(
                sql,
                new MapSqlParameterSource()
                        .addValue("id", resumeId),
                new BeanPropertyRowMapper<>(WorkExperienceDto.class));
    }

    public WorkExperienceDto findInfoById(int id) {
        String sql = "select * from WORK_EXPERIENCE_INFO where id = ?;";
        try {
            return jdbcTemplate.queryForObject(sql,new BeanPropertyRowMapper<>(WorkExperienceDto.class), id);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }


    public void addWorkExperience(WorkExperienceDto workExp, Integer resumeId) {
        String sql = "insert into WORK_EXPERIENCE_INFO(resume_id, years, company_name, position, responsibilities)" +
                "values (?, ?, ?, ?, ?);";
        jdbcTemplate.update(sql, resumeId, workExp.getYears(), workExp.getCompanyName(), workExp.getPosition(), workExp.getResponsibilities());

    }

    public void updateWorkExperience(WorkExperienceDto workExp, Integer id) {
        String sql = "update WORK_EXPERIENCE_INFO set years = ?, company_name = ?, position = ?, responsibilities = ? where id = ?";
        jdbcTemplate.update(sql, workExp.getYears(), workExp.getCompanyName(), workExp.getPosition(), workExp.getResponsibilities(), id);
    }

    public void deleteWorkExperienceInfo(Integer resumeId) {
        String sql = "delete from WORK_EXPERIENCE_INFO where resume_id = ?;";
        jdbcTemplate.update(sql, resumeId);
    }

}
