package springboot.get_a_job.services;

import springboot.get_a_job.dto.ContactInfoDto;

import java.util.List;

public interface ContactInfoService {
    void addContactInfo(Integer resumeId, List<ContactInfoDto> contacts);
    void updateContactInfo(Integer contactId, ContactInfoDto contactInfo);
}
