package springboot.get_a_job.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;


import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = ResourceNotFoundException.class)
    public ModelAndView handleResourceNotFoundException(ResourceNotFoundException ex) {
        ModelAndView modelAndView = new ModelAndView("error");

        modelAndView.addObject("status", HttpStatus.NOT_FOUND.value());
        modelAndView.addObject("error", "Resource not found");
        modelAndView.addObject("message", ex.getMessage());

        modelAndView.setStatus(HttpStatus.NOT_FOUND);

        return modelAndView;
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ModelAndView handleValidationException(MethodArgumentNotValidException ex) {

        List<String> errors = ex.getBindingResult().getFieldErrors().stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.toList());

        ModelAndView modelAndView = new ModelAndView("error");

        modelAndView.addObject("status", HttpStatus.BAD_REQUEST.value());
        modelAndView.addObject("error", "Validation Error");

        String errorMessage = "Following Errors occurred: " + String.join("; ", errors);
        modelAndView.addObject("message", errorMessage);

        modelAndView.setStatus(HttpStatus.BAD_REQUEST);

        return modelAndView;
    }

    @ExceptionHandler(value = InvalidAccountTypeException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public @ResponseBody ErrorResponse handleException(InvalidAccountTypeException ex) {
        return new ErrorResponse(LocalDateTime.now(), HttpStatus.UNPROCESSABLE_CONTENT.value(), ex.getMessage());
    }

}