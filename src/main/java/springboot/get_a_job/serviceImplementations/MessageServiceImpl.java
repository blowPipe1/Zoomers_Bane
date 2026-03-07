package springboot.get_a_job.serviceImplementations;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import springboot.get_a_job.dto.MessageDto;
import springboot.get_a_job.models.Message;
import springboot.get_a_job.repositories.MessageRepository;
import springboot.get_a_job.services.MessageService;
import springboot.get_a_job.services.RespondedApplicantService;
import springboot.get_a_job.services.UserAccountService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {
    private final MessageRepository messageRepository;
    @Autowired
    @Lazy
    private RespondedApplicantService respondedApplicantService;
    @Autowired
    @Lazy
    private UserAccountService userAccountService;

    @Override
    @Transactional
    public void save(MessageDto messageDto) {
        Message message = convertToDto(messageDto);
        messageRepository.save(message);
    }

    @Override
    public List<Message> findByApplication_IdOrderByTimestampAsc(Integer applicationId){
        return messageRepository.findByApplication_IdOrderByTimestampAsc(applicationId);
    }

    @Override
    public List<MessageDto> getApplicationsMessageHistory(Integer applicationId){
        return messageRepository.findByApplication_IdOrderByTimestampAsc(applicationId)
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    private MessageDto convertToDto(Message message) {
        return new MessageDto(
                message.getId(),
                message.getContent(),
                message.getAuthor().getName(),
                message.getAuthor().getSurname(),
                message.getAuthor().getAvatar(),
                message.getAuthor().getEmail(),
                message.getFormattedTimestamp()
        );
    }

    private Message convertToDto(MessageDto messageDto) {
        Message message = new Message();
        message.setApplication(respondedApplicantService.getById(messageDto.getApplicationId()));
        message.setAuthor(userAccountService.findByEmail(messageDto.getAuthorEmail()).orElse(null));
        message.setContent(messageDto.getContent());
        message.setTimestamp(LocalDateTime.now());
        return message;
    }
}
