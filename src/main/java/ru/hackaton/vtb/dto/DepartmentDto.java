package ru.hackaton.vtb.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Objects;

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
    private Integer serviceId;
    private Double workload;
    private Integer radius;
    private Boolean accountWorkload;
}