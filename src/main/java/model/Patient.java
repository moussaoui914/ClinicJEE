package model;

import jakarta.persistence.*;
import model.enums.BloodGroup;
import model.enums.Gender;

import java.time.LocalDate;

@Entity
@Table(name = "patients")
@PrimaryKeyJoinColumn(name = "user_id")
public class Patient extends User {

    @Column(unique = true, length = 20)
    private String cin;

    @Column(name = "date_naissance")
    private LocalDate dateNaissance;

    @Enumerated(EnumType.STRING)
    @Column(length = 10)
    private Gender genre;

    @Column(length = 255)
    private String adresse;

    @Enumerated(EnumType.STRING)
    @Column(name = "groupe_sanguin", length = 5)
    private BloodGroup groupeSanguin;

    // ===== Constructeurs =====

    public Patient() {
        setRole(model.enums.Role.PATIENT);
    }

    public Patient(String nom, String prenom, String email, String telephone,
                   String motDePasse, String cin, LocalDate dateNaissance,
                   Gender genre, String adresse, BloodGroup groupeSanguin) {
        super(nom, prenom, email, telephone, motDePasse, model.enums.Role.PATIENT);
        this.cin = cin;
        this.dateNaissance = dateNaissance;
        this.genre = genre;
        this.adresse = adresse;
        this.groupeSanguin = groupeSanguin;
    }

    // ===== Getters / Setters =====

    public String getCin() { return cin; }
    public void setCin(String cin) { this.cin = cin; }

    public LocalDate getDateNaissance() { return dateNaissance; }
    public void setDateNaissance(LocalDate dateNaissance) { this.dateNaissance = dateNaissance; }

    public Gender getGenre() { return genre; }
    public void setGenre(Gender genre) { this.genre = genre; }

    public String getAdresse() { return adresse; }
    public void setAdresse(String adresse) { this.adresse = adresse; }

    public BloodGroup getGroupeSanguin() { return groupeSanguin; }
    public void setGroupeSanguin(BloodGroup groupeSanguin) { this.groupeSanguin = groupeSanguin; }

    // ===== Helpers =====

    public int getAge() {
        if (dateNaissance == null) return 0;
        return LocalDate.now().getYear() - dateNaissance.getYear();
    }
}