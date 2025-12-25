package springboot.get_a_job.controllers;


import jakarta.validation.groups.Default;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import springboot.get_a_job.dto.UserDto;
import springboot.get_a_job.dto.validation.OnCreate;
import springboot.get_a_job.dto.validation.OnUpdate;
import springboot.get_a_job.serviceImplementations.UserAccountServiceImpl;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/users")
public class UserAccountController {
    private final UserAccountServiceImpl userAccountService;

    @PostMapping("/register/")
    public ResponseEntity<String> registerUser(@Validated(OnCreate.class) @RequestBody UserDto userDto) {
        log.info("Received request to register new User with a name and email: {} {}  {}", userDto.getName(), userDto.getSurname() ,userDto.getEmail());
        userAccountService.registerUser(userDto);
        log.debug("Registered user with name {} {} and email {}", userDto.getName(), userDto.getSurname(), userDto.getEmail());
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body("User successfully Registered");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateUser( @PathVariable Integer id, @Validated(OnUpdate.class) @RequestBody UserDto userDto) {
        log.info("Received request to update User(ID: {}) with a name and email: {} {}  {}", id, userDto.getName(), userDto.getSurname() ,userDto.getEmail());
        userAccountService.updateUser(id, userDto);
        log.debug("Updated a User(ID: {}) with a name and email: {} {}  {}", id, userDto.getName(), userDto.getSurname() ,userDto.getEmail());
        return ResponseEntity
                .status(HttpStatus.OK)
                .body("User successfully updated");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Integer id) {
        log.info("Received request to delete User with a id {}", id);
        userAccountService.deleteUser(id);
        log.debug("Deleted a User with a id {}", id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body("User successfully deleted");
    }

    @PostMapping("/{userId}/avatar")
    public ResponseEntity<String> uploadAvatar(@PathVariable Integer userId, @RequestParam("file") MultipartFile file) {
        try {
            log.info("Received request to upload avatar for user ID: {} ", userId);
            userAccountService.saveAvatar(userId, file);
            log.debug("Uploaded avatar for user ID: {} ", userId);
            return ResponseEntity.ok("Successfully uploaded avatar");
        } catch (IOException e) {
            log.error("Error occurred {}", e.getMessage());
            return ResponseEntity.internalServerError().body("Error while uploading avatar");
        } catch (IllegalArgumentException e) {
            log.error("IllegalArgumentException error occurred {}", e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @GetMapping("/{id}")
    public ResponseEntity<UserDto> findUserById(@PathVariable Integer id) {
        log.info("Received request to find User with a id {}", id);
        return userAccountService.findUserById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/all")
    public ResponseEntity<List<UserDto>> findAllUsers() {
        log.info("Received request to find all Users");
        return userAccountService.findAllUsers()
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("phone/{phone_number}")
    public ResponseEntity<List<UserDto>> findUserByPhone(@PathVariable String phone_number ) {
        log.info("Received request to find User with a phone number {}", phone_number);
        return userAccountService.findUserByPhone(phone_number)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("email/{email}")
    public ResponseEntity<List<UserDto>> findUserByEmail(@PathVariable String email) {
        log.info("Received request to find User with a email {}", email);
        return userAccountService.findUserByEmail(email)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
    @GetMapping("username/{name}")
    public ResponseEntity<List<UserDto>> findUserByName(@PathVariable String name) {
        log.info("Received request to find User with a name {}", name);
        return userAccountService.findUserByName(name)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/responded/{vacancy_id}")
    public ResponseEntity<List<UserDto>> findRespondedUsers(@PathVariable Integer vacancy_id) {
        log.info("Received request to find Users responded to Vacancy with id {}", vacancy_id);
        return userAccountService.findRespondedUsers(vacancy_id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}

