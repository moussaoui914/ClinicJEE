package model;

import jakarta.persistence.*;
import model.enums.AppointmentStatus;
import model.enums.AppointmentType;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "appointments")
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // * Appointment → 1 Patient
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

    // * Appointment → 1 Doctor
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "doctor_id", nullable = false)
    private Doctor doctor;

    @Column(name = "date_rdv", nullable = false)
    private LocalDateTime dateRdv;

    @Column(name = "duree_minutes", nullable = false)
    private Integer dureeMinutes = 30;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private AppointmentType type;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private AppointmentStatus statut = AppointmentStatus.PLANNED;

    @Column(columnDefinition = "TEXT")
    private String motif;

    // 1 Appointment → 0..1 MedicalNote
    @OneToOne(mappedBy = "appointment", cascade = CascadeType.ALL, orphanRemoval = true)
    private MedicalNote medicalNote;

    // ===== Constructeurs =====

    public Appointment() {}

    public Appointment(Patient patient, Doctor doctor, LocalDateTime dateRdv,
                       AppointmentType type, String motif) {
        this.patient = patient;
        this.doctor = doctor;
        this.dateRdv = dateRdv;
        this.type = type;
        this.motif = motif;
        this.statut = AppointmentStatus.PLANNED;
        this.dureeMinutes = 30;
    }

    // ===== Helpers métier =====

    public LocalDateTime getDateFin() {
        return dateRdv.plusMinutes(dureeMinutes);
    }

    public boolean isCancelableByPatient() {
        if (statut != AppointmentStatus.PLANNED) return false;
        return LocalDateTime.now().plusHours(12).isBefore(dateRdv);
    }

    public boolean isDone() {
        return statut == AppointmentStatus.DONE;
    }

    public boolean isPlanned() {
        return statut == AppointmentStatus.PLANNED;
    }

    public boolean isCanceled() {
        return statut == AppointmentStatus.CANCELED;
    }

    // ===== Getters / Setters =====

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Patient getPatient() { return patient; }
    public void setPatient(Patient patient) { this.patient = patient; }

    public Doctor getDoctor() { return doctor; }
    public void setDoctor(Doctor doctor) { this.doctor = doctor; }

    public LocalDateTime getDateRdv() { return dateRdv; }
    public void setDateRdv(LocalDateTime dateRdv) { this.dateRdv = dateRdv; }

    public Integer getDureeMinutes() { return dureeMinutes; }
    public void setDureeMinutes(Integer dureeMinutes) { this.dureeMinutes = dureeMinutes; }

    public AppointmentType getType() { return type; }
    public void setType(AppointmentType type) { this.type = type; }

    public AppointmentStatus getStatut() { return statut; }
    public void setStatut(AppointmentStatus statut) { this.statut = statut; }

    public String getMotif() { return motif; }
    public void setMotif(String motif) { this.motif = motif; }

    public MedicalNote getMedicalNote() { return medicalNote; }
    public void setMedicalNote(MedicalNote medicalNote) { this.medicalNote = medicalNote; }

    // ===== equals / hashCode =====

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Appointment)) return false;
        Appointment that = (Appointment) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() { return Objects.hash(id); }

    @Override
    public String toString() {
        return "Appointment{id=" + id + ", date=" + dateRdv
                + ", statut=" + statut + "}";
    }
}