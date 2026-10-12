package mapper;

import dto.PatientDTO;
import model.Patient;

import java.util.List;
import java.util.stream.Collectors;

public final class PatientMapper {
    private PatientMapper() {}

    public static PatientDTO toDTO(Patient p) {
        if (p == null) return null;
        PatientDTO dto = new PatientDTO();
        dto.setId(p.getId());
        dto.setNom(p.getNom());
        dto.setPrenom(p.getPrenom());
        dto.setEmail(p.getEmail());
        dto.setTelephone(p.getTelephone());
        dto.setCin(p.getCin());
        dto.setDateNaissance(p.getDateNaissance());
        dto.setGenre(p.getGenre());
        dto.setAdresse(p.getAdresse());
        dto.setGroupeSanguin(p.getGroupeSanguin());
        return dto;
    }

    public static List<PatientDTO> toDTOList(List<Patient> list) {
        return list.stream().map(PatientMapper::toDTO).collect(Collectors.toList());
    }
}