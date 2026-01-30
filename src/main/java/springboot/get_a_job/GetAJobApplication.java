package springboot.get_a_job;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


@SpringBootApplication
public class GetAJobApplication {
    public static void main(String[] args) {
        SpringApplication.run(GetAJobApplication.class, args);
        PasswordEncoder ps = new BCryptPasswordEncoder();
        System.out.println("Van's password - " + " secure" + ps.encode("secure"));
        System.out.println("Billy's password - " + " password123" + ps.encode("password123"));
//        System.out.println("Nico's password - " + " qwerty");
    }
}
