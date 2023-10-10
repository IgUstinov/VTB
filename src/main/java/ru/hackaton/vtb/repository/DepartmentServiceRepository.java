package ru.hackaton.vtb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.hackaton.vtb.model.DepartmentService;

public interface DepartmentServiceRepository extends JpaRepository<DepartmentService, Integer> {
}