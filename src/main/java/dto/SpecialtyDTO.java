package dto;

public class SpecialtyDTO {

    private Long id;
    private String nom;
    private String description;
    private Long departmentId;
    private String departmentNom;

    public SpecialtyDTO() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public String getDescription() { return description; }
    public void setDescription(String d) { this.description = d; }

    public Long getDepartmentId() { return departmentId; }
    public void setDepartmentId(Long d) { this.departmentId = d; }

    public String getDepartmentNom() { return departmentNom; }
    public void setDepartmentNom(String d) { this.departmentNom = d; }
}