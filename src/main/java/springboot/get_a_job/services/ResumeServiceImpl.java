package springboot.get_a_job.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import springboot.get_a_job.dao.ResumeDao;
import springboot.get_a_job.models.Resume;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ResumeServiceImpl implements ResumeService {
    private final ResumeDao resumeDao;

    @Override
    public Resume createResume(Resume resume) {
        System.out.println("Creating resume " + resume.getName());
        //TODO Логика сохранения резюме в БД
        resume.setId(1);
        resume.setCreatedDate(LocalDateTime.now());
        return resume;
    }

    @Override
    public Resume updateResume(Integer id, Resume resumeDetails) {
        System.out.println("Updating resume (ID): " + id);
        //TODO Логика обновления записи в БД
        resumeDetails.setId(id);
        resumeDetails.setUpdateTime(LocalDateTime.now());
        return resumeDetails;
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
