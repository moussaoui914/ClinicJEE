package dto;

import model.enums.BloodGroup;
import model.enums.Gender;
import java.time.LocalDate;

public class PatientDTO {
    private Long id;
    private String nom;
    private String prenom;
    private String email;
    private String telephone;
    private String cin;
    private LocalDate dateNaissance;
    private Gender genre;
    private String adresse;
    private BloodGroup groupeSanguin;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }
    public String getPrenom() { return prenom; }
    public void setPrenom(String p) { this.prenom = p; }
    public String getEmail() { return email; }
    public void setEmail(String e) { this.email = e; }
    public String getTelephone() { return telephone; }
    public void setTelephone(String t) { this.telephone = t; }
    public String getCin() { return cin; }
    public void setCin(String cin) { this.cin = cin; }
    public LocalDate getDateNaissance() { return dateNaissance; }
    public void setDateNaissance(LocalDate d) { this.dateNaissance = d; }
    public Gender getGenre() { return genre; }
    public void setGenre(Gender g) { this.genre = g; }
    public String getAdresse() { return adresse; }
    public void setAdresse(String a) { this.adresse = a; }
    public BloodGroup getGroupeSanguin() { return groupeSanguin; }
    public void setGroupeSanguin(BloodGroup g) { this.groupeSanguin = g; }

    public String getNomComplet() { return prenom + " " + nom; }
}