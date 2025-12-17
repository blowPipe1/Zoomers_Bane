package springboot.get_a_job.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import springboot.get_a_job.dao.UserDao;
import springboot.get_a_job.dto.UserDto;
import springboot.get_a_job.models.User;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserAccountServiceImpl implements UserAccountService {
    private final String subDir = "src/main/java/springboot/get_a_job/data/images/";
    private final UserDao userDao;

    @Override
    public UserDto registerUser(User user) {
        System.out.println("User registering: " + user.getEmail());
        //TODO
        // 1. Проверить данные
        // 2. Сохранить в БД

        // Заглушка:
        return convert(user);
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
    public Optional<UserDto> findUserById(Integer id) {
        User result = userDao.findUserById(id);
        if (result == null) {
            return Optional.empty();
        } else {
            return Optional.of(convert(result));
        }
    }

    @Override
    public Optional<List<UserDto>> findAllUsers() {
        List<UserDto> usersDtos = new ArrayList<>();
        if (userDao.getAllUsers().isEmpty()) {
            return Optional.empty();
        } else {
            for (User user : userDao.getAllUsers()) {
                usersDtos.add(convert(user));
            }
            return Optional.of(usersDtos);
        }
    }

    @Override
    public Optional<UserDto> findUserByPhone(String phone_number){
        List<User> result = userDao.findUserByPhone(phone_number);
        if (result == null) {
            return Optional.empty();
        } else {
            return Optional.of(convert(result.getFirst()));
        }
    }

    @Override
    public Optional<UserDto> findUserByEmail(String email) {
        List<User> result = userDao.findUserByEmail(email);
        if (result == null) {
            return Optional.empty();
        } else {
            return Optional.of(convert(result.getFirst()));
        }
    }

    @Override
    public Optional<UserDto> findUserByName(String name) {
        List<User> result = userDao.findUserByName(name);
        if (result == null) {
            return Optional.empty();
        } else {
            return Optional.of(convert(result.getFirst()));
        }
    }



    public UserDto convert(User user) {
        return new UserDto(
                user.getId(),
                user.getName(),
                user.getSurname(),
                user.getAge(),
                user.getEmail(),
                user.getPhoneNumber(),
                user.getAvatar(),
                user.getAccountType()
        );
    }


}
