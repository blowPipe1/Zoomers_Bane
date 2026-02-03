package springboot.get_a_job.services;

import springboot.get_a_job.models.User;

public interface EmailService {
    void sendResetEmail(User user, String token) throws Exception;
}
