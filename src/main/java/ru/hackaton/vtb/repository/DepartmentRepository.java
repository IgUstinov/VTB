package ru.hackaton.vtb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.hackaton.vtb.model.Department;

import java.util.List;

public interface DepartmentRepository extends JpaRepository<Department, Integer> {
}