package springboot.get_a_job.dto;

import jakarta.validation.constraints.*;
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
public class UserDto {
    @NotBlank(groups = OnCreate.class, message = "{validation.name.required}")
    @NotNull(groups = OnUpdate.class, message = "{validation.name.null}")
    @Size(groups = {OnCreate.class, OnUpdate.class}, min = 3, max = 20, message = "{validation.name.length}")
    private String name;

    @NotBlank(groups = OnCreate.class, message = "{validation.surname.required}")
    @NotNull(groups = OnUpdate.class, message = "{validation.surname.null}")
    @Size(groups = {OnCreate.class, OnUpdate.class}, min = 5, max = 25, message = "{validation.surname.length}")
    private String surname;

    @Positive
    @NotNull(groups = OnCreate.class, message = "{validation.age.required}")
    @Min(groups = OnCreate.class, value = 18, message = "{validation.age.min}")
    @Max(value = 100, message = "{validation.age.max}")
    private Integer age;

    @NotBlank(groups = OnCreate.class, message = "{validation.email.required}")
    @NotNull(groups = OnUpdate.class, message = "{validation.email.null}")
    @Email(groups = {OnUpdate.class, OnCreate.class},  message = "{validation.email.format}")
    private String email;

    @NotBlank(groups = OnCreate.class, message = "{validation.password.required}")
    @Size(groups = OnCreate.class, min = 7, max = 30, message = "{validation.password.length}")
    private String password;

    @NotBlank(groups = OnCreate.class, message = "{validation.phone-number.required}")
    @NotNull(groups = OnUpdate.class, message = "{validation.phone-number.null}")
    @Size(groups = {OnCreate.class, OnUpdate.class}, min = 10, max = 15, message = "{validation.phone-number.length}")
    private String phoneNumber;

    @NotNull(groups = OnCreate.class, message = "{validation.avatar.null}")
    private String avatar;

    @NotBlank(groups = OnCreate.class, message = "{validation.account-type.required}")
    @NotNull(groups = OnUpdate.class, message = "{validation.account-type.null}")
    private String accountType;
}
