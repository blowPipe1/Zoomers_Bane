package springboot.get_a_job.controllers;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import springboot.get_a_job.dto.ResumeDto;
import springboot.get_a_job.dto.UserDto;
import springboot.get_a_job.dto.VacancyDto;
import springboot.get_a_job.dto.validation.OnCreate;
import springboot.get_a_job.dto.validation.OnUpdate;
import springboot.get_a_job.exceptions.UserNotFoundException;
import springboot.get_a_job.models.Vacancy;
import springboot.get_a_job.services.ResumeService;
import springboot.get_a_job.services.UserAccountService;
import springboot.get_a_job.services.VacancyService;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserAccountController {
    private final UserAccountService userAccountService;
    private final ResumeService resumeService;
    private final VacancyService vacancyService;


    @GetMapping("/register")
    public String showForm(Model model) {
        model.addAttribute("userDto", new UserDto());
        return "registration";
    }

    @PostMapping("/register")
    public String registerUser(@Validated(OnCreate.class) @ModelAttribute("userDto") UserDto userDto,
                               BindingResult result) {
        if (result.hasErrors()) {
            return "registration";
        }
        userAccountService.registerUser(userDto);
        return ("User successfully Registered");
    }

    @GetMapping("/dashboard/{userId}")
    public String dashboard(@PathVariable Integer userId, Model model) {
        UserDto currentUser = userAccountService.findUserById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + userId));

        model.addAttribute("user", currentUser);

        if (currentUser.getAccountType().equalsIgnoreCase("applicant")) {
            List<ResumeDto> items = resumeService.findResumeByCreator(userId).orElseGet(Collections::emptyList);
            model.addAttribute("itemsList", items);
        } else if (currentUser.getAccountType().equalsIgnoreCase("employer")) {
            List<VacancyDto> items = vacancyService.findVacancyByCreator(userId).orElseGet(Collections::emptyList);
            model.addAttribute("itemsList", items);
        }

        return "dashboard";
    }



    @PutMapping("/{id}")
    public ResponseEntity<String> updateUser( @PathVariable Integer id, @Validated(OnUpdate.class) @RequestBody UserDto userDto) {
        userAccountService.updateUser(id, userDto);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body("User successfully updated");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Integer id) {
        userAccountService.deleteUser(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body("User successfully deleted");
    }

    @PostMapping("/{userId}/avatar")
    public ResponseEntity<String> uploadAvatar(@PathVariable Integer userId, @RequestParam("file") MultipartFile file) {
        try {
            userAccountService.saveAvatar(userId, file);
            return ResponseEntity.ok("Successfully uploaded avatar");
        } catch (IOException e) {
            return ResponseEntity.internalServerError().body("Error while uploading avatar");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @GetMapping("/{id}")
    public ResponseEntity<UserDto> findUserById(@PathVariable Integer id) {
        return userAccountService.findUserById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/all")
    public ResponseEntity<List<UserDto>> findAllUsers() {
        return userAccountService.findAllUsers()
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("phone/{phone_number}")
    public ResponseEntity<List<UserDto>> findUserByPhone(@PathVariable String phone_number ) {
        return userAccountService.findUserByPhone(phone_number)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("email/{email}")
    public ResponseEntity<List<UserDto>> findUserByEmail(@PathVariable String email) {
        return userAccountService.findUserByEmail(email)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
    @GetMapping("username/{name}")
    public ResponseEntity<List<UserDto>> findUserByName(@PathVariable String name) {
        return userAccountService.findUserByName(name)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/responded/{vacancy_id}")
    public ResponseEntity<List<UserDto>> findRespondedUsers(@PathVariable Integer vacancy_id) {
        return userAccountService.findRespondedUsers(vacancy_id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}

