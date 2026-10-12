package exception;
public class AppointmentConflictException extends RuntimeException {
    public AppointmentConflictException(String m) { super(m); }
}