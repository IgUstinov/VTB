package ru.hackaton.vtb.mapper;

import org.springframework.stereotype.Component;
import ru.hackaton.vtb.dto.DepartmentDto;
import ru.hackaton.vtb.dto.DepartmentServiceDTO;
import ru.hackaton.vtb.model.Department;
import ru.hackaton.vtb.model.DepartmentService;
import ru.hackaton.vtb.model.Service;

@Component
public class DepartmentServiceMapper {

    public DepartmentService toModel(DepartmentServiceDTO departmentServiceDTO, Department department,
                                     Service service) {
        DepartmentService departmentService = new DepartmentService();
        departmentService.setDepartment(department);
        departmentService.setService(service);
        departmentService.setWorkload(departmentServiceDTO.getWorkload());
        return departmentService;
    }

    public DepartmentServiceDTO toDto(int departmentId, int serviceId, double workload) {
        return DepartmentServiceDTO.builder()
                .departmentId(departmentId)
                .serviceId(serviceId)
                .workload(workload)
                .build();
    }
}
