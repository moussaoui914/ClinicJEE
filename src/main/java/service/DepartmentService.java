package service;

import model.Department;
import repository.DepartmentRepository;

import java.util.List;

public class DepartmentService {

    private final DepartmentRepository repo = new DepartmentRepository();

    public List<Department> findAll() { return repo.findAll(); }

    public Department findById(Long id) {
        return repo.findById(id).orElseThrow(() ->
                new IllegalArgumentException("Département introuvable : " + id));
    }

    public Department save(Department d) { return repo.save(d); }
    public void delete(Long id) { repo.delete(id); }
}