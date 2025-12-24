package springboot.get_a_job.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private interface OnCreate{}
    private interface OnUpdate{}

    @NotBlank(groups = OnCreate.class, message = "Name is Required")
    @NotNull(groups = OnUpdate.class, message = "Name cant be null")
    @Size(min = 3, max = 20, message = "Name's length must be between 3 and 20 characters")
    private String name;

    @NotBlank(groups = OnCreate.class, message = "Surname is Required")
    @NotNull(groups = OnUpdate.class, message = "Surname cant be null")
    @Size(min = 5, max = 25, message = "Surname's length must be between 5 and 25 characters")
    private String surname;

    @NotNull(groups = OnCreate.class, message = "Age is Required")
    @Min(groups = OnCreate.class, value = 18, message = "Must at least 18 years old")
    @Min(groups = OnUpdate.class, value = 0, message = "Set 0 if no updates are incoming")
    @Max(value = 100, message = "Invalid Age")
    private Integer age;

    @NotBlank(groups = OnCreate.class, message = "Email is Required")
    @NotNull(groups = OnUpdate.class, message = "Email cant be null")
    @Email(groups = {OnUpdate.class, OnCreate.class},  message = "Provide a valid email address")
    private String email;

    @NotBlank(groups = OnCreate.class, message = "Password is Required")
    @Size(min = 7, max = 30, message = "Password's length must be between 7 and 30 characters")
    private String password;

    @NotBlank(groups = OnCreate.class, message = "Phone Number is Required")
    @NotNull(groups = OnUpdate.class, message = "Phone Number cant be null")
    @Size(min = 10, max = 15, message = "Phone number length is invalid")
    private String phoneNumber;

    @NotNull(groups = OnCreate.class, message = "Avatar must al least be empty")
    private String avatar;

    @NotBlank(groups = OnCreate.class, message = "Account type is Required (Applicant / Employer)")
    @NotNull(groups = OnUpdate.class, message = "Account type cant be null")
    @Size(min = 8, max = 9, message = "Format Should look like this: 'applicant' / 'employer'")
    private String accountType;
}
