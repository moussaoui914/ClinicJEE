package model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "doctors")
@PrimaryKeyJoinColumn(name = "user_id")
public class Doctor extends User {

    @Column(nullable = false, unique = true, length = 50)
    private String matricule;

    @Column(length = 50)
    private String titre; // Dr, Pr, etc.

    // * Doctor → 1 Specialty
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "specialty_id")
    private Specialty specialty;

    // * Doctor → 1 Department
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id")
    private Department department;

    // 1 Doctor → * Availability
    @OneToMany(mappedBy = "doctor", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Availability> availabilities = new ArrayList<>();

    // 1 Doctor → * Appointment
    @OneToMany(mappedBy = "doctor")
    private List<Appointment> appointments = new ArrayList<>();

    // 1 Doctor → * MedicalNote
    @OneToMany(mappedBy = "doctor")
    private List<MedicalNote> medicalNotes = new ArrayList<>();

    // ===== Constructeurs =====

    public Doctor() {
        setRole(model.enums.Role.DOCTOR);
    }

    public Doctor(String nom, String prenom, String email, String telephone,
                  String motDePasse, String matricule, String titre,
                  Specialty specialty, Department department) {
        super(nom, prenom, email, telephone, motDePasse, model.enums.Role.DOCTOR);
        this.matricule = matricule;
        this.titre = titre;
        this.specialty = specialty;
        this.department = department;
    }

    // ===== Helpers =====

    public void addAvailability(Availability a) {
        availabilities.add(a);
        a.setDoctor(this);
    }

    public void removeAvailability(Availability a) {
        availabilities.remove(a);
        a.setDoctor(null);
    }

    // ===== Getters / Setters =====

    public String getMatricule() { return matricule; }
    public void setMatricule(String matricule) { this.matricule = matricule; }

    public String getTitre() { return titre; }
    public void setTitre(String titre) { this.titre = titre; }

    public Specialty getSpecialty() { return specialty; }
    public void setSpecialty(Specialty specialty) { this.specialty = specialty; }

    public Department getDepartment() { return department; }
    public void setDepartment(Department department) { this.department = department; }

    public List<Availability> getAvailabilities() { return availabilities; }
    public void setAvailabilities(List<Availability> availabilities) { this.availabilities = availabilities; }

    public List<Appointment> getAppointments() { return appointments; }
    public void setAppointments(List<Appointment> appointments) { this.appointments = appointments; }

    public List<MedicalNote> getMedicalNotes() { return medicalNotes; }
    public void setMedicalNotes(List<MedicalNote> medicalNotes) { this.medicalNotes = medicalNotes; }
}