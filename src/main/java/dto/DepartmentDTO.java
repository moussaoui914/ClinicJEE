package dto;

public class DepartmentDTO {

    private Long id;
    private String nom;
    private String description;

    public DepartmentDTO() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public String getDescription() { return description; }
    public void setDescription(String d) { this.description = d; }
}