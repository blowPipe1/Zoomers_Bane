package springboot.get_a_job.controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springboot.get_a_job.dto.UserDto;
import springboot.get_a_job.dto.validation.OnCreate;
import springboot.get_a_job.services.UserAccountService;

@Controller
@RequiredArgsConstructor
public class AuthController {
    private final UserAccountService userAccountService;

    @GetMapping("/login")
    public String showLoginForm(@RequestParam(value = "error", required = false) String error, Model model) {
        if (error != null) {
            model.addAttribute("errorMessage", "Invalid email or password");
        }
        return "login";
    }

    @GetMapping("/register-form")
    public String showForm(Model model) {
        model.addAttribute("userDto", new UserDto());
        return "registration";
    }

    @PostMapping("/register")
    public String registerUser(@Validated(OnCreate.class) @ModelAttribute("userDto") UserDto userDto,
                               BindingResult result,
                               HttpServletRequest request) {
        if (result.hasErrors()) {
            return "registration";
        }

        userAccountService.registerUser(userDto);

        try {
            request.login(userDto.getEmail(), userDto.getPassword());
        } catch (ServletException e) {
            return "redirect:/login?error";
        }

        return "redirect:/api/users/dashboard";
    }

}
