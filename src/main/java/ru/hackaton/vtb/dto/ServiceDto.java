package ru.hackaton.vtb.dto;

import lombok.*;

import java.io.Serializable;

/**
 * DTO for {@link ru.hackaton.vtb.model.Service}
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ServiceDto implements Serializable {
    String service;
}