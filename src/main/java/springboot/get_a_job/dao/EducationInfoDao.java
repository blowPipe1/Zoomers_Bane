package springboot.get_a_job.dao;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;
import springboot.get_a_job.dto.EducationDto;
import springboot.get_a_job.dto.ResumeDto;
import springboot.get_a_job.models.Resume;
import springboot.get_a_job.models.User;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class EducationInfoDao {
    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public List<EducationDto> getResumesEducationInfo(Integer resumeId){
        String sql = "SELECT * FROM education_info WHERE resume_id = :id;";
        try {
            log.info("Fetching Resumes(ID: {}) Contact Info", resumeId);
            return namedParameterJdbcTemplate.query(
                    sql,
                    new MapSqlParameterSource()
                            .addValue("id", resumeId),
                    new BeanPropertyRowMapper<>(EducationDto.class));
        } catch (EmptyResultDataAccessException e) {
            log.warn("No Contact Info For Resume(ID: {}) was found", resumeId);
            return null;
        }

    }

    public EducationDto findInfoById(int id) {
        String sql = "select * from EDUCATION_INFO where id = ?;";
        try {
            log.debug("Fetching Education Info with ID: {}", id);
            return jdbcTemplate.queryForObject(sql,new BeanPropertyRowMapper<>(EducationDto.class), id);
        } catch (EmptyResultDataAccessException e) {
            log.warn("No Education Info with ID: {} was found", id);
            return null;
        }
    }


    public void addEducationInfo(EducationDto edu, Integer resumeId) {
        String sql = "insert into EDUCATION_INFO(resume_id, institution, program, start_date, end_date, degree)" +
                "values (?, ?, ?, ?, ?, ?);";
        log.debug("Executing SQL to Save Contact Info: {}", sql);
        jdbcTemplate.update(sql, resumeId, edu.getInstitution(), edu.getProgram(), edu.getStartDate(), edu.getEndDate(), edu.getDegree());
    }

    public void updateEducationInfo(EducationDto edu, Integer id) {
        String sql = "update EDUCATION_INFO set institution = ?, program = ?, start_date = ?, end_date = ?, degree = ? where id = ?";
        log.debug("Executing SQL to Update Contact Info: {}", sql);
        jdbcTemplate.update(sql, edu.getInstitution(), edu.getProgram(), edu.getStartDate(), edu.getEndDate(), edu.getDegree(), id);

    }

    public void deleteEducationInfo(Integer resumeId) {
        String sql = "delete from EDUCATION_INFO where resume_id = ?;";
        log.debug("Executing SQL to Delete Contact Info: {}", sql);
        jdbcTemplate.update(sql, resumeId);
    }

}
