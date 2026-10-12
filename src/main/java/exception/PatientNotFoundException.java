package exception;
public class PatientNotFoundException extends RuntimeException {
    public PatientNotFoundException(String m) { super(m); }
}