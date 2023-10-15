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

import java.util.*;
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
        final int[] minWorkLoad = {0};
        if (services != null && !services.isEmpty() && services.get(0) != 0) {
            if (work) {
                for (minWorkLoad[0] = 1; minWorkLoad[0] <= 5; minWorkLoad[0]++) {
                    departments = departmentServiceRepository.findAllByParameters(lon, lat, rad, services.get(0), (double) minWorkLoad[0]);
                    System.out.println(minWorkLoad[0]);
                    System.out.println(departments != null);
                    if (departments != null && !departments.isEmpty()) {
                        break;
                    }
                }
                if (departments == null || departments.isEmpty()) {
                    departments = new ArrayList<>();
                }
                return departments.stream().map(department -> {
                    List<Double> servicesWorkloadList = new ArrayList<>();
                    List<Integer> servicesList = new ArrayList<>();
                    department.getDepartmentServices().forEach(service -> {
                        servicesList.add(service.getService().getId());
                        if (services.contains(service.getService().getId())) {
                            servicesWorkloadList.add(service.getWorkload());
                        }
                    });
                    OptionalDouble workload = servicesWorkloadList.stream().mapToDouble(e -> e).average();
                    if (workload.isPresent()) {
                        return departmentMapper.toDto(department, departmentDto, workload.getAsDouble(), servicesList);
                    } else {
                        return departmentMapper.toDto(department, departmentDto, 0, servicesList);
                    }
                }).collect(Collectors.toList());
            }
            departments = departmentServiceRepository.findAllByParameters(lon, lat, rad, services.get(0), null);
            return departments.stream()
                    .filter(department -> {
                        List<Integer> servicesList = new ArrayList<>();
                        department.getDepartmentServices().forEach(service -> {
                            if (services.contains(service.getService().getId())) {
                                servicesList.add(service.getService().getId());
                            }
                        });
                        return servicesList.size() == services.size();
                    })
                    .map(department -> {
                List<Double> servicesWorkloadList = new ArrayList<>();
                List<Integer> servicesList = new ArrayList<>();
                department.getDepartmentServices().forEach(service -> {
                    servicesList.add(service.getService().getId());
                    if (services.contains(service.getService().getId())) {
                        servicesWorkloadList.add(service.getWorkload());
                    }
                });
                OptionalDouble workload = servicesWorkloadList.stream().mapToDouble(e -> e).average();
                if (workload.isPresent()) {
                    return departmentMapper.toDto(department, departmentDto, workload.getAsDouble(), servicesList);
                } else {
                    return departmentMapper.toDto(department, departmentDto, 0, servicesList);
                }
            }).collect(Collectors.toList());
        } else if (work) {
            departments = departmentServiceRepository.findAllByParameters(lon, lat, rad, null, null);
            final Integer[] minWorkLoad_output = {5};
            departments.forEach(department -> {
                department.getDepartmentServices().forEach(service -> {
                   if (service.getWorkload() < minWorkLoad_output[0]) {
                       minWorkLoad_output[0] = service.getWorkload().intValue();
                   }
                });
            });
            departments = departments.stream().filter(department -> {
                List<Double> servicesWorkloadList = new ArrayList<>();
                department.getDepartmentServices().forEach(service -> {
                    servicesWorkloadList.add(service.getWorkload());
                });
                OptionalDouble workload = servicesWorkloadList.stream().mapToDouble(e -> e).average();
                if (workload.isPresent()) {
                    return  workload.getAsDouble() <= minWorkLoad_output[0];
                } else {
                    return false;
                }
            }).collect(Collectors.toList());
            if (departments.isEmpty()) {
                departments = new ArrayList<>();
            }
            return departments.stream().map(department -> {
                List<Double> servicesWorkloadList = new ArrayList<>();
                List<Integer> servicesList = new ArrayList<>();
                department.getDepartmentServices().forEach(service -> {
                    servicesWorkloadList.add(service.getWorkload());
                    servicesList.add(service.getService().getId());
                });
                OptionalDouble workload = servicesWorkloadList.stream().mapToDouble(e -> e).average();
                if (workload.isPresent()) {
                    return departmentMapper.toDto(department, departmentDto, workload.getAsDouble(), servicesList);
                } else {
                    return departmentMapper.toDto(department, departmentDto, 0, servicesList);
                }
            }).collect(Collectors.toList());
        }
        departments = departmentServiceRepository.findAllByParameters(lon, lat, rad, null, null);
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