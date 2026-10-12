package mapper;

import dto.MedicalNoteDTO;
import model.MedicalNote;

import java.util.List;
import java.util.stream.Collectors;

public final class MedicalNoteMapper {
    private MedicalNoteMapper() {}

    public static MedicalNoteDTO toDTO(MedicalNote n) {
        if (n == null) return null;
        MedicalNoteDTO dto = new MedicalNoteDTO();
        dto.setId(n.getId());
        if (n.getAppointment() != null) dto.setAppointmentId(n.getAppointment().getId());
        if (n.getDoctor() != null) {
            dto.setDoctorId(n.getDoctor().getId());
            dto.setDoctorNom(n.getDoctor().getNomComplet());
        }
        dto.setDiagnostic(n.getDiagnostic());
        dto.setContenu(n.getContenu());
        dto.setDateNote(n.getDateNote());
        dto.setValidee(n.getValidee());
        return dto;
    }

    public static List<MedicalNoteDTO> toDTOList(List<MedicalNote> list) {
        return list.stream().map(MedicalNoteMapper::toDTO).collect(Collectors.toList());
    }
}