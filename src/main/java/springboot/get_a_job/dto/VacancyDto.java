package springboot.get_a_job.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VacancyDto {
    public String name;
    public String description;
    public String category;
    public Double salary;
    public Integer expFrom;
    public Integer expTo;
    public Boolean isActive;
    public Integer authorId;
}
