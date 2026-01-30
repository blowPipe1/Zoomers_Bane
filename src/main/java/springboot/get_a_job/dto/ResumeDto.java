package springboot.get_a_job.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.*;
import springboot.get_a_job.dto.validation.OnCreate;
import springboot.get_a_job.dto.validation.OnUpdate;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResumeDto {
    private Integer id;

    @NotBlank(groups = OnCreate.class, message = "Email is Required")
    @Email(groups = OnCreate.class, message = "Email should be valid")
    private String applicantEmail;

    @NotBlank(groups = OnCreate.class, message = "Resume's title is Required")
    @NotNull(groups = OnUpdate.class, message = "Resume's title cant be null")
    @Size(min = 3, max = 30, message = "Resume's title's length must be between 3 and 30 characters")
    private String name;

    @NotBlank(groups = OnCreate.class, message = "Resume's category is Required")
    @NotNull(groups = OnUpdate.class, message = "Resume's category cant be null")
    private String category;

    @NotNull(groups = OnCreate.class, message = "Resume's salary is Required")
    @Positive(message = "Resume's salary can't be 0 or negative value")
    private Double salary;

    @NotNull(message = "Resume's status is Required")
    private Boolean isActive;

    private LocalDateTime createdDate;
    private LocalDateTime updateTime;

    @Valid
    private List<EducationDto> education;

    @Valid
    private List<WorkExperienceDto> workExperience;

    @Valid
    private List<ContactInfoDto>contactInfo;
}