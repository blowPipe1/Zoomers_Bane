package springboot.get_a_job.dto;

import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;
import springboot.get_a_job.dto.validation.OnCreate;
import springboot.get_a_job.dto.validation.OnUpdate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EducationDto {
    private Integer id;

    @NotNull(groups =OnCreate.class, message = "{validation.education.institution.required}")
    @Size(max = 50, message = "{validation.education.institution.length}")
    private String institution;

    @NotNull(groups = { OnCreate.class}, message = "{validation.education.program.required}")
    @Size(max = 100, message = "{validation.education.program.length}")
    private String program;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @NotNull(groups = OnCreate.class, message = "{validation.education.start-date.null}")
    @PastOrPresent(message = "{validation.education.start-date.past-or-present}")
    private LocalDate startDate;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @PastOrPresent(message = "{validation.education.end-date.past-or-present}")
    private LocalDate endDate;

    @NotNull(groups = { OnCreate.class}, message = "{validation.education.degree.null}")
    @Size(max = 50, message = "{validation.education.degree.length}")
    private String degree;

    @AssertTrue( message = "{validation.education.end-date.after-start-date}")
    public boolean isValidDateRange() {
        return endDate.isAfter(startDate);
    }
}