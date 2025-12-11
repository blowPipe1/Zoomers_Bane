package springboot.get_a_job.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RespondedApplicant {
    public Integer id;
    public Integer resumeId;
    public Integer vacancyId;
    public Boolean confirmation;
}
