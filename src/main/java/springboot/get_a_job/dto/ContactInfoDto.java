package springboot.get_a_job.dto;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import springboot.get_a_job.dto.validation.OnCreate;
import springboot.get_a_job.dto.validation.OnUpdate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ContactInfoDto {
    private Integer id;

    @NotNull(groups = {OnUpdate.class, OnCreate.class}, message = "Type of your Contact Info cant be null but can be empty''")
    @Size(max = 20, message = " 's length must be less than 20 characters")
    private String  type;

    @NotNull(groups = {OnUpdate.class, OnCreate.class}, message = " cant be null but can be empty''")
    @Size(max = 20, message = "Contact Info's Resume's name length must be less than 20 characters")
    private String resume;

    @NotNull(groups = {OnUpdate.class, OnCreate.class}, message = " cant be null but can be empty''")
    @Size(max = 20, message = "Contact Info's Value's length must be less than 20 characters")
    private String value;
}
