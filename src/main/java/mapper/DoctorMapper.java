package mapper;

import dto.DoctorDTO;
import model.Doctor;

import java.util.List;
import java.util.stream.Collectors;

public final class DoctorMapper {
    private DoctorMapper() {}

    public static DoctorDTO toDTO(Doctor d) {
        if (d == null) return null;
        DoctorDTO dto = new DoctorDTO();
        dto.setId(d.getId());
        dto.setNom(d.getNom());
        dto.setPrenom(d.getPrenom());
        dto.setEmail(d.getEmail());
        dto.setTelephone(d.getTelephone());
        dto.setMatricule(d.getMatricule());
        dto.setTitre(d.getTitre());
        if (d.getSpecialty() != null) dto.setSpecialtyNom(d.getSpecialty().getNom());
        if (d.getDepartment() != null) dto.setDepartmentNom(d.getDepartment().getNom());
        return dto;
    }

    public static List<DoctorDTO> toDTOList(List<Doctor> list) {
        return list.stream().map(DoctorMapper::toDTO).collect(Collectors.toList());
    }
}