package ru.hackaton.vtb.mapper;

import org.springframework.stereotype.Component;
import ru.hackaton.vtb.dto.DepartmentDto;
import ru.hackaton.vtb.model.Department;

@Component
public class DepartmentMapper {

    public Department toEntity(DepartmentDto departmentDto) {
        return Department.builder()
                .build();
    }

    public DepartmentDto toDto(Department department, DepartmentDto departmentDto, int minWorkLoad) {
        return DepartmentDto.builder()
                .serviceId(departmentDto.getServiceId())
                .accountWorkload(departmentDto.getAccountWorkload())
                .radius(departmentDto.getRadius())
                .workload((double)minWorkLoad)
                .department(department.getDepartment())
                .longitude(department.getLongitude())
                .latitude(department.getLatitude())
                .build();
    }
}
