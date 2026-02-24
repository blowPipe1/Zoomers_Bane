package springboot.get_a_job.exceptions;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@ControllerAdvice
@Slf4j
@RequiredArgsConstructor
public class GlobalExceptionHandler {
    private final MessageSource messageSource;

    @ExceptionHandler(ResourceNotFoundException.class)
    public Object handleNotFound(ResourceNotFoundException ex, HttpServletRequest request, Locale locale) {
        String localizedMessage = messageSource.getMessage(ex.getMessage(), null, ex.getMessage(), locale);
        String localizedTitle = messageSource.getMessage("error.404", null, "Not Found", locale);

        if (isApiRequest(request)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse(404, localizedTitle, localizedMessage, request.getRequestURI()));
        }

        return createErrorView(HttpStatus.NOT_FOUND, localizedTitle, localizedMessage);
    }

    @ExceptionHandler(EmailAlreadyExistsException.class)
    public Object handleDuplicateEmail(EmailAlreadyExistsException ex, HttpServletRequest request, Locale locale) {
        String localizedMessage = messageSource.getMessage(ex.getMessage(), null, locale);
        String localizedTitle = messageSource.getMessage("error.409", null, locale);

        if (isApiRequest(request)) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new ErrorResponse(409, localizedTitle, localizedMessage, request.getRequestURI()));
        }
        return createErrorView(HttpStatus.CONFLICT, localizedTitle, localizedMessage);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Object handleValidation(MethodArgumentNotValidException ex, HttpServletRequest request, Locale locale) {
        List<String> details = ex.getBindingResult().getFieldErrors().stream()
                .map(f -> messageSource.getMessage(f, locale))
                .collect(Collectors.toList());

        String localizedTitle = messageSource.getMessage("error.validation.title", null, "Validation Error", locale);

        if (isApiRequest(request)) {
            return ResponseEntity.badRequest()
                    .body(new ErrorResponse(LocalDateTime.now(), 400, localizedTitle,
                            "Invalid input", request.getRequestURI(), details));
        }

        return createErrorView(HttpStatus.BAD_REQUEST, localizedTitle, String.join(", ", details));
    }

    private boolean isApiRequest(HttpServletRequest request) {
        String accept = request.getHeader("Accept");
        return (accept != null && accept.contains("application/json")) || request.getRequestURI().startsWith("/api/");
    }

    private ModelAndView createErrorView(HttpStatus status, String error, String message) {
        ModelAndView mav = new ModelAndView("error");
        mav.addObject("status", status.value());
        mav.addObject("error", error);
        mav.addObject("message", message);
        mav.setStatus(status);
        return mav;
    }
}
