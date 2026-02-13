package springboot.get_a_job.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import springboot.get_a_job.dto.validation.OnCreate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WorkExperienceDto {
    private Integer id;

    @NotNull( message = "{validation.experience.years.null}")
    @Positive(message = "{validation.experience.years.positive}")
    private int years;

    @NotNull(groups = {OnCreate.class}, message = "{validation.experience.company-name.null}")
    @Size(max = 50, message = "{validation.experience.company-name.length}")
    private String companyName;

    @NotNull(groups = { OnCreate.class}, message = "{validation.experience.position.null}")
    @Size(max = 50, message = "{validation.experience.position.length}")
    private String position;

    @NotNull(groups = { OnCreate.class}, message = "{validation.experience.responsibilities.null}")
    @Size(max = 200, message = "{validation.experience.responsibilities.length}")
    private String responsibilities;
}