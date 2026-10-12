package service;

import exception.AppointmentNotFoundException;
import exception.MedicalNoteLockedException;
import model.Appointment;
import model.Doctor;
import model.MedicalNote;
import model.enums.AppointmentStatus;
import repository.AppointmentRepository;
import repository.DoctorRepository;
import repository.MedicalNoteRepository;

import java.util.List;

public class MedicalNoteService {

    private final MedicalNoteRepository noteRepo = new MedicalNoteRepository();
    private final AppointmentRepository apptRepo = new AppointmentRepository();
    private final DoctorRepository doctorRepo = new DoctorRepository();

    public MedicalNote create(Long appointmentId, Long doctorId,
                              String diagnostic, String contenu) {

        Appointment a = apptRepo.findById(appointmentId)
                .orElseThrow(() -> new AppointmentNotFoundException("RDV introuvable."));

        if (a.getStatut() != AppointmentStatus.DONE)
            throw new IllegalStateException("Le RDV doit être DONE pour créer une note.");

        Doctor d = doctorRepo.findById(doctorId)
                .orElseThrow(() -> new IllegalArgumentException("Médecin introuvable."));

        MedicalNote note = new MedicalNote();
        note.setAppointment(a);
        note.setDoctor(d);
        note.setDiagnostic(diagnostic);
        note.setContenu(contenu);
        note.setValidee(false);

        return noteRepo.save(note);
    }

    public void validate(Long noteId) {
        MedicalNote n = noteRepo.findById(noteId)
                .orElseThrow(() -> new IllegalArgumentException("Note introuvable."));
        n.setValidee(true);
        noteRepo.save(n);
    }

    public void update(Long noteId, String diagnostic, String contenu) {
        MedicalNote n = noteRepo.findById(noteId)
                .orElseThrow(() -> new IllegalArgumentException("Note introuvable."));
        if (n.isLocked())
            throw new MedicalNoteLockedException("Note validée → non modifiable.");
        n.setDiagnostic(diagnostic);
        n.setContenu(contenu);
        noteRepo.save(n);
    }

    public List<MedicalNote> findByDoctor(Long doctorId) {
        return noteRepo.findByDoctor(doctorId);
    }

    public List<MedicalNote> findByPatient(Long patientId) {
        return noteRepo.findByPatient(patientId);
    }
}