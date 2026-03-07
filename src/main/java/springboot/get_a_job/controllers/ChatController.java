package springboot.get_a_job.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.server.ResponseStatusException;
import springboot.get_a_job.dto.MessageDto;
import springboot.get_a_job.dto.RespondedApplicantDto;
import springboot.get_a_job.dto.ResumeDto;
import springboot.get_a_job.dto.VacancyDto;
import springboot.get_a_job.models.CustomUserDetails;
import springboot.get_a_job.models.User;
import springboot.get_a_job.services.*;

import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class ChatController {
    private final SimpMessagingTemplate messagingTemplate;
    private final MessageService messageService;
    private final RespondedApplicantService applicantService;
    private final UserAccountService userAccountService;
    private final VacancyService vacancyService;
    private final ResumeService resumeService;

    @MessageMapping("/chat/{applicationId}")
    public void sendMessage(@DestinationVariable Integer applicationId,
                            MessageDto dto,
                            Principal principal) {

        User author = userAccountService.findByEmail(principal.getName()).orElse(null);

        dto.setAuthorEmail(principal.getName());
        messageService.save(dto);

        MessageDto response = new MessageDto();
        response.setApplicationId(applicationId);
        response.setContent(dto.getContent());
        response.setTimestamp(dto.getTimestamp());

        if (author != null) {
            response.setAuthorName(author.getName());
            response.setAuthorSurname(author.getSurname());
            response.setAuthorAvatar(author.getAvatar());
        }

        messagingTemplate.convertAndSend("/topic/messages/" + applicationId, response);
    }

    @GetMapping("/chat/{applicationId}")
    public String openChat(@PathVariable Integer applicationId,
                           Model model,
                           @AuthenticationPrincipal CustomUserDetails currentUser) {

        RespondedApplicantDto app = applicantService.getDtoById(applicationId);

        VacancyDto vacancy = vacancyService.findVacancyById(app.getVacancyId()).orElse(null);
        ResumeDto resume = resumeService.findResumeById(app.getResumeId()).orElse(null);

        String user = currentUser.getUsername();
        if (!user.equalsIgnoreCase(vacancy.getAuthor()) && !user.equalsIgnoreCase(resume.getApplicantEmail())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        List<MessageDto> history = messageService.getApplicationsMessageHistory(applicationId);

        model.addAttribute("app", app);
        model.addAttribute("history", history);
        model.addAttribute("currentUserId", currentUser.getId());
        model.addAttribute("vacancy", vacancy);
        model.addAttribute("resume", resume);

        return "chat";
    }
}
