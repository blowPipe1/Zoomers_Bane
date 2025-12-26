package springboot.get_a_job.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import springboot.get_a_job.dto.validation.OnCreate;
import springboot.get_a_job.dto.validation.OnUpdate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WorkExperienceDto {
    private Integer id;

    @NotNull( message = "Years worked are Required")
    @Positive(message = "Positive number of Years Required")
    private int years;

    @NotNull(groups = {OnUpdate.class, OnCreate.class}, message = "Company's Name cant be null but can be empty''")
    @Size(max = 50, message = "Company's name's length must be less than 50 characters")
    private String companyName;

    @NotNull(groups = {OnUpdate.class, OnCreate.class}, message = "Position cant be null but can be empty''")
    @Size(max = 50, message = "Position's length must be less than 50 characters")
    private String position;

    @NotNull(groups = {OnUpdate.class, OnCreate.class}, message = "Responsibilities cant be null but can be empty''")
    @Size(max = 200, message = "Responsibilities length must be less than 200 characters")
    private String responsibilities;
}