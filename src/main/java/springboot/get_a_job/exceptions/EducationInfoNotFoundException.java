package springboot.get_a_job.exceptions;

public class EducationInfoNotFoundException extends ResourceNotFoundException {
    public EducationInfoNotFoundException(String message) {
        super(message);
    }
}
