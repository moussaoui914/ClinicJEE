package dto;

public class DoctorDTO {
    private Long id;
    private String nom;
    private String prenom;
    private String email;
    private String telephone;
    private String matricule;
    private String titre;
    private String specialtyNom;
    private String departmentNom;

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
    public String getMatricule() { return matricule; }
    public void setMatricule(String m) { this.matricule = m; }
    public String getTitre() { return titre; }
    public void setTitre(String t) { this.titre = t; }
    public String getSpecialtyNom() { return specialtyNom; }
    public void setSpecialtyNom(String s) { this.specialtyNom = s; }
    public String getDepartmentNom() { return departmentNom; }
    public void setDepartmentNom(String d) { this.departmentNom = d; }

    public String getNomComplet() {
        return (titre != null ? titre + " " : "") + prenom + " " + nom;
    }
}