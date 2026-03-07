package springboot.get_a_job.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class MessageDto {
    private Integer applicationId;
    private String content;
    private String authorName;
    private String authorSurname;
    private String authorAvatar;
    private String authorEmail;
    private String timestamp;
}
