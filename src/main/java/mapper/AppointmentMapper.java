package mapper;

import dto.AppointmentDTO;
import model.Appointment;

import java.util.List;
import java.util.stream.Collectors;

public final class AppointmentMapper {
    private AppointmentMapper() {}

    public static AppointmentDTO toDTO(Appointment a) {
        if (a == null) return null;
        AppointmentDTO dto = new AppointmentDTO();
        dto.setId(a.getId());
        if (a.getPatient() != null) {
            dto.setPatientId(a.getPatient().getId());
            dto.setPatientNom(a.getPatient().getNomComplet());
        }
        if (a.getDoctor() != null) {
            dto.setDoctorId(a.getDoctor().getId());
            dto.setDoctorNom(a.getDoctor().getNomComplet());
            if (a.getDoctor().getSpecialty() != null)
                dto.setSpecialtyNom(a.getDoctor().getSpecialty().getNom());
        }
        dto.setDateRdv(a.getDateRdv());
        dto.setDureeMinutes(a.getDureeMinutes());
        dto.setType(a.getType());
        dto.setStatut(a.getStatut());
        dto.setMotif(a.getMotif());
        return dto;
    }

    public static List<AppointmentDTO> toDTOList(List<Appointment> list) {
        return list.stream().map(AppointmentMapper::toDTO).collect(Collectors.toList());
    }
}