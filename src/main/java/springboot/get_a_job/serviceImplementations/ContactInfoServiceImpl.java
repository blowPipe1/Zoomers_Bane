package springboot.get_a_job.serviceImplementations;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import springboot.get_a_job.dao.*;
import springboot.get_a_job.dto.ContactInfoDto;
import springboot.get_a_job.exceptions.ResourceNotFoundException;
import springboot.get_a_job.exceptions.ResumeNotFoundException;
import springboot.get_a_job.models.ContactInfo;
import springboot.get_a_job.services.ContactInfoService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ContactInfoServiceImpl implements ContactInfoService {
    private final ResumeDao resumeDao;
    private final CategoryDao categoryDao;
    private final EducationInfoDao educationDao;
    private final WorkExperienceDao workExperienceDao;
    private final UserDao userDao;
    private final ContactInfoDao contactInfoDao;

    @Override
    public void addContactInfo(Integer resumeId, List<ContactInfoDto> contacts){
        if (resumeDao.findResumeById(resumeId) == null) {
            throw new ResumeNotFoundException("Resume with id: " + resumeId + " not found");
        }
        for (ContactInfoDto contact : contacts){
            contactInfoDao.addContactInfo(new ContactInfo(
                    0, contactInfoDao.findIdByName(contact.getType()), resumeId, contact.getValue()
            ), resumeId);
        }
    }

    @Override
    public void updateContactInfo(Integer contactId, ContactInfoDto contactInfo){
        if (contactInfoDao.findInfoByID(contactId) == null) {
            throw new ResourceNotFoundException("Contact info with id: " + contactId + " not found");
        }
        if (contactInfoDao.findIdByName(contactInfo.getType()) == null ) {
            throw new ResourceNotFoundException("Contact info with type: " + contactInfo.getType() + " not found");
        }
        contactInfoDao.updateContactInfo(new ContactInfo(
                0,
                contactInfoDao.findIdByName(contactInfo.getType()),
                contactInfoDao.findInfoByID(contactId).getResumeId(),
                contactInfo.getValue()
        ), contactId);
    }
}
