package springboot.get_a_job.controllers;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import springboot.get_a_job.dto.UserDto;
import springboot.get_a_job.models.User;
import springboot.get_a_job.services.UserAccountServiceImpl;
import java.io.IOException;

@RestController
@RequestMapping("/api/users")
public class UserAccountController {

    private final UserAccountServiceImpl userAccountService = new UserAccountServiceImpl();

    @PostMapping("/register")
    public ResponseEntity<UserDto> registerUser(@RequestBody User user) {
        UserDto createdUserDto = userAccountService.registerUser(user);
        return ResponseEntity.ok(createdUserDto);
//        {
//            "id":1,
//                "name": "Petya",
//                "surname": "Petrov",
//                "age": 18,
//                "email": "petya.petrov@mail.com",
//                "password": "12345",
//                "phoneNumber": "996999999999",
//                "avatar":"",
//                "accountType":"Employee"
//        }
//         тела для post запроса
    }

    @PostMapping("/{userId}/avatar")
    public ResponseEntity<String> uploadAvatar(@PathVariable Integer userId, @RequestParam("file") MultipartFile file) {
        try {
            userAccountService.saveAvatar(userId, file);
            return ResponseEntity.ok("Аватар успешно загружен.");
        } catch (IOException e) {
            return ResponseEntity.internalServerError().body("Ошибка загрузки или обработки файла.");
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
}

