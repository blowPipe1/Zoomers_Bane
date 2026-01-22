package springboot.get_a_job.services;

import org.springframework.web.multipart.MultipartFile;
import springboot.get_a_job.dto.UserDto;
import springboot.get_a_job.models.User;


import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface UserAccountService {
    void registerUser(UserDto userDto);
    void updateUser(Integer id, UserDto userDto);
    void deleteUser(Integer id);
    void saveAvatar(Integer userId, MultipartFile file) throws IOException;
    Optional<UserDto> findUserById(Integer id);
    String findNameById(Integer id);
    Optional<Integer> findIdByEmail(String email);
    Optional<List<UserDto>>findAllUsers();
}
