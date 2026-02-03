package springboot.get_a_job.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import springboot.get_a_job.dto.validation.OnCreate;
import springboot.get_a_job.dto.validation.OnUpdate;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VacancyDto {
    private Integer id;
    @NotBlank(groups = OnCreate.class, message = "Vacancy's title is Required")
    @NotNull(groups = OnUpdate.class, message = "Vacancy's title cant be null")
    @Size(min = 3, max = 30, message = "Vacancy's title's length must be between 3 and 30 characters")
    private String name;

    @NotNull(message = "Vacancy's description can be empty bu can't be null")
    @Size(max = 250, message = "Vacancy's description's length must be less than 250 characters")
    private String description;

    @NotBlank(groups = OnCreate.class, message = "Vacancy's category is Required")
    @NotNull(groups = OnUpdate.class, message = "Vacancy's category cant be null")
    private String category;

    @NotNull(groups = OnCreate.class, message = "Vacancy's salary is Required")
    @Positive(message = "Vacancy's salary can't be 0 or negative value")
    private Double salary;

    @NotNull(groups = OnCreate.class, message = "Vacancy's experience is Required")
    @PositiveOrZero(message = "Vacancy's experience can't be negative value")
    private Integer expFrom;

    @NotNull(groups = OnCreate.class, message = "Vacancy's experience is Required")
    @PositiveOrZero(message = "Vacancy's experience can't be negative value")
    private Integer expTo;

    @NotNull(message = "Vacancy's status is Required")
    private Boolean isActive;

    @NotBlank(groups = OnCreate.class, message = "Creator's Name is Required")
    @Size(min = 3, max = 20, message = "Creator's Name's length must be between 3 and 20 characters")
    private String author;

    private LocalDateTime createdDate;
    private LocalDateTime updateTime;
}
