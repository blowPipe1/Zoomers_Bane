package springboot.get_a_job.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WorkExperienceDto {
    private Integer id;
    private int years;
    private String companyName;
    private String position;
    private String responsibilities;
}