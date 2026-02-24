package springboot.get_a_job.serviceImplementations;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import springboot.get_a_job.dto.UserDto;
import springboot.get_a_job.exceptions.EmailAlreadyExistsException;
import springboot.get_a_job.exceptions.InvalidAccountTypeException;
import springboot.get_a_job.exceptions.UserNotFoundException;
import springboot.get_a_job.models.User;
import springboot.get_a_job.repositories.UserRepository;
import springboot.get_a_job.services.ResumeService;
import springboot.get_a_job.services.UserAccountService;
import springboot.get_a_job.services.VacancyService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserAccountServiceImpl implements UserAccountService {
    @Value("${upload.path}")
    private String uploadPath;
    @Autowired
    @Lazy
    private UserRepository userRepository;
    @Autowired
    @Lazy
    private  ResumeService resumeService;
    @Autowired
    @Lazy
    private  VacancyService vacancyService;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void registerUser(UserDto userDto) {
        if (userDto == null) {
            throw new UserNotFoundException("Error registering user: DTO is null");
        }
        if (userRepository.existsByEmail(userDto.getEmail())) {
            throw new EmailAlreadyExistsException("error.duplicate-email");
        }

        User user = convertIntoModel(userDto);
        userRepository.save(user);
        log.info("Server Successfully registered user: {} {} (Email: {})",
                user.getName(), user.getSurname(), user.getEmail());
    }

    @Override
    @Transactional
    public void updateUser(Integer id, UserDto userDto) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User with id " + id + " not found"));

        updateFields(user, userDto);

        userRepository.save(user);
        log.info("Server Successfully updated user(ID {}): (Email: {})", id, user.getEmail());
    }

    @Override
    @Transactional
    public void deleteUser(Integer userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        if (!resumeService.findAllByApplicantId(userId).isEmpty()) {
            throw new RuntimeException("User has Resumes attached to their id");
        }
        if (!vacancyService.findAllByAuthorId(userId).isEmpty()) {
            throw new RuntimeException("User has Vacancies attached to their id");
        }

        userRepository.delete(user);
        log.info("Server Successfully deleted user(ID: {})", userId);
    }

    @Override
    @Transactional
    public void saveAvatar(Integer userId, MultipartFile file) throws IOException {
        if (file.isEmpty()) throw new IllegalArgumentException("File is empty");

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        Path root = Paths.get(uploadPath).toAbsolutePath().normalize();
        if (!Files.exists(root)) {
            Files.createDirectories(root);
        }

        String fileName = userId + "_" + System.currentTimeMillis() + "_" + file.getOriginalFilename();

        Path filePath = root.resolve(fileName);
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        user.setAvatar(fileName);

        log.info("Server Successfully updated user avatar (ID: {}) to {}", userId, fileName);
    }

    @Override
    public Optional<UserDto> findUserById(Integer id) {
        return userRepository.findById(id).map(this::convert);
    }

    @Override
    public String findNameById(Integer id) {
        return userRepository.findEmailById(id).orElse(null);
    }

    @Override
    public Optional<Integer> findIdByEmail(String email) {
        return userRepository.findIdByEmail(email);
    }

    @Override
    public Optional<List<UserDto>>findAllUsers(){
        return Optional.of(userRepository.findAll().stream().map(this::convert).toList());
    }

    @Override
    public Optional<User>findByEmail(String email){
        return userRepository.findByEmail(email);
    }

    private User convertIntoModel(UserDto dto) {
        if (!isValidAccountType(dto.getAccountType())) {
            throw new InvalidAccountTypeException("Invalid account type: " + dto.getAccountType());
        }
        User user = new User();
        user.setName(dto.getName());
        user.setSurname(dto.getSurname());
        user.setAge(dto.getAge());
        user.setEmail(dto.getEmail());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setPhoneNumber(dto.getPhoneNumber());
        user.setAvatar(dto.getAvatar());
        user.setAccountType(dto.getAccountType().toLowerCase().trim());
        return user;
    }

    private void updateFields(User user, UserDto dto) {
        if (dto.getName() != null) user.setName(dto.getName());
        if (dto.getSurname() != null) user.setSurname(dto.getSurname());
        if (dto.getAge() != null) user.setAge(dto.getAge());
        if (dto.getPhoneNumber() != null) user.setPhoneNumber(dto.getPhoneNumber());
        if (dto.getEmail() != null) user.setEmail(dto.getEmail());

        if (dto.getPassword() != null && !dto.getPassword().isBlank()) {
            user.setPassword(passwordEncoder.encode(dto.getPassword()));
        }
    }

    private UserDto convert(User user) {
        return new UserDto(
                user.getName(),
                user.getSurname(),
                user.getAge(),
                user.getEmail(),
                null,
                user.getPhoneNumber(),
                user.getAvatar(),
                user.getAccountType()
        );
    }

    private boolean isValidAccountType(String type) {
        if (type == null) return false;
        String t = type.trim().toLowerCase();
        return t.equals("employer") || t.equals("applicant");
    }
}




