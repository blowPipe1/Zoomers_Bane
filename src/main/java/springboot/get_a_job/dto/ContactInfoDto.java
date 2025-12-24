package springboot.get_a_job.dto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ContactInfoDto {
    private Integer id;
    private String  type;
    private String resume;
    private String value;
}
