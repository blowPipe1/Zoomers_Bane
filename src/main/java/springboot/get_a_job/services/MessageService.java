package springboot.get_a_job.services;

import springboot.get_a_job.dto.MessageDto;
import springboot.get_a_job.models.Message;

import java.util.List;

public interface MessageService {
    List<Message> findByApplication_IdOrderByTimestampAsc(Integer applicationId);
    List<MessageDto> getApplicationsMessageHistory(Integer applicationId);
    void save(MessageDto messageDto);
}
