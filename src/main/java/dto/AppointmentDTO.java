package dto;

import model.enums.AppointmentStatus;
import model.enums.AppointmentType;

import java.time.LocalDateTime;

public class AppointmentDTO {
    private Long id;
    private Long patientId;
    private String patientNom;
    private Long doctorId;
    private String doctorNom;
    private String specialtyNom;
    private LocalDateTime dateRdv;
    private Integer dureeMinutes;
    private AppointmentType type;
    private AppointmentStatus statut;
    private String motif;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getPatientId() { return patientId; }
    public void setPatientId(Long p) { this.patientId = p; }
    public String getPatientNom() { return patientNom; }
    public void setPatientNom(String p) { this.patientNom = p; }
    public Long getDoctorId() { return doctorId; }
    public void setDoctorId(Long d) { this.doctorId = d; }
    public String getDoctorNom() { return doctorNom; }
    public void setDoctorNom(String d) { this.doctorNom = d; }
    public String getSpecialtyNom() { return specialtyNom; }
    public void setSpecialtyNom(String s) { this.specialtyNom = s; }
    public LocalDateTime getDateRdv() { return dateRdv; }
    public void setDateRdv(LocalDateTime d) { this.dateRdv = d; }
    public Integer getDureeMinutes() { return dureeMinutes; }
    public void setDureeMinutes(Integer d) { this.dureeMinutes = d; }
    public AppointmentType getType() { return type; }
    public void setType(AppointmentType t) { this.type = t; }
    public AppointmentStatus getStatut() { return statut; }
    public void setStatut(AppointmentStatus s) { this.statut = s; }
    public String getMotif() { return motif; }
    public void setMotif(String m) { this.motif = m; }
}