package springboot.get_a_job.serviceImplementations;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import springboot.get_a_job.exceptions.ResourceNotFoundException;
import springboot.get_a_job.models.PasswordResetToken;
import springboot.get_a_job.models.User;
import springboot.get_a_job.repositories.PasswordTokenRepository;
import springboot.get_a_job.repositories.UserRepository;
import springboot.get_a_job.services.PasswordResetService;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class PasswordResetServiceImpl implements PasswordResetService {
    private final PasswordTokenRepository tokenRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    @Override
    @Transactional
    public String createPasswordResetToken(User user) {
        tokenRepository.deleteByUser(user);

        String token = UUID.randomUUID().toString();
        PasswordResetToken myToken = new PasswordResetToken();
        myToken.setToken(token);
        myToken.setUser(user);
        myToken.setExpiryDate(LocalDateTime.now().plusHours(1));

        tokenRepository.save(myToken);
        log.info("Password reset token created for User {} {} ({})", user.getName(), user.getSurname(), user.getEmail());
        return token;
    }

    @Override
    @Transactional
    public boolean isTokenValid(String token) {
        return tokenRepository.findByToken(token)
                .map(t -> t.getExpiryDate().isAfter(LocalDateTime.now()))
                .orElse(false);
    }

    @Override
    @Transactional
    public void resetPassword(String token, String newPassword) {
        PasswordResetToken passToken = tokenRepository.findByToken(token)
                .orElseThrow(() -> new ResourceNotFoundException("Token not found"));

        if (passToken.getExpiryDate().isBefore(LocalDateTime.now())) {
            throw new ResourceNotFoundException("Token expired");
        }

        User user = passToken.getUser();
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);

        tokenRepository.delete(passToken);
        log.info("Password reset for user {} {} ({}) successful", user.getName(), user.getSurname(),user.getEmail());
    }
}
