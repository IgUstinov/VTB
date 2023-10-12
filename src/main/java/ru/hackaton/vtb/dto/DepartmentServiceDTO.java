package ru.hackaton.vtb.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for {@link ru.hackaton.vtb.model.DepartmentService}
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DepartmentServiceDTO {
    private int departmentId;
    private int serviceId;
    private double workload;
}
