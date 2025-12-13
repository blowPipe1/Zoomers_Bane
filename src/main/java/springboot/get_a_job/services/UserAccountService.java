package springboot.get_a_job.services;

import org.springframework.web.multipart.MultipartFile;
import springboot.get_a_job.dto.UserDto;
import springboot.get_a_job.models.User;


import java.io.IOException;
import java.util.Optional;

public interface UserAccountService {
    UserDto registerUser(User user);
    void saveAvatar(Integer userId, MultipartFile file) throws IOException;
    Optional<UserDto> findUserById(Integer id);
}
