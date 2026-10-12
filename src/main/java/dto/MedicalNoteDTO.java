package dto;

import java.time.LocalDateTime;

public class MedicalNoteDTO {
    private Long id;
    private Long appointmentId;
    private Long doctorId;
    private String doctorNom;
    private String diagnostic;
    private String contenu;
    private LocalDateTime dateNote;
    private Boolean validee;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getAppointmentId() { return appointmentId; }
    public void setAppointmentId(Long a) { this.appointmentId = a; }
    public Long getDoctorId() { return doctorId; }
    public void setDoctorId(Long d) { this.doctorId = d; }
    public String getDoctorNom() { return doctorNom; }
    public void setDoctorNom(String d) { this.doctorNom = d; }
    public String getDiagnostic() { return diagnostic; }
    public void setDiagnostic(String d) { this.diagnostic = d; }
    public String getContenu() { return contenu; }
    public void setContenu(String c) { this.contenu = c; }
    public LocalDateTime getDateNote() { return dateNote; }
    public void setDateNote(LocalDateTime d) { this.dateNote = d; }
    public Boolean getValidee() { return validee; }
    public void setValidee(Boolean v) { this.validee = v; }
}