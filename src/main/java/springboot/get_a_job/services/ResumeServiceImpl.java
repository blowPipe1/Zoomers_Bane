
package springboot.get_a_job.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import springboot.get_a_job.dao.CategoryDao;
import springboot.get_a_job.dao.EducationInfoDao;
import springboot.get_a_job.dao.ResumeDao;
import springboot.get_a_job.dao.WorkExperienceDao;
import springboot.get_a_job.dto.EducationDto;
import springboot.get_a_job.dto.ResumeDto;
import springboot.get_a_job.dto.WorkExperienceDto;
import springboot.get_a_job.models.Resume;

import java.util.ArrayList;
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
    public Optional<List<ResumeDto>> getAllActiveResumes() {
        List<ResumeDto>resumeDtos = new ArrayList<>();
        for (Resume resume : resumeDao.getAllActiveResumes()){
            String category = categoryDao.findNameById(resume.categoryId);
            List<EducationDto>educationInfo = educationDao.getResumesEducationInfo(resume.getId());
            List<WorkExperienceDto>workExperienceInfo = workExperienceDao.getResumesWorkExperience(resume.getId());

            resumeDtos.add(new ResumeDto(
                    resume.getApplicantId(),
                    resume.getName(),
                    category,
                    resume.getSalary(),
                    resume.getIsActive(),
                    educationInfo,
                    workExperienceInfo
            ));
        }
        return Optional.of(resumeDtos);
    }

    @Override
    public Optional<ResumeDto> findResumeById(Integer id) {
        Resume resume = resumeDao.findResumeById(id);
        List<EducationDto> education = educationDao.getResumesEducationInfo(id);
        List<WorkExperienceDto> workExperience = workExperienceDao.getResumesWorkExperience(id);
        String category = categoryDao.findNameById(resume.categoryId);

        ResumeDto resumeDto = new ResumeDto(
                resume.getApplicantId(),
                resume.getName(),
                category,
                resume.getSalary(),
                resume.getIsActive(),
                education,
                workExperience
        );

        if (resumeDao.findResumeById(id) == null) {
            return Optional.empty();
        } else {
            return Optional.of(resumeDto);
        }
    }

    @Override
    public Optional<List<ResumeDto>> findResumeByCategory(Integer category_id) {
        List<ResumeDto>resumeDtos = new ArrayList<>();
        for (Resume resume : resumeDao.findResumeByCategory(category_id)) {
            String category = categoryDao.findNameById(resume.categoryId);
            List<EducationDto>educationInfo = educationDao.getResumesEducationInfo(resume.getId());
            List<WorkExperienceDto>workExperienceInfo = workExperienceDao.getResumesWorkExperience(resume.getId());

            resumeDtos.add(new ResumeDto(
                    resume.getApplicantId(),
                    resume.getName(),
                    category,
                    resume.getSalary(),
                    resume.getIsActive(),
                    educationInfo,
                    workExperienceInfo
            ));
        }
        return Optional.of(resumeDtos);
    }

    @Override
    public Optional<List<ResumeDto>> findResumeByCategory(String category1) {
        List<ResumeDto>resumeDtos = new ArrayList<>();
        for (Resume resume : resumeDao.findResumeByCategory(category1)) {
            String category = categoryDao.findNameById(resume.categoryId);
            List<EducationDto>educationInfo = educationDao.getResumesEducationInfo(resume.getId());
            List<WorkExperienceDto>workExperienceInfo = workExperienceDao.getResumesWorkExperience(resume.getId());

            resumeDtos.add(new ResumeDto(
                    resume.getApplicantId(),
                    resume.getName(),
                    category,
                    resume.getSalary(),
                    resume.getIsActive(),
                    educationInfo,
                    workExperienceInfo
            ));
        }
        return Optional.of(resumeDtos);
    }

    @Override
    public Optional<List<ResumeDto>> findResumeByCreator(Integer applicant_id) {
        List<ResumeDto>resumeDtos = new ArrayList<>();
        for (Resume resume : resumeDao.findResumeByCreator(applicant_id)) {
            String category = categoryDao.findNameById(resume.categoryId);
            List<EducationDto>educationInfo = educationDao.getResumesEducationInfo(resume.getId());
            List<WorkExperienceDto>workExperienceInfo = workExperienceDao.getResumesWorkExperience(resume.getId());

            resumeDtos.add(new ResumeDto(
                    resume.getApplicantId(),
                    resume.getName(),
                    category,
                    resume.getSalary(),
                    resume.getIsActive(),
                    educationInfo,
                    workExperienceInfo
            ));
        }
        return Optional.of(resumeDtos);
    }

    @Override
    public Optional<List<ResumeDto>> findResumeByCreator(String creatorName) {
        List<ResumeDto>resumeDtos = new ArrayList<>();
        for (Resume resume : resumeDao.findResumeByCreator(creatorName)) {
            String category = categoryDao.findNameById(resume.categoryId);
            List<EducationDto>educationInfo = educationDao.getResumesEducationInfo(resume.getId());
            List<WorkExperienceDto>workExperienceInfo = workExperienceDao.getResumesWorkExperience(resume.getId());

            resumeDtos.add(new ResumeDto(
                    resume.getApplicantId(),
                    resume.getName(),
                    category,
                    resume.getSalary(),
                    resume.getIsActive(),
                    educationInfo,
                    workExperienceInfo
            ));
        }
        return Optional.of(resumeDtos);
    }
}
