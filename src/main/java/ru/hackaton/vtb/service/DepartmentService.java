package ru.hackaton.vtb.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.hackaton.vtb.dto.DepartmentDto;
import ru.hackaton.vtb.mapper.DepartmentMapper;
import ru.hackaton.vtb.model.Department;
import ru.hackaton.vtb.repository.DepartmentServiceRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class DepartmentService {
    private final DepartmentServiceRepository departmentServiceRepository;
    private final DepartmentMapper departmentMapper;

    @Autowired
    public DepartmentService(DepartmentServiceRepository departmentServiceRepository, DepartmentMapper departmentMapper) {
        this.departmentServiceRepository = departmentServiceRepository;
        this.departmentMapper = departmentMapper;
    }

    public List<DepartmentDto> findAll(DepartmentDto departmentDto) {
        System.out.println(departmentDto);
        Double lon = departmentDto.getLongitude();
        Double lat = departmentDto.getLatitude();
        Integer rad = departmentDto.getRadius();
        Integer serviceId = departmentDto.getServiceId();
        Double workload = departmentDto.getWorkload();
        Boolean work = departmentDto.getAccountWorkload();
        List<Department> departments = null;
        int minWorkLoad = 0;
        if (serviceId != 0) {
            if (work) {
                minWorkLoad = 1;
                for (minWorkLoad = 1; minWorkLoad < 5; minWorkLoad++) {
                    departments = departmentServiceRepository.findAllByServiceIdAndWorkloadLessThanEqualAndRadius(
                            lon, lat, rad, serviceId, (double) minWorkLoad);
                    if (departments != null) {
                        break;
                    }
                }
                /*if (departments != null) {
                    //return departments;
                }*/
            }
            if (departments == null) {
                departments = departmentServiceRepository.findAllByServiceIdAndRadius(
                        lon, lat, rad, serviceId);
            }
            //return departmentServiceRepository.findAllByServiceId(serviceId);
        }
        if (departments == null) {
            departments = departmentServiceRepository.findAllByRadius(
                    lon, lat, rad);
        }
        int finalMinWorkLoad = minWorkLoad;
        return departments.stream()
                .map((department -> departmentMapper.toDto(department, departmentDto, finalMinWorkLoad)))
                .collect(Collectors.toList());
        /*return departmentServiceRepository.findAllByLongitudeBetweenAndLatitudeBetween(lon+rad.doubleValue(),
                lon-rad.doubleValue(), lat+rad.doubleValue(), lat-rad.doubleValue());*/
    }
}
/*
departmentRepository.findAllByLongitudeBetweenAndLatitudeBetween(lon+rad.doubleValue(),
lon-rad.doubleValue(), lat+rad.doubleValue(), lat-rad.doubleValue());
*/
//return departmentRepository.findAll();