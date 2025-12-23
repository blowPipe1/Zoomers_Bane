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
    public Integer id;
    public String  type;
    public String resume;
    public String value;
}
