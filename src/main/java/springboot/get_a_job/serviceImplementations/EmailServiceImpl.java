package springboot.get_a_job.serviceImplementations;

import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import springboot.get_a_job.models.User;
import springboot.get_a_job.services.EmailService;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {
    private final JavaMailSender mailSender;
    private final freemarker.template.Configuration freemarkerConfig;

    public void sendResetEmail(User user, String token) throws Exception {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

        Map<String, Object> model = new HashMap<>();
        model.put("name", user.getName());
        model.put("resetUrl", "http://localhost:8080/passwords/reset-password?token=" + token);

        String html = FreeMarkerTemplateUtils.processTemplateIntoString(
                freemarkerConfig.getTemplate("email-reset.ftlh"), model);

        helper.setTo(user.getEmail());
        helper.setSubject("Password Reset");
        helper.setText(html, true);

        mailSender.send(message);
    }
}
