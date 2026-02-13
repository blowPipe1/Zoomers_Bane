package springboot.get_a_job.dto;
import jakarta.validation.constraints.NotNull;
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
public class ContactInfoDto {
    private Integer id;

    @NotNull(groups = {OnCreate.class}, message = "{validation.contact-info.type.null}")
    @Size(max = 20, message = "{validation.contact-info.type.length}")
    private String  type;

    @NotNull(groups = {OnCreate.class}, message = "{validation.contact-info.resume.null}")
    @Size(max = 30, message = "{validation.contact-info.resume.length}")
    private String resume;

    @NotNull(groups = {OnCreate.class}, message = "{validation.contact-info.value.null}")
    @Size(max = 20, message = "{validation.contact-info.value.length}")
    private String value;
}
