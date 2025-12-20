package springboot.get_a_job.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;
import springboot.get_a_job.models.User;

import java.util.List;

@Component
@RequiredArgsConstructor
public class UserDao {
    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public List<User> getAllUsers() {
        String sql = "select * from users";
        return jdbcTemplate.query(sql,new BeanPropertyRowMapper<>(User.class));
    }

    public User findUserById(int id) {
        String sql = "select * from USERS\n" +
                "where id = ?;";
        return jdbcTemplate.queryForObject(sql,new BeanPropertyRowMapper<>(User.class), id);
    }

    public List<User> findUserByPhone(String phone_number) {
        String sql = "select * from USERS\n" +
                "where phone_number ilike :phone_number;";
        return namedParameterJdbcTemplate.query(
                sql,
                new MapSqlParameterSource()
                        .addValue("phone_number", phone_number),
                new BeanPropertyRowMapper<>(User.class));
    }

    public List<User> findUserByName(String name) {
        String sql = "SELECT * FROM USERS WHERE name ILIKE :name;";

        return namedParameterJdbcTemplate.query(
                sql,
                new MapSqlParameterSource()
                        .addValue("name", name),
                new BeanPropertyRowMapper<>(User.class));
    }

    public List<User> findUserByEmail(String email) {
        String sql = "select * from USERS\n" +
                "where email ilike :email;";
        return namedParameterJdbcTemplate.query(
                sql,
                new MapSqlParameterSource()
                        .addValue("email", email),
                new BeanPropertyRowMapper<>(User.class));
    }

    public List<User> findRespondedUsers(Integer vacancy_id) {
        String sql = "SELECT u.* FROM users u\n" +
                "    JOIN resumes r ON u.id = r.applicant_id\n" +
                "    JOIN responded_applicants ra ON r.id = ra.resume_id\n" +
                "    WHERE ra.vacancy_id = :id;";
        return namedParameterJdbcTemplate.query(
                sql,
                new MapSqlParameterSource()
                        .addValue("id", vacancy_id ),
                new BeanPropertyRowMapper<>(User.class));
    }

    public String findNameById(Integer id) {
        String sql = "SELECT concat(name, ' ' , surname) as name  FROM users WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, String.class, id);
    }

    public String findIdBySurname(String surname) {
        String sql = "SELECT id  FROM users WHERE SURNAME ilike ?";
        return jdbcTemplate.queryForObject(sql, String.class, surname);
    }

    public void updateAvatarPath(Integer userId, String savedPath){
        String sql = "UPDATE USERS SET AVATAR = ? WHERE id = ?";
        jdbcTemplate.update(sql,savedPath,userId);
    }

    public void registerUser(String name, String surname, Integer age, String email, String password, String phone_number, String avatar, String account_type){
        String sql = "INSERT INTO USERS (name, surname, age, email, password, phone_number, avatar, account_type) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, name, surname, age, email, password, phone_number, avatar, account_type);
    }

    public void updateUser(Integer userId, String name, String surname, Integer age, String email, String password, String phone_number, String avatar, String account_type){
        String sql = "update USERS set name = ?, surname = ?, age = ?, email = ?, password = ?, phone_number = ?, avatar = ?, account_type = ? where id = ?";
        jdbcTemplate.update(sql, name, surname, age, email, password, phone_number, avatar, account_type, userId);
    }

    public void deleteUser(Integer userId) {
        String sql = "update USERS set name = ?, surname = ?, age = ?, email = ?, password = ?, phone_number = ?, avatar = ?, account_type = ? where id = ?";
        jdbcTemplate.update(sql, "deleted", "deleted", -1, "deleted", "deleted", "deleted", "deleted", "deleted", userId);
    }

    public void deleteUserHard(Integer userId) {
        String sql = "delete from USERS where id = ?";
        jdbcTemplate.update(sql, userId);
    }

}
