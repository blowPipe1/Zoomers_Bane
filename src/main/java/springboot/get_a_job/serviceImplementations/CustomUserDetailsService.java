package springboot.get_a_job.serviceImplementations;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import springboot.get_a_job.models.CustomUserDetails;
import springboot.get_a_job.models.User;
import springboot.get_a_job.repositories.UserRepository;

import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userDao;

    public CustomUserDetailsService(UserRepository userDao) {
        this.userDao = userDao;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) {
        User user = userDao.findByEmail(email).orElse(null);

        String roleName = "ROLE_" + user.getAccountType().toUpperCase();
        List<SimpleGrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(roleName));

        return new CustomUserDetails(
                user.getId(),
                user.getEmail(),
                user.getPassword(),
                authorities
        );
    }
}