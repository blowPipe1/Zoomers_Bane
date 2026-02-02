package springboot.get_a_job.services;

import springboot.get_a_job.models.User;

public interface PasswordResetService {
    String createPasswordResetToken(User user);
    boolean isTokenValid(String token);
    void resetPassword(String token, String newPassword);
}
