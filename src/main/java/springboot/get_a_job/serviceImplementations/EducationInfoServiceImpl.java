package springboot.get_a_job.serviceImplementations;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import springboot.get_a_job.dao.*;
import springboot.get_a_job.dto.EducationDto;
import springboot.get_a_job.exceptions.EducationInfoNotFoundException;
import springboot.get_a_job.exceptions.ResumeNotFoundException;
import springboot.get_a_job.services.EducationInfoService;

import java.util.List;

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
        for (EducationDto edu : educationDtos){
            educationDao.addEducationInfo(edu, resumeId);
            log.info("Added education with id: {} for Resume(ID: {})", edu.getId(), resumeId);
        }

    }

    @Override
    public void updateResumesEducationInfo(Integer educationId, EducationDto educationDto) {
        if (educationDao.findInfoById(educationId) == null) {
            throw new EducationInfoNotFoundException("Education info with id: " + educationId + " not found");
        }
        educationDao.updateEducationInfo(educationDto, educationId);
        log.info("Updated Education Info(ID: {})", educationId);
    }
}
