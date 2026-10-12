package mapper;

import dto.DepartmentDTO;
import model.Department;

import java.util.List;
import java.util.stream.Collectors;

public final class DepartmentMapper {

    private DepartmentMapper() {}

    public static DepartmentDTO toDTO(Department d) {
        if (d == null) return null;
        DepartmentDTO dto = new DepartmentDTO();
        dto.setId(d.getId());
        dto.setNom(d.getNom());
        dto.setDescription(d.getDescription());
        return dto;
    }

    public static List<DepartmentDTO> toDTOList(List<Department> list) {
        return list.stream().map(DepartmentMapper::toDTO).collect(Collectors.toList());
    }
}