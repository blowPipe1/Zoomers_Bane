package springboot.get_a_job.controllers;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import springboot.get_a_job.dto.UserDto;
import springboot.get_a_job.models.User;
import springboot.get_a_job.services.UserAccountServiceImpl;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserAccountController {

    private final UserAccountServiceImpl userAccountService;

    @PostMapping("/register")
    public ResponseEntity<Optional<UserDto>> registerUser(@RequestBody User user) {
        Optional<UserDto> createdUserDto = userAccountService.registerUser(user);
        return ResponseEntity.ok(createdUserDto);
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

