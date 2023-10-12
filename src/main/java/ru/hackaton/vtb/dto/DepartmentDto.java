package ru.hackaton.vtb.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.hackaton.vtb.model.Service;

import java.io.Serializable;
import java.util.List;

/**
 * DTO for {@link ru.hackaton.vtb.model.Department}
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DepartmentDto implements Serializable {
    private String department;
    private Double longitude;
    private Double latitude;
    private List<Integer> service;
    private Double workload;
    private Integer radius;
    private Boolean accountWorkload;
}