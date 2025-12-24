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
    private String name;
    private String description;
    private String category;
    private Double salary;
    private Integer expFrom;
    private Integer expTo;
    private Boolean isActive;
    private String author;
}
