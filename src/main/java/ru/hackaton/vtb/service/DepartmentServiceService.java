package ru.hackaton.vtb.service;

import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import ru.hackaton.vtb.dto.DepartmentServiceDTO;
import ru.hackaton.vtb.mapper.DepartmentServiceMapper;
import ru.hackaton.vtb.model.Department;
import ru.hackaton.vtb.model.DepartmentService;
import ru.hackaton.vtb.model.Service;
import ru.hackaton.vtb.repository.DepartmentRepository;
import ru.hackaton.vtb.repository.DepartmentServiceRepository;
import ru.hackaton.vtb.repository.ServiceRepository;
import ru.hackaton.vtb.util.DepartmentNotFoundException;
import ru.hackaton.vtb.util.DepartmentServiceServiceException;
import ru.hackaton.vtb.util.ServiceNotFoundException;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@org.springframework.stereotype.Service
public class DepartmentServiceService {
    final private DepartmentServiceRepository departmentServiceRepository;
    final private ServiceRepository serviceRepository;
    final private DepartmentRepository departmentRepository;
    final private DepartmentServiceMapper departmentServiceMapper;

    @Autowired
    public DepartmentServiceService(DepartmentServiceRepository departmentServiceRepository, ServiceRepository serviceRepository, DepartmentRepository departmentRepository, DepartmentServiceMapper departmentServiceMapper) {
        this.departmentServiceRepository = departmentServiceRepository;
        this.serviceRepository = serviceRepository;
        this.departmentRepository = departmentRepository;
        this.departmentServiceMapper = departmentServiceMapper;
    }

    @Transactional
    public void save(DepartmentServiceDTO departmentServiceDTO) {
        Optional<Service> service = serviceRepository.findById(departmentServiceDTO.getServiceId());
        Optional<Department> department = departmentRepository.findById(departmentServiceDTO.getDepartmentId());
        departmentServiceRepository.save(departmentServiceMapper.toModel(departmentServiceDTO,
                department.orElseThrow(DepartmentNotFoundException::new),
                service.orElseThrow(ServiceNotFoundException::new)));
    }

    @Transactional
    public void deleteDepartmentServiceById(int id) {
        Optional<DepartmentService> departmentService = departmentServiceRepository.findById(id);
        departmentServiceRepository.delete(departmentService.orElseThrow(DepartmentServiceServiceException::new));
    }

    @Transactional
    public void updateDepartmentServiceById(int id, DepartmentServiceDTO departmentServiceDTO) {
        Optional<Service> service = serviceRepository.findById(departmentServiceDTO.getServiceId());
        Optional<Department> department = departmentRepository.findById(departmentServiceDTO.getDepartmentId());
        DepartmentService updateDepartmentService = departmentServiceMapper.toModel(departmentServiceDTO,
                department.orElseThrow(DepartmentNotFoundException::new),
                service.orElseThrow(ServiceNotFoundException::new));
        Optional<DepartmentService> newDepartmentService = departmentServiceRepository.findById(id);
        newDepartmentService.orElseThrow(DepartmentServiceServiceException::new).setDepartment(updateDepartmentService.getDepartment());
        newDepartmentService.orElseThrow(DepartmentServiceServiceException::new).setService(updateDepartmentService.getService());
        newDepartmentService.orElseThrow(DepartmentServiceServiceException::new).setWorkload(updateDepartmentService.getWorkload());
        departmentServiceRepository.save(updateDepartmentService);
    }

    public List<DepartmentServiceDTO> findAll() {
        List<DepartmentServiceDTO> departmentServiceDTOs = new ArrayList<>();
        for (DepartmentService departmentService : departmentServiceRepository.findAll()) {
            departmentServiceDTOs.add(departmentServiceMapper.toDto(departmentService.getDepartment().getId(),
                    departmentService.getService().getId(),
                    departmentService.getWorkload()));
        }
        return departmentServiceDTOs;
    }

    public DepartmentServiceDTO findById(int id) {
        Optional<ru.hackaton.vtb.model.DepartmentService> departmentService = departmentServiceRepository.findById(id);
        return departmentServiceMapper.toDto(departmentService.orElseThrow(DepartmentServiceServiceException::new).getDepartment().getId(),
                departmentService.orElseThrow(DepartmentServiceServiceException::new).getService().getId(),
                departmentService.orElseThrow(DepartmentServiceServiceException::new).getWorkload());
    }
}
