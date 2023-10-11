package ru.hackaton.vtb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.hackaton.vtb.model.Department;


public interface DepartmentRepository extends JpaRepository<Department, Integer> {

}