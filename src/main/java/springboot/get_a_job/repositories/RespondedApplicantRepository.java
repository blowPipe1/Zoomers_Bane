package springboot.get_a_job.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import springboot.get_a_job.models.RespondedApplicant;

import java.util.List;

@Repository
public interface RespondedApplicantRepository extends JpaRepository<RespondedApplicant, Integer> {
    List<RespondedApplicant> findByResumeId(Integer id);
    List<RespondedApplicant> findByVacancy_Id(Integer id);
}

//TODO
// 1 Message & Responses Logic for Vacancies
// 2 Make resume-list.ftlh, vacancy-list.ftlh, dashboard.ftlh. Page<> & Pageable
// 3 Add Navigation panel to access: (depending on account type, resume-list.ftlh, vacancy-list.ftlh) in dashboard template
// 4 Add sorting by published date and responses for resume-list.ftlh, vacancy-list.ftlh templates
