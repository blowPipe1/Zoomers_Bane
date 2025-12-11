package springboot.get_a_job.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WorkExperienceInfo {
    public Integer id;
    public Integer resumeId;
    public Integer years;
    public String companyName;
    public String position;
    public String responsibilities;
}
