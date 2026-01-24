package springboot.get_a_job.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/register-form/**", "/register/**", "/api/vacancies/all").permitAll()
                        .requestMatchers("/images/**", "/css/**", "/js/**", "/static/**").permitAll()

                        .requestMatchers("/api/vacancies/create").hasRole("EMPLOYER")
                        .requestMatchers("/api/vacancies/update/*").hasRole("EMPLOYER")
                        .requestMatchers("/api/vacancies/delete/*").hasRole("EMPLOYER")

                        .requestMatchers("/api/resumes/create").hasRole("APPLICANT")
                        .requestMatchers("/api/resumes/update/**").hasRole("APPLICANT")
                        .requestMatchers("/api/resumes/delete/*").hasRole("APPLICANT")
                        .requestMatchers("/api/resumes/add/**").hasRole("APPLICANT")


                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .loginProcessingUrl("/process_login")
                        .defaultSuccessUrl("/api/users/dashboard", true)
                        .permitAll()
                )
                .httpBasic(Customizer.withDefaults())
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/")
                );

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager (UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder);
        return new ProviderManager(authProvider);
    }
}