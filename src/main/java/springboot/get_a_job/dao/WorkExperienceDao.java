
package springboot.get_a_job.dao;

import lombok.RequiredArgsConstructor;
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

    public void addWorkExperience(ResumeDto resumeDto, Integer resumeId) {
        String sql = "insert into WORK_EXPERIENCE_INFO(resume_id, years, company_name, position, responsibilities)" +
                "values (?, ?, ?, ?, ?);";
        for (WorkExperienceDto workExp : resumeDto.getWorkExperience()) {
            jdbcTemplate.update(sql, resumeId, workExp.getYears(), workExp.getCompanyName(), workExp.getPosition(), workExp.getResponsibilities());
        }
    }

    public void updateWorkExperience(ResumeDto resumeDto, Integer resumeId) {
        String sql = "update WORK_EXPERIENCE_INFO set years = ?, company_name = ?, position = ?, responsibilities = ? where id = ?";
        for (WorkExperienceDto workExp : resumeDto.getWorkExperience()) {
            jdbcTemplate.update(sql, workExp.getYears(), workExp.getCompanyName(), workExp.getPosition(), workExp.getResponsibilities(), resumeId);
        }
    }

    public void deleteWorkExperienceInfo(Integer resumeId) {
        String sql = "delete from WORK_EXPERIENCE_INFO where resume_id = ?;";
        jdbcTemplate.update(sql, resumeId);
    }

}
