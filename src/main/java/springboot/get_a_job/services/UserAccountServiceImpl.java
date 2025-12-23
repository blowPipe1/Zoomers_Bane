package springboot.get_a_job.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import springboot.get_a_job.dao.ResumeDao;
import springboot.get_a_job.dao.UserDao;
import springboot.get_a_job.dao.VacancyDao;
import springboot.get_a_job.dto.UserDto;
import springboot.get_a_job.exceptions.UserNotFoundException;
import springboot.get_a_job.models.User;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserAccountServiceImpl implements UserAccountService {
    private final String subDir = "src/main/java/springboot/get_a_job/data/images/";
    private final UserDao userDao;
    private final ResumeDao resumeDao;
    private final VacancyDao vacancyDao;

    @Override
    public void registerUser(User user) {
        //TODO add logic to check for necessary fields
        if (user == null) {
            throw new UserNotFoundException("Error registering user");
        }
        userDao.registerUser(
                user.getName(),
                user.getSurname(),
                user.getAge(),
                user.getEmail(),
                user.getPassword(),
                user.getPhoneNumber(),
                user.getAvatar(),
                user.getAccountType()
        );
    }

    @Override
    public void updateUser(Integer id, User user) {
        //TODO add logic to check for necessary fields
        if (user == null) {
            throw new UserNotFoundException("User not found");
        }
        User result = checkFieldsForNullOrEmpty(id, user);

        userDao.updateUser(
                id,
                result.getName(),
                result.getSurname(),
                result.getAge(),
                result.getEmail(),
                result.getPassword(),
                result.getPhoneNumber(),
                result.getAvatar(),
                result.getAccountType()
        );
    }

    @Override
    public void deleteUser(Integer userId) {
        if (userDao.findUserById(userId) == null) {
            throw new UserNotFoundException("User not found");
        }
        if (!resumeDao.findResumeByCreator(userId).isEmpty()) {
            throw new RuntimeException("User has Resume attached to their id");
        }
        if(!vacancyDao.findVacancyByCreator(userId).isEmpty()) {
            throw new RuntimeException("User has Vacancy attached to their id");
        }
        //TODO add logic to check for necessary fields
        userDao.deleteUserHard(userId);
    }

    @Override
    public void saveAvatar(Integer userId, MultipartFile file) throws IOException{
        if (file.isEmpty()) {
            throw new IllegalArgumentException("File is empty");
        }
        Path uploadPath = Paths.get(subDir);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        String fileName = userId + "_" + System.currentTimeMillis() + "_" + file.getOriginalFilename();
        Path filePath = uploadPath.resolve(fileName);

        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        String savedPath = filePath.toString();
        userDao.updateAvatarPath(userId, savedPath);
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
            throw new UserNotFoundException("User not found");
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

    private Optional<UserDto> convert(User user) {
        if (user == null) {
            throw new UserNotFoundException("User not found");
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

    private User checkFieldsForNullOrEmpty(Integer id, User newUser){
        User oldUser = userDao.findUserById(id);
        User result = new User();

        if (ifNull(newUser.getName()) || newUser.getName().isEmpty()) {
            result.setName(oldUser.getName());
        } else {
            result.setName(newUser.getName());
        }

        if (ifNull(newUser.getSurname()) || newUser.getSurname().isEmpty()) {
            result.setSurname(oldUser.getSurname());
        } else {
            result.setSurname(newUser.getSurname());
        }

        if (ifNull(newUser.getAge()) || newUser.getAge() <= 0) {
            result.setAge(oldUser.getAge());
        } else {
            result.setAge(newUser.getAge());
        }

        if (ifNull(newUser.getEmail()) || newUser.getEmail().isEmpty()) {
            result.setEmail(oldUser.getEmail());
        } else {
            result.setEmail(newUser.getEmail());
        }

        if (ifNull(newUser.getPassword()) || newUser.getPassword().isEmpty()) {
            result.setPassword(oldUser.getPassword());
        } else {
            result.setPassword(newUser.getPassword());
        }

        if (ifNull(newUser.getPhoneNumber()) || newUser.getPhoneNumber().isEmpty()) {
            result.setPhoneNumber(oldUser.getPhoneNumber());
        } else {
            result.setPhoneNumber(newUser.getPhoneNumber());
        }

        if (ifNull(newUser.getAvatar()) || newUser.getAvatar().isEmpty()) {
            result.setAvatar(oldUser.getAvatar());
        } else {
            result.setAvatar(newUser.getAvatar());
        }

        if (ifNull(newUser.getAccountType()) || newUser.getAccountType().isEmpty()) {
            result.setAccountType(oldUser.getAccountType());
        } else {
            result.setAccountType(newUser.getAccountType());
        }
        return result;
    }

    private boolean ifNull(Object object){
        return object == null;
    }
}
