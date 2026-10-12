package exception;
public class AppointmentNotFoundException extends RuntimeException {
    public AppointmentNotFoundException(String m) { super(m); }
}