package springboot.get_a_job;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDateTime;

@SpringBootApplication
public class GetAJobApplication {

    public static void main(String[] args) {
        SpringApplication.run(GetAJobApplication.class, args);
        System.out.println(LocalDateTime.now());

    }

}
