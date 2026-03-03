package springboot.get_a_job.services;

import springboot.get_a_job.models.User;

import java.util.Locale;

public interface EmailService {
    void sendResetEmail(User user, String token, Locale locale) throws Exception;
}
