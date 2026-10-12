package dto;

public class LoginDTO {
    private String email;
    private String motDePasse;

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getMotDePasse() { return motDePasse; }
    public void setMotDePasse(String m) { this.motDePasse = m; }

    public boolean isValid() {
        return email != null && !email.isBlank()
                && motDePasse != null && !motDePasse.isBlank();
    }
}