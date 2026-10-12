package service;

import model.Availability;
import repository.AvailabilityRepository;

import java.util.List;

public class AvailabilityService {

    private final AvailabilityRepository repo = new AvailabilityRepository();

    public List<Availability> findByDoctor(Long doctorId) {
        return repo.findByDoctor(doctorId);
    }

    public Availability save(Availability a) { return repo.save(a); }
    public void delete(Long id) { repo.delete(id); }

    public Availability findById(Long id) {
        return repo.findById(id).orElseThrow(() ->
                new IllegalArgumentException("Disponibilite introuvable : " + id));
    }
}