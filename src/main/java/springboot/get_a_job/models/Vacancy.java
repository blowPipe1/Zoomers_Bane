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
public class Vacancy {
    public Integer id;
    public String name;
    public String description;
    public Integer categoryId;
    public Double salary;
    public Integer expFrom;
    public Integer expTo;
    public Boolean isActive;
    public Integer authorId;
    public LocalDateTime createdDate;
    public LocalDateTime updateTime;
}
