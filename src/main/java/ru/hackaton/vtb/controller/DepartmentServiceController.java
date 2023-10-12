package ru.hackaton.vtb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hackaton.vtb.dto.DepartmentServiceDTO;
import ru.hackaton.vtb.model.DepartmentService;
import ru.hackaton.vtb.service.DepartmentServiceService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/department_service")
public class DepartmentServiceController {
    private final DepartmentServiceService departmentServiceService;

    @Autowired
    public DepartmentServiceController(DepartmentServiceService departmentServiceService) {
        this.departmentServiceService = departmentServiceService;
    }

    @GetMapping("/read")
    public List<DepartmentServiceDTO> readAllDepartmentServices() {
        return new ArrayList<>(departmentServiceService.findAll());
    }

    //метод возвращает автора по id
    @GetMapping("/{id}")
    public DepartmentServiceDTO getDepartmentServiceById (@PathVariable("id") int id) {
        return departmentServiceService.findById(id);
    }

    //метод удаляет автора по id
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<HttpStatus> deleteServiceById (@PathVariable int id) {
        departmentServiceService.deleteDepartmentServiceById(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    //метод создания автора
    @PostMapping("/create")
    public ResponseEntity<HttpStatus> createService(@RequestBody DepartmentServiceDTO departmentServiceDTO) {
        departmentServiceService.save(departmentServiceDTO);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PatchMapping("/update/{id}")
    public ResponseEntity<HttpStatus> updateServiceById (@PathVariable("id") Integer id,
                                                         @RequestBody DepartmentServiceDTO departmentServiceDTO) {
        departmentServiceService.updateDepartmentServiceById(id, departmentServiceDTO);
        return ResponseEntity.ok(HttpStatus.OK);
    }
}
