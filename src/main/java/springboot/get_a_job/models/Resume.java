package springboot.get_a_job.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Resume {
    public Integer id;
    public Integer applicantId;
    public String name;
    public Integer categoryId;
    public Double salary;
    public Boolean isActive;
    public LocalDateTime createdDate;
    public LocalDateTime updateTime;
}
