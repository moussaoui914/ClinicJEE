package dto;

public class RegisterDTO {
    private String nom;
    private String prenom;
    private String email;
    private String telephone;
    private String motDePasse;
    private String confirmationMotDePasse;

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }
    public String getPrenom() { return prenom; }
    public void setPrenom(String p) { this.prenom = p; }
    public String getEmail() { return email; }
    public void setEmail(String e) { this.email = e; }
    public String getTelephone() { return telephone; }
    public void setTelephone(String t) { this.telephone = t; }
    public String getMotDePasse() { return motDePasse; }
    public void setMotDePasse(String m) { this.motDePasse = m; }
    public String getConfirmationMotDePasse() { return confirmationMotDePasse; }
    public void setConfirmationMotDePasse(String c) { this.confirmationMotDePasse = c; }

    public boolean passwordsMatch() {
        return motDePasse != null && motDePasse.equals(confirmationMotDePasse);
    }
}