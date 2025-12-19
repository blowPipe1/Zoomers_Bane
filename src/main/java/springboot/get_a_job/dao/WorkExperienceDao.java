package springboot.get_a_job.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import springboot.get_a_job.dto.EducationDto;
import springboot.get_a_job.dto.ResumeDto;
import springboot.get_a_job.dto.WorkExperienceDto;

@Component
@RequiredArgsConstructor
public class WorkExperienceDao {
    private final JdbcTemplate jdbcTemplate;

    public void addWorkExperience(ResumeDto resumeDto, Integer resumeId) {
        String sql = "insert into WORK_EXPERIENCE_INFO(resume_id, years, company_name, position, responsibilities)" +
                "values (?, ?, ?, ?, ?);";
        for (WorkExperienceDto workExp : resumeDto.getWorkExperience()) {
            jdbcTemplate.update(sql, resumeId, workExp.getYears(), workExp.getCompanyName(), workExp.getPosition(), workExp.getResponsibilities());
        }
    }

}