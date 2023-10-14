package ru.hackaton.vtb.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.hackaton.vtb.dto.DepartmentDto;
import ru.hackaton.vtb.mapper.DepartmentMapper;
import ru.hackaton.vtb.model.Department;
import ru.hackaton.vtb.repository.DepartmentRepository;
import ru.hackaton.vtb.repository.DepartmentServiceRepository;
import ru.hackaton.vtb.repository.ServiceRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;

@Service
@Transactional(readOnly = true)
public class DepartmentService {
    private final DepartmentServiceRepository departmentServiceRepository;
    private final DepartmentRepository departmentRepository;
    private final DepartmentMapper departmentMapper;

    @Autowired
    public DepartmentService(DepartmentServiceRepository departmentServiceRepository, DepartmentRepository departmentRepository, ServiceRepository serviceRepository1, DepartmentMapper departmentMapper) {
        this.departmentServiceRepository = departmentServiceRepository;
        this.departmentRepository = departmentRepository;
        this.departmentMapper = departmentMapper;
    }

    public List<Department> findAll() {
        return departmentRepository.findAll();
    }

    public List<DepartmentDto> findAllByDto(DepartmentDto departmentDto) {
        System.out.println(departmentDto);
        Double lon = departmentDto.getLongitude();
        Double lat = departmentDto.getLatitude();
        Integer rad = departmentDto.getRadius();
        List<Integer> services = departmentDto.getService();
        Boolean work = departmentDto.getAccountWorkload();
        List<Department> departments = null;
        int minWorkLoad = 0;
        if (services != null && services.get(0) != 0) {
            if (work) {
                for (minWorkLoad = 1; minWorkLoad < 5; minWorkLoad++) {
                    departments = departmentServiceRepository.findAllByServiceIdAndWorkloadLessThanEqualAndRadius(
                            lon, lat, rad, services.get(0), (double) minWorkLoad);
                    if (departments != null) {
                        break;
                    }
                }
            }
            if (departments == null) {
                departments = departmentServiceRepository.findAllByServiceIdAndRadius(
                        lon, lat, rad, services.get(0));
            }
        }
        if (departments == null) {
            departments = departmentServiceRepository.findAllByRadius(
                    lon, lat, rad);
            return departments.stream().map(department -> {
                List<Integer> servicesList = new ArrayList<>();
                List<Double> servicesWorkloadList = new ArrayList<>();
                department.getDepartmentServices().forEach(service -> {
                    servicesList.add(service.getService().getId());
                    servicesWorkloadList.add(service.getWorkload());
                });
                OptionalDouble workload = servicesWorkloadList.stream().mapToDouble(e -> e).average();
                if (workload.isPresent()) {
                    System.out.println(
                            departmentMapper.toDto(department, departmentDto, workload.getAsDouble(), servicesList).getDepartment()
                    );
                    System.out.println(
                            departmentMapper.toDto(department, departmentDto, workload.getAsDouble(), servicesList).getWorkload()
                    );
                    return departmentMapper.toDto(department, departmentDto, workload.getAsDouble(), servicesList);
                } else {
                    return departmentMapper.toDto(department, departmentDto, 0, servicesList);
                }
            }).collect(Collectors.toList());
        }
        int finalMinWorkLoad = minWorkLoad;
        return departments.stream()
                .map((department -> departmentMapper.toDto(department, departmentDto, finalMinWorkLoad)))
                .collect(Collectors.toList());
    }

    @Transactional
    public void save(Department department) {
        departmentRepository.save(department);
    }

    public Department findById(int id) {
        Optional<Department> foundDepartment = departmentRepository.findById(id);
        return foundDepartment.orElse(null);
    }

    @Transactional
    public void updateDepartmentById(Integer id, Department newDepartment) {
        Department updatedDepartment = this.findById(id);
        updatedDepartment.setDepartment(newDepartment.getDepartment());
        updatedDepartment.setLongitude(newDepartment.getLongitude());
        updatedDepartment.setLatitude(newDepartment.getLatitude());
        departmentRepository.save(updatedDepartment);
    }

    @Transactional
    public void deleteDepartmentById(Integer id) {
        Department department = this.findById(id);
        departmentRepository.delete(department);
    }
}