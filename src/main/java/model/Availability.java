package model;

import jakarta.persistence.*;
import model.enums.AvailabilityStatus;
import model.enums.DayOfWeek;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

@Entity
@Table(name = "availabilities")
public class Availability {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // * Availability → 1 Doctor
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "doctor_id", nullable = false)
    private Doctor doctor;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private DayOfWeek jour;

    @Column(name = "heure_debut", nullable = false)
    private LocalTime heureDebut;

    @Column(name = "heure_fin", nullable = false)
    private LocalTime heureFin;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private AvailabilityStatus statut = AvailabilityStatus.ACTIVE;

    @Column(name = "date_debut")
    private LocalDate dateDebut; // période de validité

    @Column(name = "date_fin")
    private LocalDate dateFin;   // période de validité

    // ===== Constructeurs =====

    public Availability() {}

    public Availability(Doctor doctor, DayOfWeek jour,
                        LocalTime heureDebut, LocalTime heureFin,
                        LocalDate dateDebut, LocalDate dateFin) {
        this.doctor = doctor;
        this.jour = jour;
        this.heureDebut = heureDebut;
        this.heureFin = heureFin;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.statut = AvailabilityStatus.ACTIVE;
    }

    // ===== Helpers métier =====

    public boolean isActive() {
        return statut == AvailabilityStatus.ACTIVE;
    }

    public boolean isValidOn(LocalDate date) {
        if (dateDebut != null && date.isBefore(dateDebut)) return false;
        if (dateFin != null && date.isAfter(dateFin)) return false;
        return true;
    }

    // ===== Getters / Setters =====

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Doctor getDoctor() { return doctor; }
    public void setDoctor(Doctor doctor) { this.doctor = doctor; }

    public DayOfWeek getJour() { return jour; }
    public void setJour(DayOfWeek jour) { this.jour = jour; }

    public LocalTime getHeureDebut() { return heureDebut; }
    public void setHeureDebut(LocalTime heureDebut) { this.heureDebut = heureDebut; }

    public LocalTime getHeureFin() { return heureFin; }
    public void setHeureFin(LocalTime heureFin) { this.heureFin = heureFin; }

    public AvailabilityStatus getStatut() { return statut; }
    public void setStatut(AvailabilityStatus statut) { this.statut = statut; }

    public LocalDate getDateDebut() { return dateDebut; }
    public void setDateDebut(LocalDate dateDebut) { this.dateDebut = dateDebut; }

    public LocalDate getDateFin() { return dateFin; }
    public void setDateFin(LocalDate dateFin) { this.dateFin = dateFin; }

    // ===== equals / hashCode =====

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Availability)) return false;
        Availability that = (Availability) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() { return Objects.hash(id); }

    @Override
    public String toString() {
        return "Availability{id=" + id + ", jour=" + jour
                + ", " + heureDebut + "→" + heureFin + "}";
    }
}