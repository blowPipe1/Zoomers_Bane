package springboot.get_a_job.services;

import org.springframework.web.multipart.MultipartFile;
import springboot.get_a_job.dto.UserDto;
import springboot.get_a_job.models.User;


import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface UserAccountService {
    void registerUser(User user);
    void updateUser(Integer id, User user);
    void deleteUser(Integer id);
    void saveAvatar(Integer userId, MultipartFile file) throws IOException;
    Optional<UserDto> findUserById(Integer id);
    Optional<List<UserDto>> findAllUsers();
    Optional<List<UserDto>> findUserByPhone(String phone_number);
    Optional<List<UserDto>>findUserByEmail(String email);
    Optional<List<UserDto>> findUserByName(String name);
    Optional<List<UserDto>> findRespondedUsers(Integer vacancy_id);
}
