package ru.hackaton.vtb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hackaton.vtb.dto.DepartmentDto;
import ru.hackaton.vtb.model.Department;
import ru.hackaton.vtb.model.Service;
import ru.hackaton.vtb.service.DepartmentService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/department")
public class DepartmentController {

    private final DepartmentService departmentService;

    @Autowired
    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @PostMapping("")
    public ResponseEntity<List<DepartmentDto>> readAllDepartmentsByDto(@RequestBody DepartmentDto departmentDto) {
        return new ResponseEntity<>(departmentService.findAllByDto(departmentDto), HttpStatus.OK);
    }

    /*@GetMapping("/read")
    public ResponseEntity<List<Department>> readAllDepartments() {
        return new ResponseEntity<>(departmentService.findAll(), HttpStatus.OK);
    }*/
    @GetMapping("/read")
    public ResponseEntity<List<Department>> readAllDepartments() {
        return new ResponseEntity<>(departmentService.findAll(), HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<HttpStatus> createDepartment(@RequestBody Department department) {
        departmentService.save(department);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PatchMapping("/update/{id}")
    public ResponseEntity<HttpStatus> updateDepartmentById(@PathVariable("id") Integer id,
                                                          @RequestBody Department department) {
        departmentService.updateDepartmentById(id, department);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<HttpStatus> deleteDepartmentById(@PathVariable("id") int id) {
        departmentService.deleteDepartmentById(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }
}
