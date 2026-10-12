package service;

import exception.*;
import model.Appointment;
import model.Doctor;
import model.Patient;
import model.enums.AppointmentStatus;
import repository.AppointmentRepository;
import repository.DoctorRepository;
import repository.PatientRepository;

import java.time.LocalDateTime;
import java.util.List;

public class AppointmentService {

    private final AppointmentRepository appointmentRepo = new AppointmentRepository();
    private final DoctorRepository doctorRepo = new DoctorRepository();
    private final PatientRepository patientRepo = new PatientRepository();

    public Appointment create(Long patientId, Long doctorId,
                              LocalDateTime dateRdv, String motif, String typeStr) {

        // Vérifier patient
        Patient patient = patientRepo.findById(patientId)
                .orElseThrow(() -> new PatientNotFoundException("Patient introuvable."));

        // Vérifier médecin
        Doctor doctor = doctorRepo.findById(doctorId)
                .orElseThrow(() -> new DoctorNotFoundException("Médecin introuvable."));

        // Lead time 2h
        if (dateRdv.isBefore(LocalDateTime.now().plusHours(2)))
            throw new UnavailableSlotException("Lead time insuffisant (min. 2h).");

        // Vérifier conflit
        LocalDateTime fin = dateRdv.plusMinutes(30);
        List<Appointment> existants = appointmentRepo.findByDoctorAndDateRange(
                doctorId, dateRdv.minusMinutes(1), fin.plusMinutes(1));

        for (Appointment a : existants) {
            if (a.getStatut() == AppointmentStatus.CANCELED) continue;
            if (dateRdv.isBefore(a.getDateFin()) && fin.isAfter(a.getDateRdv()))
                throw new AppointmentConflictException("Créneau déjà occupé.");
        }

        Appointment a = new Appointment();
        a.setPatient(patient);
        a.setDoctor(doctor);
        a.setDateRdv(dateRdv);
        a.setDureeMinutes(30);
        a.setMotif(motif);
        a.setType(model.enums.AppointmentType.valueOf(typeStr));
        a.setStatut(AppointmentStatus.PLANNED);

        return appointmentRepo.save(a);
    }

    public List<Appointment> findByPatient(Long patientId) {
        return appointmentRepo.findByPatient(patientId);
    }

    public List<Appointment> findByDoctor(Long doctorId) {
        return appointmentRepo.findByDoctor(doctorId);
    }

    public Appointment findById(Long id) {
        return appointmentRepo.findById(id).orElseThrow(() ->
                new AppointmentNotFoundException("RDV introuvable : " + id));
    }

    public void cancel(Long appointmentId, Long patientId) {
        Appointment a = findById(appointmentId);

        if (!a.getPatient().getId().equals(patientId))
            throw new UnauthorizedActionException("Ce RDV ne vous appartient pas.");

        if (a.getStatut() != AppointmentStatus.PLANNED)
            throw new UnauthorizedActionException("RDV déjà terminé ou annulé.");

        if (!a.isCancelableByPatient())
            throw new UnauthorizedActionException("Annulation impossible < 12h avant.");

        a.setStatut(AppointmentStatus.CANCELED);
        appointmentRepo.save(a);
    }

    public void markAsDone(Long appointmentId) {
        Appointment a = findById(appointmentId);
        a.setStatut(AppointmentStatus.DONE);
        appointmentRepo.save(a);
    }

    public void reschedule(Long appointmentId, LocalDateTime nouvelleDate) {
        Appointment a = findById(appointmentId);
        a.setDateRdv(nouvelleDate);
        a.setStatut(AppointmentStatus.PLANNED);
        appointmentRepo.save(a);
    }
}