package springboot.get_a_job.exceptions;


import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

public record ErrorResponse(
        LocalDateTime timestamp,
        int status,
        String error,
        String message,
        String path,
        List<String> details
) {
    public ErrorResponse(int status, String error, String message, String path) {
        this(LocalDateTime.now(), status, error, message, path, Collections.emptyList());
    }
}
