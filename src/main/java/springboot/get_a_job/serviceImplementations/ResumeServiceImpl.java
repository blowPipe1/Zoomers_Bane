
package springboot.get_a_job.serviceImplementations;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import springboot.get_a_job.dao.*;
import springboot.get_a_job.dto.ContactInfoDto;
import springboot.get_a_job.dto.EducationDto;
import springboot.get_a_job.dto.ResumeDto;
import springboot.get_a_job.dto.WorkExperienceDto;
import springboot.get_a_job.exceptions.CategoryNotFoundException;
import springboot.get_a_job.exceptions.ResumeNotFoundException;
import springboot.get_a_job.exceptions.UserNotFoundException;
import springboot.get_a_job.models.Resume;
import springboot.get_a_job.services.*;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ResumeServiceImpl implements ResumeService {
    private final ResumeDao resumeDao;
    private final CategoryService categoryService;
    private final UserAccountService userAccountService;
    private final EducationInfoService educationInfoService;
    private final WorkExperienceService workExperienceService;
    private final ContactInfoService contactInfoService;

    @Override
    public void createResume(ResumeDto resumeDto) {
        if (resumeDto == null) {
            throw new ResumeNotFoundException("Category not found");
        }

        Integer resumeId = resumeDao.saveResume(convertIntoModel(resumeDto));
        log.info("Server Successfully created Resume with ID {} ", resumeId);

        if (resumeDto.getEducation() != null && !resumeDto.getEducation().isEmpty()) {
            educationInfoService.addEducationInfo(resumeId, resumeDto.getEducation());
        }

        if (resumeDto.getWorkExperience() != null && !resumeDto.getWorkExperience().isEmpty()) {
            workExperienceService.addWorkExperienceInfo(resumeId, resumeDto.getWorkExperience());
        }

        if (resumeDto.getContactInfo() != null && !resumeDto.getContactInfo().isEmpty()) {
            contactInfoService.addContactInfo(resumeId, resumeDto.getContactInfo());
        }
    }

    @Override
    public void updateResume(Integer id, ResumeDto resumeDto) {
        if (resumeDao.findResumeById(id) == null) {
            throw new ResumeNotFoundException("Resume with id: " + id + " not found");
        }
        ResumeDto checkedResume = checkFieldsForNullOrEmpty(id, resumeDto);

        Integer resumeId = resumeDao.updateResume(id, convertIntoModel(checkedResume));

        educationInfoService.updateOrAddEducationInfo(resumeId, checkedResume.getEducation());

        workExperienceService.updateOrAddWorkExp(resumeId, checkedResume.getWorkExperience());

        contactInfoService.updateOrAddContactInfo(resumeId, checkedResume.getContactInfo());
    }

    @Override
    public void deleteResume(Integer id) {
        if (resumeDao.findResumeById(id) == null) {
            throw new ResumeNotFoundException("Resume with id: " + id + " not found");
        }

        if (educationInfoService.getResumesEducationInfo(id) != null) {
            for (EducationDto edu : educationInfoService.getResumesEducationInfo(id)) {
                educationInfoService.deleteEducationInfo(id);
            }
        }
        if (workExperienceService.getResumesWorkExperience(id) != null) {
            for (WorkExperienceDto we : workExperienceService.getResumesWorkExperience(id)) {
                workExperienceService.deleteWorkExperienceInfo(id);
            }
        }
        if (contactInfoService.getResumesContacts(id) != null) {
            for (ContactInfoDto contact : contactInfoService.getResumesContacts(id)) {
                contactInfoService.deleteContactInfo(id);
            }
        }
        resumeDao.deleteResume(id);
        log.info("Server Successfully deleted Resume(ID: {})", id);

    }

    @Override
    public Optional<List<ResumeDto>> getAllActiveResumes() {
        return convertIntoDto(resumeDao.getAllActiveResumes());
    }

    @Override
    public Optional<ResumeDto> findResumeById(Integer id) {
        return convertIntoDto(resumeDao.findResumeById(id));
    }

    @Override
    public Optional<List<ResumeDto>> findResumeByCategory(Integer category_id) {
        return convertIntoDto(resumeDao.findResumeByCategory(category_id));
    }

    @Override
    public Optional<List<ResumeDto>> findResumeByCategory(String category1) {
        return convertIntoDto(resumeDao.findResumeByCategory(category1));
    }

    @Override
    public Optional<List<ResumeDto>> findResumeByCreator(Integer applicant_id) {
        if (resumeDao.findResumeByCreator(applicant_id).isEmpty()) {
            log.info("empty list");
            return Optional.empty();
        }
        return convertIntoDto(resumeDao.findResumeByCreator(applicant_id));
    }

    @Override
    public Optional<List<ResumeDto>> findResumeByCreator(String creatorName) {
        if (resumeDao.findResumeByCreator(creatorName).isEmpty()) {
            log.info("empty list");
            return Optional.empty();
        }
        return convertIntoDto(resumeDao.findResumeByCreator(creatorName));
    }

    @Override
    public String findResumeNameById(Integer resumeId){
        if (resumeDao.findResumeNameById(resumeId).isEmpty()) {
            log.info("empty");
            return null;
        }
        return resumeDao.findResumeNameById(resumeId);
    }

    @Override
    public Integer findResumeIdByName(String name){
        return resumeDao.findResumeIdByName(name);
    }


    private Optional<List<ResumeDto>> convertIntoDto(List<Resume>resumes){
        if (resumes == null || resumes.isEmpty()) {
            throw new ResumeNotFoundException("Resumes not Found");
        }
        List<ResumeDto>resumeDtos = new ArrayList<>();
        for (Resume resume : resumes) {
            List<EducationDto>educationInfo = educationInfoService.getResumesEducationInfo(resume.getId());
            List<WorkExperienceDto>workExperienceInfo = workExperienceService.getResumesWorkExperience(resume.getId());
            List<ContactInfoDto>contacts = contactInfoService.getResumesContacts(resume.getId());
            log.info("Mapping (ID: {})Resume into Resume DTO, stored in list", resume.getId());

            resumeDtos.add(new ResumeDto(
                    userAccountService.findNameById(resume.getApplicantId()),
                    resume.getName(),
                    categoryService.findNameById(resume.getCategoryId()),
                    resume.getSalary(),
                    resume.getIsActive(),
                    educationInfo,
                    workExperienceInfo,
                    contacts
            ));
        }
        return Optional.of(resumeDtos);
    }

    private Optional<ResumeDto> convertIntoDto(Resume resume){
        if (resume == null) {
            throw new ResumeNotFoundException("Resume not Found");
        }
        List<EducationDto>educationInfo = educationInfoService.getResumesEducationInfo(resume.getId());
        List<WorkExperienceDto>workExperienceInfo = workExperienceService.getResumesWorkExperience(resume.getId());
        List<ContactInfoDto>contacts = contactInfoService.getResumesContacts(resume.getId());
        log.info("Mapping (ID: {})Resume into Resume DTO", resume.getId());

        return Optional.of(new ResumeDto(
                userAccountService.findNameById(resume.getApplicantId()),
                resume.getName(),
                categoryService.findNameById(resume.getCategoryId()),
                resume.getSalary(),
                resume.getIsActive(),
                educationInfo,
                workExperienceInfo,
                contacts
        ));
    }

    private ResumeDto checkFieldsForNullOrEmpty(Integer id, ResumeDto newResume) {
        Optional<ResumeDto> oldResume = convertIntoDto(resumeDao.findResumeById(id));
        if (oldResume.isEmpty()){
            throw new ResumeNotFoundException("Resume with id: " + id + " not found");
        }
        ResumeDto result = new ResumeDto();

        if (ifNull(newResume.getApplicantEmail()) || newResume.getApplicantEmail().isEmpty()) {
            result.setApplicantEmail(oldResume.get().getApplicantEmail());
        } else {
            result.setApplicantEmail(newResume.getApplicantEmail());
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
        if (newResume.getIsActive() == oldResume.get().getIsActive() || ifNull(newResume.getIsActive())) {
            result.setIsActive(oldResume.get().getIsActive());
        } else {
            result.setIsActive(newResume.getIsActive());
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
        if (ifNull(newResume.getContactInfo()) || newResume.getContactInfo().isEmpty()){
            result.setContactInfo(oldResume.get().getContactInfo());
        } else {
            result.setContactInfo(newResume.getContactInfo());
        }
        return result;
    }

    private boolean ifNull(Object object){
        return object == null;
    }

    private Resume convertIntoModel(ResumeDto resumeDto) {
        Integer categoryId = categoryService.findIdByName(resumeDto.getCategory())
                .orElseThrow(() -> new CategoryNotFoundException("Category not found: " + resumeDto.getCategory()));

        Integer applicantId = userAccountService.findIdByEmail(resumeDto.getApplicantEmail())
                .orElseThrow(() -> new UserNotFoundException("User not found with email: " + resumeDto.getApplicantEmail()));

        log.info("Mapping Resume DTO into Resume for User ID: {}, Email: {}", applicantId, resumeDto.getApplicantEmail());

        return new Resume(
                0,
                applicantId,
                resumeDto.getName(),
                categoryId,
                resumeDto.getSalary(),
                resumeDto.getIsActive(),
                LocalDateTime.now(),
                LocalDateTime.now()
        );
    }
}