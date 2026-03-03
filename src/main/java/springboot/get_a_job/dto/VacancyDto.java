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
    @NotBlank(groups = OnCreate.class, message = "{validation.vacancy.name.required}")
    @NotNull(groups = OnUpdate.class, message = "{validation.vacancy.name.null}")
    @Size(min = 3, max = 30, message = "{validation.vacancy.name.length}")
    private String name;

    @NotNull(message = "{validation.vacancy.description.null}")
    @Size(max = 250, message = "{validation.vacancy.description.length}")
    private String description;

    @NotBlank(groups = OnCreate.class, message = "{validation.vacancy.category.required}")
    @NotNull(groups = OnUpdate.class, message = "{validation.vacancy.category.null}")
    private String category;

    @NotNull(groups = OnCreate.class, message = "{validation.vacancy.salary.required}")
    @Positive(groups = OnCreate.class,message = "{validation.vacancy.salary.positive}")
    @Min(value = 1, groups = OnCreate.class,message = "{validation.vacancy.salary.positive}")
    private Double salary;

    @NotNull(groups = OnCreate.class, message = "{validation.vacancy.exp-from.required}")
    @PositiveOrZero(message = "{validation.vacancy.exp-from.positive}")
    private Integer expFrom;

    @NotNull(groups = OnCreate.class, message = "{validation.vacancy.exp-to.required}")
    @PositiveOrZero(message = "{validation.vacancy.exp-to.positive}")
    private Integer expTo;

    @NotNull(message = "{validation.vacancy.status.required}")
    private Boolean isActive;

    @NotBlank(groups = OnCreate.class, message = "{validation.vacancy.author.null}")
    @Size(min = 3, max = 20)
    private String author;

    private LocalDateTime createdDate;
    private LocalDateTime updateTime;

    private Integer applications;


    public String getFormattedCreatedDate() {
        if (createdDate == null) return "N/A";
        return createdDate.format(java.time.format.DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"));
    }

    public String getFormattedUpdateTime() {
        if (updateTime == null) return "N/A";
        return updateTime.format(java.time.format.DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"));
    }
}


