package exception;
public class DoctorNotFoundException extends RuntimeException {
    public DoctorNotFoundException(String m) { super(m); }
}