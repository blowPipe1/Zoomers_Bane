package springboot.get_a_job.services;


import springboot.get_a_job.dto.UserDto;
import springboot.get_a_job.models.User;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

public interface UserAccountService {
    UserDto registerUser(User user);
    void saveAvatar(Integer userId, File file) throws IOException, IllegalArgumentException;
    Optional<UserDto> findUserDtoById(Integer id);
}
