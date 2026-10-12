package mapper;

import dto.SpecialtyDTO;
import model.Specialty;

import java.util.List;
import java.util.stream.Collectors;

public final class SpecialtyMapper {

    private SpecialtyMapper() {}

    public static SpecialtyDTO toDTO(Specialty s) {
        if (s == null) return null;
        SpecialtyDTO dto = new SpecialtyDTO();
        dto.setId(s.getId());
        dto.setNom(s.getNom());
        dto.setDescription(s.getDescription());
        if (s.getDepartment() != null) {
            dto.setDepartmentId(s.getDepartment().getId());
            dto.setDepartmentNom(s.getDepartment().getNom());
        }
        return dto;
    }

    public static List<SpecialtyDTO> toDTOList(List<Specialty> list) {
        return list.stream().map(SpecialtyMapper::toDTO).collect(Collectors.toList());
    }
}