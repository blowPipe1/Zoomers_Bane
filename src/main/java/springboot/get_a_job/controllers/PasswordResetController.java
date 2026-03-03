package springboot.get_a_job.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import springboot.get_a_job.repositories.UserRepository;
import springboot.get_a_job.services.EmailService;
import springboot.get_a_job.services.PasswordResetService;

import java.util.Locale;

@Slf4j
@Controller
@RequestMapping("/passwords")
@RequiredArgsConstructor
public class PasswordResetController {
    private final PasswordResetService resetService;
    private final UserRepository userRepository;
    private final EmailService emailService;

    @GetMapping("/forgot-password/form")
    public String showForgotPasswordPage() {
        return "forgot-password";
    }


    @PostMapping("/forgot-password")
    public String processForgotPassword(@RequestParam String email, Model model, Locale locale) {
        userRepository.findByEmail(email).ifPresent(user -> {
            try {
                String token = resetService.createPasswordResetToken(user);
                emailService.sendResetEmail(user, token, locale);
            } catch (Exception e) {
                model.addAttribute("error", "Error while sending reset email");
            }
        });
        model.addAttribute("message", "Check your mail for instructions");
        return "forgot-password";
    }

    @GetMapping("/reset-password")
    public String showResetPage(@RequestParam String token, Model model) {
        if (!resetService.isTokenValid(token)) {
            model.addAttribute("error", "Invalid or expired Link");
            return "error";
        }
        model.addAttribute("token", token);
        return "reset-password";
    }

    @PostMapping("/reset-password")
    public String handlePasswordReset(@RequestParam String token,
                                      @RequestParam String password,
                                      Model model) {
        try {
            resetService.resetPassword(token, password);
            return "redirect:/login?resetSuccess";
        } catch (Exception e) {
            model.addAttribute("error", "Failed to reset password");
            return "reset-password";
        }
    }
}
