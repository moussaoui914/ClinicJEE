package model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "medical_notes")
public class MedicalNote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // * MedicalNote → 1 Doctor
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "doctor_id", nullable = false)
    private Doctor doctor;

    // 1 MedicalNote → 1 Appointment
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "appointment_id", nullable = false, unique = true)
    private Appointment appointment;

    @Column(columnDefinition = "TEXT")
    private String diagnostic;

    @Column(columnDefinition = "TEXT")
    private String contenu;

    @Column(name = "date_note", nullable = false)
    private LocalDateTime dateNote = LocalDateTime.now();

    @Column(nullable = false)
    private Boolean validee = false;

    // ===== Constructeurs =====

    public MedicalNote() {}

    public MedicalNote(Doctor doctor, Appointment appointment,
                       String diagnostic, String contenu) {
        this.doctor = doctor;
        this.appointment = appointment;
        this.diagnostic = diagnostic;
        this.contenu = contenu;
        this.dateNote = LocalDateTime.now();
        this.validee = false;
    }

    // ===== Helpers métier =====

    public boolean isLocked() {
        return Boolean.TRUE.equals(validee);
    }

    public void validate() {
        this.validee = true;
    }

    // ===== Getters / Setters =====

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Doctor getDoctor() { return doctor; }
    public void setDoctor(Doctor doctor) { this.doctor = doctor; }

    public Appointment getAppointment() { return appointment; }
    public void setAppointment(Appointment appointment) { this.appointment = appointment; }

    public String getDiagnostic() { return diagnostic; }
    public void setDiagnostic(String diagnostic) { this.diagnostic = diagnostic; }

    public String getContenu() { return contenu; }
    public void setContenu(String contenu) { this.contenu = contenu; }

    public LocalDateTime getDateNote() { return dateNote; }
    public void setDateNote(LocalDateTime dateNote) { this.dateNote = dateNote; }

    public Boolean getValidee() { return validee; }
    public void setValidee(Boolean validee) { this.validee = validee; }

    // ===== equals / hashCode =====

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MedicalNote)) return false;
        MedicalNote that = (MedicalNote) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() { return Objects.hash(id); }

    @Override
    public String toString() {
        return "MedicalNote{id=" + id + ", validee=" + validee + "}";
    }
}