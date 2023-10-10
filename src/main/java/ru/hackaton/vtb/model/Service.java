package ru.hackaton.vtb.model;

import jakarta.persistence.*;

import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "service")
public class Service {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "service_id", nullable = false)
    private Integer id;

    @Column(name = "service", nullable = false, length = 100)
    private String service;

    @OneToMany(mappedBy = "service")
    private Set<DepartmentService> departmentServices = new LinkedHashSet<>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public Set<DepartmentService> getDepartmentServices() {
        return departmentServices;
    }

    public void setDepartmentServices(Set<DepartmentService> departmentServices) {
        this.departmentServices = departmentServices;
    }

}