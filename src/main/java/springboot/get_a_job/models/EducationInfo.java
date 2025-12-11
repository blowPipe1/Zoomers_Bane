package springboot.get_a_job.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EducationInfo {
    public Integer id;
    public Integer resumeId;
    public String institution;
    public String program;
    public Date startDate;
    public Date endDate;
    public String degree;
}
