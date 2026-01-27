package springboot.get_a_job.serviceImplementations;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import springboot.get_a_job.models.Message;
import springboot.get_a_job.repositories.MessageRepository;
import springboot.get_a_job.services.MessageService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {
    private final MessageRepository messageRepository;

    @Override
    public List<Message> findByApplication_IdOrderByTimestampAsc(Integer applicationId){
        return messageRepository.findByApplication_IdOrderByTimestampAsc(applicationId);
    }
}
