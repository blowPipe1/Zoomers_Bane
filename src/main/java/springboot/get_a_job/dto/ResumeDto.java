package springboot.get_a_job.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResumeDto {
    private String applicant;
    private String name;
    private String category;
    private Double salary;
    private boolean isActive;
    private List<EducationDto> education;
    private List<WorkExperienceDto> workExperience;
    private List<ContactInfoDto>contactInfo;
}