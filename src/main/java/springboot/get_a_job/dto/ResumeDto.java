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

    @NotBlank(groups = OnCreate.class, message = "{validation.resume.applicant-email.required}")
    @Email(groups = OnCreate.class, message = "{validation.resume.applicant-email.format}")
    private String applicantEmail;

    @NotBlank(groups = OnCreate.class, message = "{validation.resume.name.required}")
    @NotNull(groups = OnUpdate.class, message = "{validation.resume.name.null}")
    @Size(min = 3, max = 30, message = "{validation.resume.name.length}")
    private String name;

    @NotBlank(groups = OnCreate.class, message = "{validation.resume.category.required}")
    @NotNull(groups = OnUpdate.class, message = "{validation.resume.category.null}")
    private String category;

    @NotNull(groups = OnCreate.class, message = "{validation.resume.salary.required}")
    @Positive(message = "{validation.resume.salary.positive}")
    private Double salary;

    @NotNull(message = "{validation.resume.status.required}")
    private Boolean isActive;

    private LocalDateTime createdDate;
    private LocalDateTime updateTime;

    @Valid
    private List<EducationDto> education;

    @Valid
    private List<WorkExperienceDto> workExperience;

    @Valid
    private List<ContactInfoDto>contactInfo;


    public String getFormattedCreatedDate() {
        if (createdDate == null) return "N/A";
        return createdDate.format(java.time.format.DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"));
    }

    public String getFormattedUpdateTime() {
        if (updateTime == null) return "N/A";
        return updateTime.format(java.time.format.DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"));
    }
}