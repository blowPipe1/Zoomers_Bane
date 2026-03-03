package springboot.get_a_job.serviceImplementations;

import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import springboot.get_a_job.models.User;
import springboot.get_a_job.services.EmailService;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {
    private final JavaMailSender mailSender;
    private final freemarker.template.Configuration freemarkerConfig;
    @Autowired
    private MessageSource messageSource;

    public void sendResetEmail(User user, String token, Locale locale) throws Exception {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

        Map<String, Object> model = new HashMap<>();
        model.put("name", user.getName());
        model.put("resetUrl", "http://localhost:8080/passwords/reset-password?token=" + token);

        model.put("msgHeader", messageSource.getMessage("email-reset.header", null, locale));
        model.put("msgGreeting", messageSource.getMessage("email-reset.greeting", null, locale));
        model.put("msgInstruction", messageSource.getMessage("email-reset.instruction", null, locale));
        model.put("msgNotice", messageSource.getMessage("email-reset.notice", null, locale));
        model.put("msgBtn", messageSource.getMessage("email-reset.btn", null, locale));

        String html = FreeMarkerTemplateUtils.processTemplateIntoString(
                freemarkerConfig.getTemplate("email-reset.ftlh"), model);

        helper.setTo(user.getEmail());
        helper.setSubject("Password Reset");
        helper.setText(html, true);

        mailSender.send(message);
    }
}
