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
    Integer id;
    int years;
    String companyName;
    String position;
    String responsibilities;
}