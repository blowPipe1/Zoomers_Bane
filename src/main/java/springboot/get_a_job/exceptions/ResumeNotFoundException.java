package springboot.get_a_job.exceptions;

public class ResumeNotFoundException extends ResourceNotFoundException {
  public ResumeNotFoundException(String message) {
    super(message);
  }
}
