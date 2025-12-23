package springboot.get_a_job.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;
import springboot.get_a_job.dto.EducationDto;
import springboot.get_a_job.dto.ResumeDto;
import springboot.get_a_job.models.Resume;

import java.util.List;

@Component
@RequiredArgsConstructor
public class EducationInfoDao {
    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public List<EducationDto> getResumesEducationInfo(Integer resumeId){
        String sql = "SELECT * FROM education_info WHERE resume_id = :id;";
        return namedParameterJdbcTemplate.query(
                sql,
                new MapSqlParameterSource()
                        .addValue("id", resumeId),
                new BeanPropertyRowMapper<>(EducationDto.class));
    }

    public void addEducationInfo(EducationDto edu, Integer resumeId) {
        String sql = "insert into EDUCATION_INFO(resume_id, institution, program, start_date, end_date, degree)" +
                "values (?, ?, ?, ?, ?, ?);";
        jdbcTemplate.update(sql, resumeId, edu.getInstitution(), edu.getProgram(), edu.getStartDate(), edu.getEndDate(), edu.getDegree());
    }

    public void updateEducationInfo(EducationDto edu, Integer id) {
        String sql = "update EDUCATION_INFO set institution = ?, program = ?, start_date = ?, end_date = ?, degree = ? where id = ?";
        jdbcTemplate.update(sql, edu.getInstitution(), edu.getProgram(), edu.getStartDate(), edu.getEndDate(), edu.getDegree(), id);

    }

    public void deleteEducationInfo(Integer resumeId) {
        String sql = "delete from EDUCATION_INFO where resume_id = ?;";
        jdbcTemplate.update(sql, resumeId);
    }

}
