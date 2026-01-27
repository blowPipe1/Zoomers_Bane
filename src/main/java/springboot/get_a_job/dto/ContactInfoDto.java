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

    @NotNull(groups = {OnCreate.class}, message = "Type of your Contact Info cant be null but can be empty''")
    @Size(max = 20, message = " 's length must be less than 20 characters")
    private String  type;

    @NotNull(groups = {OnCreate.class}, message = " cant be null but can be empty''")
    @Size(max = 30, message = "Contact Info's Resume's name length must be less than 30, characters")
    private String resume;

    @NotNull(groups = {OnCreate.class}, message = " cant be null but can be empty''")
    @Size(max = 20, message = "Contact Info's Value's length must be less than 20 characters")
    private String value;
}
