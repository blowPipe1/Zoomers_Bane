
package springboot.get_a_job.serviceImplementations;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import springboot.get_a_job.dao.*;
import springboot.get_a_job.dto.ContactInfoDto;
import springboot.get_a_job.dto.EducationDto;
import springboot.get_a_job.dto.ResumeDto;
import springboot.get_a_job.dto.WorkExperienceDto;
import springboot.get_a_job.exceptions.CategoryNotFoundException;
import springboot.get_a_job.exceptions.ResumeNotFoundException;
import springboot.get_a_job.models.ContactInfo;
import springboot.get_a_job.models.Resume;
import springboot.get_a_job.services.ResumeService;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ResumeServiceImpl implements ResumeService {
    private final ResumeDao resumeDao;
    private final CategoryDao categoryDao;
    private final EducationInfoDao educationDao;
    private final WorkExperienceDao workExperienceDao;
    private final UserDao userDao;
    private final ContactInfoDao contactInfoDao;
    private final EducationInfoServiceImpl educationInfoService;
    private final WorkExperienceServiceImpl workExperienceService;
    private final ContactInfoServiceImpl contactInfoService;

    @Override
    public void createResume(ResumeDto resumeDto) {
        if (resumeDto == null) {
            throw new ResumeNotFoundException("Category not found");
        }

        Integer resumeId = resumeDao.saveResume(convertIntoModel(resumeDto));
        log.info("Resume with ID {} created", resumeId);

        if (resumeDto.getEducation() != null  || !resumeDto.getEducation().isEmpty()) {
            educationInfoService.addEducationInfo(resumeId, resumeDto.getEducation());
        }

        if (resumeDto.getWorkExperience() != null || !resumeDto.getWorkExperience().isEmpty()) {
            workExperienceService.addWorkExperienceInfo(resumeId, resumeDto.getWorkExperience());
        }

        if (resumeDto.getContactInfo() != null || !resumeDto.getContactInfo().isEmpty()) {
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
        log.info("Updating resume({})", resumeId);

        if (checkedResume.getEducation() != null) {
            educationInfoService.updateResumesEducationInfo(checkedResume.getEducation());
        } else {
            educationInfoService.addEducationInfo(resumeId, checkedResume.getEducation());
        }

        if (checkedResume.getWorkExperience() != null || !checkedResume.getWorkExperience().isEmpty()) {
           workExperienceService.updateResumesWorkExperienceInfo(checkedResume.getWorkExperience());
        } else {
            workExperienceService.addWorkExperienceInfo(resumeId, checkedResume.getWorkExperience());
        }

        if (checkedResume.getContactInfo() != null || !checkedResume.getContactInfo().isEmpty()) {
            contactInfoService.updateContactInfo(checkedResume.getContactInfo());
        } else {
            contactInfoService.addContactInfo(resumeId, checkedResume.getContactInfo());
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
                log.info("Deleting (ID: {})Resume's Education Info", id);
            }
        }
        if (workExperienceDao.getResumesWorkExperience(id) != null) {
            for (WorkExperienceDto we : workExperienceDao.getResumesWorkExperience(id)) {
                workExperienceDao.deleteWorkExperienceInfo(id);
                log.info("Deleting (ID: {})Resume's Work Experience", id);
            }
        }
        if (contactInfoDao.getResumesContacts(id) != null) {
            for (ContactInfoDto contact : contactInfoDao.getResumesContacts(id)) {
                contactInfoDao.deleteContactInfo(id);
                log.info("Deleting (ID: {})Resume's Contact Info", id);
            }
        }
        resumeDao.deleteResume(id);
        log.info("Deleting Resume(ID: {})", id);

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
        return convertIntoDto(resumeDao.findResumeByCreator(applicant_id));
    }

    @Override
    public Optional<List<ResumeDto>> findResumeByCreator(String creatorName) {
        return convertIntoDto(resumeDao.findResumeByCreator(creatorName));
    }

    private Optional<List<ResumeDto>> convertIntoDto(List<Resume>resumes){
        if (resumes == null || resumes.isEmpty()) {
            throw new ResumeNotFoundException("Resumes not Found");
        }
        List<ResumeDto>resumeDtos = new ArrayList<>();
        for (Resume resume : resumes) {
            List<EducationDto>educationInfo = educationDao.getResumesEducationInfo(resume.getId());
            List<WorkExperienceDto>workExperienceInfo = workExperienceDao.getResumesWorkExperience(resume.getId());
            List<ContactInfoDto>contacts = contactInfoDao.getResumesContacts(resume.getId());
            log.info("Mapping (ID: {})Resume into Resume DTO, stored in list", resume.getId());

            resumeDtos.add(new ResumeDto(
                    userDao.findNameById(resume.getApplicantId()),
                    resume.getName(),
                    categoryDao.findNameById(resume.getCategoryId()),
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
        List<EducationDto>educationInfo = educationDao.getResumesEducationInfo(resume.getId());
        List<WorkExperienceDto>workExperienceInfo = workExperienceDao.getResumesWorkExperience(resume.getId());
        List<ContactInfoDto>contacts = contactInfoDao.getResumesContacts(resume.getId());
        log.info("Mapping (ID: {})Resume into Resume DTO", resume.getId());

        return Optional.of(new ResumeDto(
                userDao.findNameById(resume.getApplicantId()),
                resume.getName(),
                categoryDao.findNameById(resume.getCategoryId()),
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

    private Resume convertIntoModel(ResumeDto resumeDto){
        Integer categoryId = categoryDao.findIdByName(resumeDto.getCategory())
                .orElseThrow(() -> new CategoryNotFoundException("Category not found: " + resumeDto.getCategory()));

        // TODO check for null & empty string etc.
        String[] name = resumeDto.getApplicant().split(" ");
        Integer applicantId = Integer.valueOf(userDao.findIdBySurname(name[1]));
        log.info("Mapping Resume DTO into Resume for User with ID: {} Name: {} {}", applicantId, name[0], name[1]);

        return new Resume(
                0,
                applicantId,
                resumeDto.getName(),
                categoryId,
                resumeDto.getSalary(),
                resumeDto.isActive(),
                LocalDateTime.now(),
                LocalDateTime.now()
        );
    }
}