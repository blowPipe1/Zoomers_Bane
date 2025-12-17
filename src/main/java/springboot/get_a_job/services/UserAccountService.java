package springboot.get_a_job.services;

import org.springframework.web.multipart.MultipartFile;
import springboot.get_a_job.dto.UserDto;
import springboot.get_a_job.models.User;


import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface UserAccountService {
    UserDto registerUser(User user);
    void saveAvatar(Integer userId, MultipartFile file) throws IOException;
    Optional<UserDto> findUserById(Integer id);
    Optional<List<UserDto>> findAllUsers();
    Optional<UserDto> findUserByPhone(String phone_number);
    Optional<UserDto> findUserByEmail(String email);
    Optional<UserDto> findUserByName(String name);
}
