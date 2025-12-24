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
    private Integer id;
    private Integer resumeId;
    private String institution;
    private String program;
    private Date startDate;
    private Date endDate;
    private String degree;
}
