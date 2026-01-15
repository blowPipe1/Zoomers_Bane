package springboot.get_a_job.serviceImplementations;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import springboot.get_a_job.dao.UserDao;
import springboot.get_a_job.models.CustomUserDetails;
import springboot.get_a_job.models.User;

import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final UserDao userDao;

    public CustomUserDetailsService(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) {
        User user = userDao.findUserByEmail(email).get(0);

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