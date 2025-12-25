package springboot.get_a_job.services;

import springboot.get_a_job.dto.EducationDto;

import java.util.List;

public interface EducationInfoService {
    void addEducationInfo(Integer resumeId, List<EducationDto> educationDtos);
    void updateResumesEducationInfo(List<EducationDto> educationDto);
}
