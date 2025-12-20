package springboot.get_a_job.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResumeDto {
    String applicant;
    String name;
    String category;
    Double salary;
    boolean isActive;
    List<EducationDto> education;
    List<WorkExperienceDto> workExperience;
}