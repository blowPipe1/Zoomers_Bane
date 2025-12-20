
package springboot.get_a_job.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import springboot.get_a_job.dao.*;
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
    private final UserDao userDao;

    @Override
    public void createResume(ResumeDto resumeDto) {
        Integer categoryId = categoryDao.findIdByName(resumeDto.getCategory())
                .orElseThrow(() -> new RuntimeException("Категория не найдена: " + resumeDto.getCategory()));

        // TODO check for null & empty string etc.
        String[] name = resumeDto.getApplicant().split(" ");
        Integer applcantId = Integer.valueOf(userDao.findIdBySurname(name[1]));


        Integer resumeId = resumeDao.saveResume(applcantId, resumeDto.getName(), categoryId, resumeDto.getSalary(), resumeDto.isActive());

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

            Integer applcantId = Integer.valueOf(userDao.findIdBySurname(resumeDto.getApplicant()));

            Integer resumeId = resumeDao.updateResume(id, applcantId, resumeDto.getName(), categoryId, resumeDto.getSalary(), resumeDto.isActive());

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
        if (resumeDao.findResumeById(id) == null) {
            throw new RuntimeException("Resume with id: " + id + " not found");
        }else {
            if (educationDao.getResumesEducationInfo(id) != null) {
                for (EducationDto edu : educationDao.getResumesEducationInfo(id)) {
                    educationDao.deleteEducationInfo(id);
                }
            }
            if (workExperienceDao.getResumesWorkExperience(id) != null) {
                for (WorkExperienceDto we : workExperienceDao.getResumesWorkExperience(id)) {
                    workExperienceDao.deleteWorkExperienceInfo(id);
                }
            }
            resumeDao.deleteResume(id);
        }
    }

    @Override
    public Optional<List<ResumeDto>> getAllActiveResumes() {
        return convert(resumeDao.getAllActiveResumes());
    }

    @Override
    public Optional<ResumeDto> findResumeById(Integer id) {
        return convert(resumeDao.findResumeById(id));
    }

    @Override
    public Optional<List<ResumeDto>> findResumeByCategory(Integer category_id) {
        return convert(resumeDao.findResumeByCategory(category_id));

    }

    @Override
    public Optional<List<ResumeDto>> findResumeByCategory(String category1) {
        return convert(resumeDao.findResumeByCategory(category1));
    }

    @Override
    public Optional<List<ResumeDto>> findResumeByCreator(Integer applicant_id) {
        return convert(resumeDao.findResumeByCreator(applicant_id));
    }

    @Override
    public Optional<List<ResumeDto>> findResumeByCreator(String creatorName) {
        return convert(resumeDao.findResumeByCreator(creatorName));
    }

    private Optional<List<ResumeDto>>convert(List<Resume>resumes){
        if (resumes == null || resumes.isEmpty()) {
            return Optional.empty();
        }
        List<ResumeDto>resumeDtos = new ArrayList<>();
        for (Resume resume : resumes) {
            List<EducationDto>educationInfo = educationDao.getResumesEducationInfo(resume.getId());
            List<WorkExperienceDto>workExperienceInfo = workExperienceDao.getResumesWorkExperience(resume.getId());


            resumeDtos.add(new ResumeDto(
                    userDao.findNameById(resume.getApplicantId()),
                    resume.getName(),
                    categoryDao.findNameById(resume.categoryId),
                    resume.getSalary(),
                    resume.getIsActive(),
                    educationInfo,
                    workExperienceInfo
            ));
        }
        return Optional.of(resumeDtos);
    }

    private Optional<ResumeDto>convert(Resume resume){
        if (resume == null) {
            return Optional.empty();
        }
        List<EducationDto>educationInfo = educationDao.getResumesEducationInfo(resume.getId());
        List<WorkExperienceDto>workExperienceInfo = workExperienceDao.getResumesWorkExperience(resume.getId());
        return Optional.of(new ResumeDto(
                userDao.findNameById(resume.getApplicantId()),
                resume.getName(),
                categoryDao.findNameById(resume.categoryId),
                resume.getSalary(),
                resume.getIsActive(),
                educationInfo,
                workExperienceInfo
        ));
    }
}