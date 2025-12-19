package springboot.get_a_job.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import springboot.get_a_job.dto.EducationDto;
import springboot.get_a_job.dto.ResumeDto;

@Component
@RequiredArgsConstructor
public class EducationInfoDao {
    private final JdbcTemplate jdbcTemplate;

    public void addEducationInfo(ResumeDto resumeDto, Integer resumeId) {
        String sql = "insert into EDUCATION_INFO(resume_id, institution, program, start_date, end_date, degree)" +
                "values (?, ?, ?, ?, ?, ?);";
        for (EducationDto edu : resumeDto.getEducation()){
            jdbcTemplate.update(sql, resumeId, edu.getInstitution(), edu.getProgram(), edu.getStartDate(), edu.getEndDate(), edu.getDegree());
        }
    }
}