package springboot.get_a_job.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import springboot.get_a_job.dto.UserDto;
import springboot.get_a_job.models.User;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserAccountServiceImpl implements UserAccountService {

    private final String subDir = "data/images/";

    @Override
    public UserDto registerUser(User user) {
        System.out.println("User registering: " + user.getEmail());
        //TODO
        // 1. Проверить данные
        // 2. Сохранить в БД

        // Заглушка:
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setName(user.getName());
        userDto.setSurname(user.getSurname());
        userDto.setAge(user.getAge());
        userDto.setEmail(user.getEmail());
        userDto.setPhoneNumber(user.getPhoneNumber());
        userDto.setAvatar(user.getAvatar());
        userDto.setAccountType(user.getAccountType());

        return userDto;
    }

    @Override
    public void saveAvatar(Integer userId, MultipartFile file) throws IOException{
        if (file.isEmpty()) {
            System.out.println("File is empty");
        }

        System.out.println("Saving avatar for user ID: " + userId);
        //TODO Логика сохранения файла и обновления пути в БД
        Path uploadPath = Paths.get(subDir);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }
        Path filePath = uploadPath.resolve(userId + "_" + file.getOriginalFilename());
        Files.copy(file.getInputStream(), filePath);
    }

    @Override
    public Optional<UserDto> findUserDtoById(Integer id) {
        System.out.println("Search by ID " + id);
        //TODO Логика поиска в БД

        // Заглушка:
        if (id > 0) {
            UserDto foundUser = new UserDto();
            foundUser.setId(id);
            foundUser.setName("test");
            return Optional.of(foundUser);
        }
        return Optional.empty();
    }
}
