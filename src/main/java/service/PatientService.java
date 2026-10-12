package service;

import exception.PatientNotFoundException;
import model.Patient;
import repository.PatientRepository;

import java.util.List;

public class PatientService {

    private final PatientRepository repo = new PatientRepository();

    public List<Patient> findAll() { return repo.findAll(); }

    public Patient findById(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new PatientNotFoundException("Patient introuvable : " + id));
    }

    public Patient update(Patient p) { return repo.update(p); }

    public void delete(Long id) { repo.delete(id); }
}