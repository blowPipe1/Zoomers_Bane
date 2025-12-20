package springboot.get_a_job.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import springboot.get_a_job.dao.UserDao;
import springboot.get_a_job.dto.UserDto;
import springboot.get_a_job.dto.VacancyDto;
import springboot.get_a_job.models.User;
import springboot.get_a_job.models.Vacancy;

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
    public Optional<UserDto> registerUser(User user) {
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
        return convert(userDao.findUserById(id));
    }

    @Override
    public Optional<List<UserDto>> findAllUsers() {
        return convert(userDao.getAllUsers());
    }

    @Override
    public Optional<List<UserDto>> findUserByPhone(String phone_number){
        return convert(userDao.findUserByPhone(phone_number));
    }

    @Override
    public Optional<List<UserDto>> findUserByEmail(String email) {
        return convert(userDao.findUserByEmail(email));
    }

    @Override
    public Optional<List<UserDto>> findUserByName(String name) {
        return convert(userDao.findUserByName(name));
    }

    @Override
    public Optional<List<UserDto>> findRespondedUsers(Integer vacancy_id) {
        return convert(userDao.findRespondedUsers(vacancy_id));
    }

    private Optional<List<UserDto>>convert(List<User> users) {
        if (users == null || users.isEmpty()) {
            return Optional.empty();
        }
        List<UserDto> userDtos = new ArrayList<>();
        for (User user : users){
            userDtos.add(new UserDto(
                            user.getName(),
                            user.getSurname(),
                            user.getAge(),
                            user.getEmail(),
                            user.getPhoneNumber(),
                            user.getAvatar(),
                            user.getAccountType())
            );
        }
        return Optional.of(userDtos);
    }


    public Optional<UserDto> convert(User user) {
        if (user == null) {
            return Optional.empty();
        }
        return Optional.of(new UserDto(
                user.getName(),
                user.getSurname(),
                user.getAge(),
                user.getEmail(),
                user.getPhoneNumber(),
                user.getAvatar(),
                user.getAccountType())
        );
    }


}
