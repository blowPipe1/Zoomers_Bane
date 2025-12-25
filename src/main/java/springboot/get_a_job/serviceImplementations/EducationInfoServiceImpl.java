package springboot.get_a_job.serviceImplementations;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import springboot.get_a_job.dao.*;
import springboot.get_a_job.dto.EducationDto;
import springboot.get_a_job.dto.ResumeDto;
import springboot.get_a_job.exceptions.EducationInfoNotFoundException;
import springboot.get_a_job.exceptions.ResumeNotFoundException;
import springboot.get_a_job.services.EducationInfoService;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class EducationInfoServiceImpl implements EducationInfoService {
    private final ResumeDao resumeDao;
    private final EducationInfoDao educationDao;

    @Override
    public void addEducationInfo(Integer resumeId, List<EducationDto> educationDtos) {
        if (resumeDao.findResumeById(resumeId) == null) {
            throw new ResumeNotFoundException("Resume with id: " + resumeId + " not found");
        }

        if (educationDtos != null  || !educationDtos.isEmpty()) {
            for (EducationDto edu : educationDtos){
                educationDao.addEducationInfo(edu, resumeId);
                log.info("New Education Info for Resume(ID: {}) added", resumeId);
            }
        }

    }

    @Override
    public void updateResumesEducationInfo(List<EducationDto> educationDto) {
        if (educationDto == null || educationDto.isEmpty()) {
            throw new EducationInfoNotFoundException("Education info list id null or empty");
        }
        for (EducationDto edu : educationDto){
            educationDao.updateEducationInfo(checkedEducation(edu), edu.getId());
            log.info("Updated Education Info(ID: {})", edu.getId());
        }
    }

    private EducationDto checkedEducation(EducationDto newEducation) {
        if (educationDao.findInfoById(newEducation.getId()) == null) {
            throw new EducationInfoNotFoundException("Education info with id: " + newEducation.getId() + " not found");
        }
        EducationDto oldEducation = educationDao.findInfoById(newEducation.getId());
        EducationDto result = new EducationDto();

        if (newEducation.getInstitution() == null || newEducation.getInstitution().isEmpty()) {
            result.setInstitution(oldEducation.getInstitution());
        } else {
            result.setInstitution(newEducation.getInstitution());
        }
        if (newEducation.getProgram() == null || newEducation.getProgram().isEmpty()) {
            result.setProgram(oldEducation.getProgram());
        } else {
            result.setProgram(newEducation.getProgram());
        }
        if (newEducation.getStartDate() == null){
            result.setStartDate(oldEducation.getStartDate());
        } else {
            result.setStartDate(newEducation.getStartDate());
        }
        if (newEducation.getEndDate() == null){
            result.setEndDate(oldEducation.getEndDate());
        } else {
            result.setEndDate(newEducation.getEndDate());
        }
        if (newEducation.getDegree() == null || newEducation.getDegree().isEmpty()) {
            result.setDegree(oldEducation.getDegree());
        } else {
            result.setDegree(newEducation.getDegree());
        }
        return result;
    }

}
