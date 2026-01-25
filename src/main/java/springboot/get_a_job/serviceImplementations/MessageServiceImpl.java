package springboot.get_a_job.serviceImplementations;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import springboot.get_a_job.repositories.MessageRepository;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl {
    private final MessageRepository messageRepository;

}
