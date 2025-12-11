package springboot.get_a_job.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    public Integer id;
    public String name;
    public String surname;
    public Integer age;
    public String email;
    public String phoneNumber;
    public String avatar;
    public String accountType;
}
