package springboot.get_a_job.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RespondedApplicantDto {
    private Integer id;
    private String vacancyTitle;
    private String resumeTitle;
    private Boolean confirmation;
    private LocalDateTime lastMessageAt;
}
