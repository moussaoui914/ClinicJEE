package model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "departments")
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 100)
    private String nom;

    @Column(length = 255)
    private String description;

    // 1 Department → * Specialty
    @OneToMany(mappedBy = "department", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Specialty> specialties = new ArrayList<>();

    // 1 Department → * Doctor
    @OneToMany(mappedBy = "department")
    private List<Doctor> doctors = new ArrayList<>();

    // ===== Constructeurs =====

    public Department() {}

    public Department(String nom, String description) {
        this.nom = nom;
        this.description = description;
    }

    // ===== Helpers =====

    public void addSpecialty(Specialty s) {
        specialties.add(s);
        s.setDepartment(this);
    }

    public void removeSpecialty(Specialty s) {
        specialties.remove(s);
        s.setDepartment(null);
    }

    // ===== Getters / Setters =====

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public List<Specialty> getSpecialties() { return specialties; }
    public void setSpecialties(List<Specialty> specialties) { this.specialties = specialties; }

    public List<Doctor> getDoctors() { return doctors; }
    public void setDoctors(List<Doctor> doctors) { this.doctors = doctors; }

    // ===== equals / hashCode =====

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Department)) return false;
        Department that = (Department) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() { return Objects.hash(id); }

    @Override
    public String toString() {
        return "Department{id=" + id + ", nom='" + nom + "'}";
    }
}