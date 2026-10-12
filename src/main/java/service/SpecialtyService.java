package service;

import model.Specialty;
import repository.SpecialtyRepository;

import java.util.List;

public class SpecialtyService {

    private final SpecialtyRepository repo = new SpecialtyRepository();

    public List<Specialty> findAll() { return repo.findAll(); }

    public List<Specialty> findByDepartment(Long deptId) {
        return repo.findByDepartment(deptId);
    }

    public Specialty findById(Long id) {
        return repo.findById(id).orElseThrow(() ->
                new IllegalArgumentException("Spécialité introuvable : " + id));
    }

    public Specialty save(Specialty s) { return repo.save(s); }
    public void delete(Long id) { repo.delete(id); }
}