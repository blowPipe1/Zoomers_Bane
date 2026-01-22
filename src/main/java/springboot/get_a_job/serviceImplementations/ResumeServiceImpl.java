
package springboot.get_a_job.serviceImplementations;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import springboot.get_a_job.dto.ResumeDto;
import springboot.get_a_job.exceptions.CategoryNotFoundException;
import springboot.get_a_job.exceptions.ResumeNotFoundException;
import springboot.get_a_job.exceptions.UserNotFoundException;
import springboot.get_a_job.models.Category;
import springboot.get_a_job.models.Resume;
import springboot.get_a_job.models.User;
import springboot.get_a_job.repositories.ResumeRepository;
import springboot.get_a_job.services.*;


import java.time.LocalDateTime;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ResumeServiceImpl implements ResumeService {
    private final ResumeRepository resumeRepository;
    @Autowired
    @Lazy
    private CategoryService categoryService;
    @Autowired
    @Lazy
    private UserAccountService userAccountService;
    @Autowired
    @Lazy
    private EducationInfoService educationInfoService;
    @Autowired
    @Lazy
    private WorkExperienceService workExperienceService;
    @Autowired
    @Lazy
    private ContactInfoService contactInfoService;

    @Override
    @Transactional
    public void createResume(ResumeDto resumeDto) {
        if (resumeDto == null) {
            throw new ResumeNotFoundException("Resume data is null");
        }

        Resume resume = convertIntoModel(resumeDto);

        Resume savedResume = resumeRepository.save(resume);
        Integer resumeId = savedResume.getId();
        log.info("Server Successfully created Resume with ID {}", resumeId);

        if (resumeDto.getEducation() != null) {
            educationInfoService.addEducationInfo(resumeId, resumeDto.getEducation());
        }
        if (resumeDto.getWorkExperience() != null) {
            workExperienceService.addWorkExperienceInfo(resumeId, resumeDto.getWorkExperience());
        }
        if (resumeDto.getContactInfo() != null) {
            contactInfoService.addContactInfo(resumeId, resumeDto.getContactInfo());
        }
    }

    @Override
    @Transactional
    public void updateResume(Integer id, ResumeDto resumeDto) {
        Resume resume = resumeRepository.findById(id)
                .orElseThrow(() -> new ResumeNotFoundException("Resume with id: " + id + " not found"));

        resume.setName(resumeDto.getName());
        resume.setSalary(resumeDto.getSalary());
        resume.setIsActive(resumeDto.getIsActive());
        resume.setUpdateTime(LocalDateTime.now());

        if (resumeDto.getCategory() != null) {
            Category category = categoryService.findByNameIgnoreCase(resumeDto.getCategory())
                    .orElseThrow(() -> new CategoryNotFoundException("Category not found"));
            resume.setCategory(category);
        }

        educationInfoService.updateOrAddEducationInfo(id, resumeDto.getEducation());
        workExperienceService.updateOrAddWorkExp(id, resumeDto.getWorkExperience());
        contactInfoService.updateOrAddContactInfo(id, resumeDto.getContactInfo());

        log.info("Server Successfully updated Resume(ID: {})", id);
    }

    @Override
    @Transactional
    public void deleteResume(Integer id) {
        Resume resume = resumeRepository.findById(id)
                .orElseThrow(() -> new ResumeNotFoundException("Resume with id: " + id + " not found"));

        educationInfoService.deleteEducationInfo(id);
        workExperienceService.deleteWorkExperienceInfo(id);
        contactInfoService.deleteContactInfo(id);

        resumeRepository.delete(resume);
        log.info("Server Successfully deleted Resume(ID: {})", id);
    }

    @Override
    public Optional<List<ResumeDto>> getAllActiveResumes() {
        List<Resume> resumes = resumeRepository.findAllByIsActiveTrue();
        return resumes.isEmpty() ? Optional.empty() : Optional.of(convertList(resumes));
    }

    @Override
    public Optional<ResumeDto> findResumeById(Integer id) {
        return resumeRepository.findById(id).map(this::convertIntoDto);
    }

    @Override
    public Optional<List<ResumeDto>> findResumeByCreator(Integer applicantId) {
        List<Resume> resumes = resumeRepository.findAllByApplicantId(applicantId);
        return resumes.isEmpty() ? Optional.empty() : Optional.of(convertList(resumes));
    }

    @Override
    public Optional<Resume>findById(Integer id){
        return resumeRepository.findById(id);
    }

    @Override
    public List<ResumeDto> findAllByApplicantId(Integer applicantId){
        return resumeRepository.findAllByApplicantId(applicantId)
                .stream()
                .map(this::convertIntoDto).collect(Collectors.toList());
    }

    private List<ResumeDto> convertList(List<Resume> resumes) {
        return resumes.stream().map(this::convertIntoDto).collect(Collectors.toList());
    }

    private ResumeDto convertIntoDto(Resume resume) {
        return new ResumeDto(
                resume.getId(),
                resume.getApplicant() != null ? resume.getApplicant().getEmail() : null,
                resume.getName(),
                resume.getCategory() != null ? resume.getCategory().getName() : null,
                resume.getSalary(),
                resume.getIsActive(),
                educationInfoService.getResumesEducationInfo(resume.getId()),
                workExperienceService.getResumesWorkExperience(resume.getId()),
                contactInfoService.getResumesContacts(resume.getId())
        );
    }

    private Resume convertIntoModel(ResumeDto dto) {
        Category category = categoryService.findByNameIgnoreCase(dto.getCategory())
                .orElseThrow(() -> new CategoryNotFoundException("Category not found: " + dto.getCategory()));

        User applicant = userAccountService.findByEmail(dto.getApplicantEmail())
                .orElseThrow(() -> new UserNotFoundException("User not found: " + dto.getApplicantEmail()));

        Resume resume = new Resume();
        resume.setApplicant(applicant);
        resume.setName(dto.getName());
        resume.setCategory(category);
        resume.setSalary(dto.getSalary());
        resume.setIsActive(dto.getIsActive());
        resume.setCreatedDate(LocalDateTime.now());
        resume.setUpdateTime(LocalDateTime.now());
        return resume;
    }
}