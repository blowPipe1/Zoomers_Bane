package springboot.get_a_job.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import springboot.get_a_job.dao.CategoryDao;
import springboot.get_a_job.dao.EducationInfoDao;
import springboot.get_a_job.dao.ResumeDao;
import springboot.get_a_job.dao.WorkExperienceDao;
import springboot.get_a_job.dto.ResumeDto;
import springboot.get_a_job.models.Resume;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ResumeServiceImpl implements ResumeService {
    private final ResumeDao resumeDao;
    private final CategoryDao categoryDao;
    private final EducationInfoDao educationDao;
    private final WorkExperienceDao workExperienceDao;

    @Override
    public void createResume(ResumeDto resumeDto) {

        Integer categoryId = categoryDao.findIdByName(resumeDto.getCategory())
                .orElseThrow(() -> new RuntimeException("Категория не найдена: " + resumeDto.getCategory()));

        Integer resumeId = resumeDao.saveResume(resumeDto.getApplicantId(), resumeDto.getName(), categoryId, resumeDto.getSalary(), resumeDto.isActive());

        if (resumeDto.getEducation() != null) {
            educationDao.addEducationInfo(resumeDto, resumeId);
        }

        if (resumeDto.getWorkExperience() != null) {
            workExperienceDao.addWorkExperience(resumeDto, resumeId );
        }
    }

    @Override
    public void updateResume(Integer id, ResumeDto resumeDto) {
        if (resumeDao.findResumeById(id) == null) {
            throw new RuntimeException("Resume with id: " + id + " not found");
        } else {
            Integer categoryId = categoryDao.findIdByName(resumeDto.getCategory())
                    .orElseThrow(() -> new RuntimeException("Категория не найдена: " + resumeDto.getCategory()));

            Integer resumeId = resumeDao.updateResume(id, resumeDto.getApplicantId(), resumeDto.getName(), categoryId, resumeDto.getSalary(), resumeDto.isActive());

            if (resumeDto.getEducation() != null) {
                educationDao.updateEducationInfo(resumeDto, resumeId);
            }

            if (resumeDto.getWorkExperience() != null) {
                workExperienceDao.updateWorkExperience(resumeDto, resumeId );
            }
        }

    }

    @Override
    public void deleteResume(Integer id) {
        System.out.println("Deleting resume (ID) " + id);
        //TODO Логика удаления записи из БД
    }

    @Override
    public Optional<List<Resume>> getAllActiveResumes() {
        if (resumeDao.getAllActiveResumes().isEmpty()) {
            return Optional.empty();
        } else {
            return Optional.of(resumeDao.getAllActiveResumes());
        }
    }

    @Override
    public Optional<Resume> findResumeById(Integer id) {
        if (resumeDao.findResumeById(id) == null) {
            return Optional.empty();
        } else {
            return Optional.of(resumeDao.findResumeById(id));
        }
    }

    @Override
    public Optional<List<Resume>> findResumeByCategory(Integer category_id) {
        if (resumeDao.findResumeByCategory(category_id).isEmpty()) {
            return Optional.empty();
        } else {
            return Optional.of(resumeDao.findResumeByCategory(category_id));
        }
    }

    @Override
    public Optional<List<Resume>> findResumeByCategory(String category) {
        if (resumeDao.findResumeByCategory(category).isEmpty()) {
            return Optional.empty();
        } else {
            return Optional.of(resumeDao.findResumeByCategory(category));
        }
    }

    @Override
    public Optional<List<Resume>> findResumeByCreator(Integer applicant_id) {
        if (resumeDao.findResumeByCreator(applicant_id).isEmpty()) {
            return Optional.empty();
        } else {
            return Optional.of(resumeDao.findResumeByCreator(applicant_id));
        }
    }

    @Override
    public Optional<List<Resume>> findResumeByCreator(String creatorName) {
        if (resumeDao.findResumeByCreator(creatorName).isEmpty()) {
            return Optional.empty();
        } else {
            return Optional.of(resumeDao.findResumeByCreator(creatorName));
        }
    }
}
