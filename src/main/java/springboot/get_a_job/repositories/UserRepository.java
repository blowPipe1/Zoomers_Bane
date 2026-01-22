package springboot.get_a_job.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import springboot.get_a_job.models.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByEmail(String email);

    List<User> findAllByPhoneNumberContainingIgnoreCase(String phoneNumber);

    @Query("SELECT u.id FROM User u WHERE u.email = :email")
    Optional<Integer> findIdByEmail(@Param("email") String email);

    @Query("SELECT u FROM User u " +
            "JOIN Resume r ON u.id = r.applicant.id " +
            "JOIN RespondedApplicant ra ON r.id = ra.resume.id " +
            "WHERE ra.vacancy.id = :vacancyId")
    List<User> findRespondedUsers(@Param("vacancyId") Integer vacancyId);

    @Query("SELECT u.email FROM User u WHERE u.id = :id")
    Optional<String> findEmailById(@Param("id") Integer id);


    @Modifying
    @Query("UPDATE User u SET u.avatar = :avatarPath WHERE u.id = :id")
    void updateAvatarPath(@Param("id") Integer id, @Param("avatarPath") String avatarPath);

}
