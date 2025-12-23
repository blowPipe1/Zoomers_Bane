package springboot.get_a_job.dto;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EducationDto {
    Integer id;
    String institution;
    String program;
    LocalDate startDate;
    LocalDate endDate;
    String degree;
}