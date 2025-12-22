package springboot.get_a_job.exceptions;

public class VacancyNotFoundException extends ResourceNotFoundException {
    public VacancyNotFoundException(String message) {
        super(message);
    }
}
