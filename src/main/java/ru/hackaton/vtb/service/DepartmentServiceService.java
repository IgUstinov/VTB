package ru.hackaton.vtb.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import ru.hackaton.vtb.model.DepartmentService;
import ru.hackaton.vtb.repository.DepartmentServiceRepository;
import ru.hackaton.vtb.util.DepartmentServiceServiceException;

import java.util.List;
import java.util.Optional;

@org.springframework.stereotype.Service
public class DepartmentServiceService {
    final private DepartmentServiceRepository departmentServiceRepository;

    @Autowired
    public DepartmentServiceService(DepartmentServiceRepository departmentServiceRepository) {
        this.departmentServiceRepository = departmentServiceRepository;
    }

    @Transactional
    public void save(ru.hackaton.vtb.model.DepartmentService departmentService) {
        departmentServiceRepository.save(departmentService);
    }

    @Transactional
    public void deleteDepartmentServiceById(int id) {
        DepartmentService departmentService = this.findById(id);
        departmentServiceRepository.delete(departmentService);
    }

    @Transactional
    public void updateDepartmentServiceById(int id, ru.hackaton.vtb.model.DepartmentService updateDepartmentService) {
        DepartmentService newDepartmentService = this.findById(id);
        newDepartmentService.setDepartment(updateDepartmentService.getDepartment());
        newDepartmentService.setService(updateDepartmentService.getService());
        newDepartmentService.setWorkload(updateDepartmentService.getWorkload());
        departmentServiceRepository.save(updateDepartmentService);
    }

    public List<ru.hackaton.vtb.model.DepartmentService> findAll() {
        return departmentServiceRepository.findAll();
    }

    public ru.hackaton.vtb.model.DepartmentService findById(int id) {
        Optional<ru.hackaton.vtb.model.DepartmentService> service = departmentServiceRepository.findById(id);
        return service.orElseThrow(DepartmentServiceServiceException::new);
    }
}
