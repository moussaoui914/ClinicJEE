package service;

import exception.DoctorNotFoundException;
import exception.DuplicateMatriculeException;
import model.Doctor;
import repository.DoctorRepository;

import java.util.List;

public class DoctorService {

    private final DoctorRepository repo = new DoctorRepository();

    public List<Doctor> findAll() { return repo.findAll(); }

    public List<Doctor> findBySpecialty(Long specialtyId) {
        return repo.findBySpecialty(specialtyId);
    }

    public Doctor findById(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new DoctorNotFoundException("Médecin introuvable : " + id));
    }

    public Doctor create(Doctor d) {
        if (repo.findByMatricule(d.getMatricule()).isPresent())
            throw new DuplicateMatriculeException("Matricule déjà utilisé : " + d.getMatricule());
        return repo.save(d);
    }

    public Doctor update(Doctor d) { return repo.update(d); }

    public void delete(Long id) { repo.delete(id); }
}