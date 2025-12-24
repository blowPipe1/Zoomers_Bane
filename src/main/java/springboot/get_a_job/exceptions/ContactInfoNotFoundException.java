package springboot.get_a_job.exceptions;

public class ContactInfoNotFoundException extends ResourceNotFoundException {
    public ContactInfoNotFoundException(String message) {
        super(message);
    }
}
