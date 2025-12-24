package springboot.get_a_job.exceptions;

public class WorkExpNotFoundException extends ResourceNotFoundException {
    public WorkExpNotFoundException(String message) {
        super(message);
    }
}
