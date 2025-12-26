package springboot.get_a_job.dao;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;
import springboot.get_a_job.models.User;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class UserDao {
    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public List<User> getAllUsers() {
        String sql = "select * from users";
        try {
            log.debug("Fetching all Users");
            return jdbcTemplate.query(sql,new BeanPropertyRowMapper<>(User.class));
        } catch (EmptyResultDataAccessException e) {
            log.debug("No Users found");
            return null;
        }
    }

    public User findUserById(int id) {
        String sql = "select * from USERS where id = ?;";
        try {
            log.debug("Fetching User with id {}", id);
            return jdbcTemplate.queryForObject(sql,new BeanPropertyRowMapper<>(User.class), id);
        } catch (EmptyResultDataAccessException e) {
            log.warn("User with id {} not found", id);
            return null;
        }
    }

    public List<User> findUserByPhone(String phone_number) {
        String sql = "select * from USERS\n" +
                "where phone_number ilike :phone_number;";
        try {
            log.debug("Fetching User with phone number: {}", phone_number);
            return namedParameterJdbcTemplate.query(
                    sql,
                    new MapSqlParameterSource()
                            .addValue("phone_number", phone_number),
                    new BeanPropertyRowMapper<>(User.class));
        } catch (EmptyResultDataAccessException e) {
            log.warn("No Users with phone number: {} were found", phone_number);
            return null;
        }

    }

    public List<User> findUserByName(String name) {
        String sql = "SELECT * FROM USERS WHERE name ILIKE :name;";
        try {
            log.debug("Fetching Users with name {}", name);
            return namedParameterJdbcTemplate.query(
                    sql,
                    new MapSqlParameterSource()
                            .addValue("name", name),
                    new BeanPropertyRowMapper<>(User.class));
        } catch (EmptyResultDataAccessException e) {
            log.warn("No Users with name {} were found", name);
            return null;
        }
    }

    public List<User> findUserByEmail(String email) {
        String sql = "select * from USERS\n" +
                "where email ilike :email;";
        try {
            log.debug("Fetching Users with email {}", email);
            return namedParameterJdbcTemplate.query(
                    sql,
                    new MapSqlParameterSource()
                            .addValue("email", email),
                    new BeanPropertyRowMapper<>(User.class));
        } catch (EmptyResultDataAccessException e) {
            log.warn("No Users with email {} were found", email);
            return null;
        }
    }

    public List<User> findRespondedUsers(Integer vacancy_id) {
        String sql = "SELECT u.* FROM users u\n" +
                "    JOIN resumes r ON u.id = r.applicant_id\n" +
                "    JOIN responded_applicants ra ON r.id = ra.resume_id\n" +
                "    WHERE ra.vacancy_id = :id;";
        try {
            log.debug("Fetching Users responded to Vacancy(ID): {}", vacancy_id);
            return namedParameterJdbcTemplate.query(
                    sql,
                    new MapSqlParameterSource()
                            .addValue("id", vacancy_id ),
                    new BeanPropertyRowMapper<>(User.class));
        } catch (EmptyResultDataAccessException e) {
            log.warn("No Users responded to Vacancy(ID): {} were found", vacancy_id);
            return null;
        }
    }

    public String findNameById(Integer id) {
        String sql = "SELECT concat(name, ' ' , surname) as name  FROM users WHERE id = ?";
        try {
            log.debug("Fetching full name of User with ID: {}", id);
            return jdbcTemplate.queryForObject(sql, String.class, id);
        } catch (EmptyResultDataAccessException e) {
            log.warn("No Name of User with ID {} was found", id);
            return null;
        }
    }

    public String findIdBySurname(String surname) {
        String sql = "SELECT id  FROM users WHERE SURNAME ilike ?";
        try {
            log.debug("Fetching ID  of User with surname: {}", surname);
            return jdbcTemplate.queryForObject(sql, String.class, surname);
        } catch (EmptyResultDataAccessException e) {
            log.warn("No ID of User with surname {} was found", surname);
            return null;
        }
    }

    public void updateAvatarPath(Integer userId, String savedPath){
        String sql = "UPDATE USERS SET AVATAR = ? WHERE id = ?";
        jdbcTemplate.update(sql,savedPath,userId);
    }

    public void registerUser(User user){
        String sql = "INSERT INTO USERS (name, surname, age, email, password, phone_number, avatar, account_type) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        log.debug("Executing SQL to Save User: {}", sql);
        jdbcTemplate.update(sql, user.getName(), user.getSurname(), user.getAge(), user.getEmail(), user.getPassword(), user.getPhoneNumber(), user.getAvatar(), user.getAccountType());
    }

    public void updateUser(Integer userId, User user){
        String sql = "update USERS set name = ?, surname = ?, age = ?, email = ?, password = ?, phone_number = ?, avatar = ?, account_type = ? where id = ?";
        log.debug("Executing SQL to Update User: {}", sql);
        jdbcTemplate.update(sql, user.getName(), user.getSurname(), user.getAge(), user.getEmail(), user.getPassword(), user.getPhoneNumber(), user.getAvatar(), user.getAccountType(), userId);
    }

    public void deleteUser(Integer userId) {
        String sql = "update USERS set name = ?, surname = ?, age = ?, email = ?, password = ?, phone_number = ?, avatar = ?, account_type = ? where id = ?";
        log.debug("Executing SQL to Delete User's data: {}", sql);
        jdbcTemplate.update(sql, "deleted", "deleted", -1, "deleted", "deleted", "deleted", "deleted", "deleted", userId);
    }

    public void deleteUserHard(Integer userId) {
        String sql = "delete from USERS where id = ?";
        log.debug("Executing SQL to Delete User completely: {}", sql);
        jdbcTemplate.update(sql, userId);
    }

}
