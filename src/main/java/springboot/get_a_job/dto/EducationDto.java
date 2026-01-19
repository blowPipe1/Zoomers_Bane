package springboot.get_a_job.dto;

import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;
import springboot.get_a_job.dto.validation.OnCreate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EducationDto {
    private Integer id;

    @NotNull(groups =OnCreate.class, message = "Institution's Name cant be null but can be empty''")
    @Size(max = 50, message = "Institution's name's length must be less than 50 characters")
    private String institution;

    @NotNull(groups = { OnCreate.class}, message = "Program's Name cant be null but can be empty''")
    @Size(max = 100, message = "Program's Name's length must be less than 50 characters")
    private String program;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @NotNull(groups = OnCreate.class, message = "Creating Education info requires Starting date")
    @PastOrPresent(message = "Starting Date can't be in the future")
    private LocalDate startDate;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @PastOrPresent(message = "Ending Date can't be in the future")
    private LocalDate endDate;

    @NotNull(groups = { OnCreate.class}, message = "Education's Degree cant be null but can be empty''")
    @Size(max = 50, message = "Degree's length must be less than 50 characters")
    private String degree;
}