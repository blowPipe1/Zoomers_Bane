
package springboot.get_a_job.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import springboot.get_a_job.dao.*;
import springboot.get_a_job.dto.EducationDto;
import springboot.get_a_job.dto.ResumeDto;
import springboot.get_a_job.dto.WorkExperienceDto;
import springboot.get_a_job.exceptions.CategoryNotFoundException;
import springboot.get_a_job.exceptions.ResumeNotFoundException;
import springboot.get_a_job.models.Resume;


import java.time.LocalDateTime;
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
        if (resumeDto == null) {
            throw new ResumeNotFoundException("Category not found");
        }
        Integer categoryId = categoryDao.findIdByName(resumeDto.getCategory())
                .orElseThrow(() -> new CategoryNotFoundException("Категория не найдена: " + resumeDto.getCategory()));

        // TODO check for null & empty string etc.
        String[] name = resumeDto.getApplicant().split(" ");
        Integer applcantId = Integer.valueOf(userDao.findIdBySurname(name[1]));

        Resume resume = new Resume(
                0,
                applcantId,
                resumeDto.getName(),
                categoryId,
                resumeDto.getSalary(),
                resumeDto.isActive(),
                LocalDateTime.now(),
                LocalDateTime.now()
        );

        Integer resumeId = resumeDao.saveResume(resume);

        if (resumeDto.getEducation() != null  || !resumeDto.getEducation().isEmpty()) {
            for (EducationDto edu : resumeDto.getEducation()){
                educationDao.addEducationInfo(edu, resumeId);
            }
        }

        if (resumeDto.getWorkExperience() != null || !resumeDto.getWorkExperience().isEmpty()) {
            for (WorkExperienceDto workExp : resumeDto.getWorkExperience()) {
                workExperienceDao.addWorkExperience(workExp, resumeId);
            }
        }
    }

    @Override
    public void updateResume(Integer id, ResumeDto resumeDto) {
        if (resumeDao.findResumeById(id) == null) {
            throw new ResumeNotFoundException("Resume with id: " + id + " not found");
        }
        ResumeDto checkedResume = checkFieldsForNullOrEmpty(id, resumeDto);

        Integer categoryId = categoryDao.findIdByName(checkedResume.getCategory())
                .orElseThrow(() -> new CategoryNotFoundException("Category not found: " + checkedResume.getCategory()));

        // TODO check for null & empty string etc.
        String[] name = checkedResume.getApplicant().split(" ");
        Integer applcantId = Integer.valueOf(userDao.findIdBySurname(name[1]));

        Resume resume = new Resume(
                0,
                applcantId,
                checkedResume.getName(),
                categoryId,
                checkedResume.getSalary(),
                checkedResume.isActive(),
                LocalDateTime.now(),
                LocalDateTime.now()
        );

        Integer resumeId = resumeDao.updateResume(id, resume);

        if (checkedResume.getEducation() != null || !checkedResume.getEducation().isEmpty()) {
            // TODO change educationDaos update method so it updates existing object by ID(not resumes ID)
            for (EducationDto edu : resumeDto.getEducation()){
                educationDao.updateEducationInfo(edu, edu.getId());
            }

        } else {
            // TODO same needs to be done here
            for (EducationDto edu : resumeDto.getEducation()){
                educationDao.addEducationInfo(edu, resumeId);
            }

        }

        if (checkedResume.getWorkExperience() != null || !checkedResume.getWorkExperience().isEmpty()) {
            for (WorkExperienceDto workExp : resumeDto.getWorkExperience()) {
                workExperienceDao.updateWorkExperience(workExp, workExp.getId() );
            }
        } else {
            for (WorkExperienceDto workExp : resumeDto.getWorkExperience()){
                workExperienceDao.addWorkExperience(workExp, resumeId);
            }
        }


    }

    @Override
    public void deleteResume(Integer id) {
        if (resumeDao.findResumeById(id) == null) {
            throw new ResumeNotFoundException("Resume with id: " + id + " not found");
        }
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
            throw new ResumeNotFoundException("Resumes not Found");
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
            throw new ResumeNotFoundException("Resume not Found");
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

    private ResumeDto checkFieldsForNullOrEmpty(Integer id, ResumeDto newResume) {
        Optional<ResumeDto> oldResume = convert(resumeDao.findResumeById(id));
        if (oldResume.isEmpty()){
            throw new ResumeNotFoundException("Resume with id: " + id + " not found");
        }
        ResumeDto result = new ResumeDto();

        if (ifNull(newResume.getApplicant()) || newResume.getApplicant().isEmpty()) {
            result.setApplicant(oldResume.get().getApplicant());
        } else {
            result.setApplicant(newResume.getApplicant());
        }
        if (ifNull(newResume.getName()) || newResume.getName().isEmpty()) {
            result.setName(oldResume.get().getName());
        } else {
            result.setName(newResume.getName());
        }
        if (ifNull(newResume.getCategory()) || newResume.getCategory().isEmpty()) {
            result.setCategory(oldResume.get().getCategory());
        } else {
            result.setCategory(newResume.getCategory());
        }
        if (ifNull(newResume.getSalary()) || newResume.getSalary() <= 0){
            result.setSalary(oldResume.get().getSalary());
        } else {
            result.setSalary(newResume.getSalary());
        }
        if (newResume.isActive() == oldResume.get().isActive() || ifNull(newResume.isActive())) {
            result.setActive(oldResume.get().isActive());
        } else {
            result.setActive(newResume.isActive());
        }
        if (ifNull(newResume.getEducation()) || newResume.getEducation().isEmpty()) {
            result.setEducation(oldResume.get().getEducation());
        } else {
            result.setEducation(newResume.getEducation());
        }
        if (ifNull(newResume.getWorkExperience()) || newResume.getWorkExperience().isEmpty()) {
            result.setWorkExperience(oldResume.get().getWorkExperience());
        } else {
            result.setWorkExperience(newResume.getWorkExperience());
        }
        return result;
    }

    private boolean ifNull(Object object){
        return object == null;
    }
}