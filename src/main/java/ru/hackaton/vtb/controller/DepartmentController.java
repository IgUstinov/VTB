package ru.hackaton.vtb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hackaton.vtb.dto.DepartmentDto;
import ru.hackaton.vtb.model.Department;
import ru.hackaton.vtb.service.DepartmentService;

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
    public ResponseEntity<List<DepartmentDto>> getAllDepartments(@RequestBody DepartmentDto departmentDto) {
        return new ResponseEntity<>(departmentService.findAll(departmentDto), HttpStatus.OK);
    }


}
