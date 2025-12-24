package springboot.get_a_job.serviceImplementations;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import springboot.get_a_job.dao.*;
import springboot.get_a_job.dto.ContactInfoDto;
import springboot.get_a_job.exceptions.ContactInfoNotFoundException;
import springboot.get_a_job.exceptions.ResumeNotFoundException;
import springboot.get_a_job.models.ContactInfo;
import springboot.get_a_job.services.ContactInfoService;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ContactInfoServiceImpl implements ContactInfoService {
    private final ResumeDao resumeDao;
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
            log.info("Added contact info({} {}) for Resume(ID: {})", contact.getType(), contact.getValue(), resumeId);
        }
    }

    @Override
    public void updateContactInfo(Integer contactId, ContactInfoDto contactInfo){
        if (contactInfoDao.findInfoByID(contactId) == null) {
            throw new ContactInfoNotFoundException("Contact info with id: " + contactId + " not found");
        }
        if (contactInfoDao.findIdByName(contactInfo.getType()) == null ) {
            throw new ContactInfoNotFoundException("Contact info with type: " + contactInfo.getType() + " not found");
        }
        contactInfoDao.updateContactInfo(new ContactInfo(
                0,
                contactInfoDao.findIdByName(contactInfo.getType()),
                contactInfoDao.findInfoByID(contactId).getResumeId(),
                contactInfo.getValue()
        ), contactId);
        log.info("Updated contact info(ID: {})",  contactId);
    }
}
