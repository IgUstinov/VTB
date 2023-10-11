package ru.hackaton.vtb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hackaton.vtb.model.Department;
import ru.hackaton.vtb.model.Service;
import ru.hackaton.vtb.service.ServiceService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/service")
public class ServiceController {
    private final ServiceService serviceService;

    @Autowired
    public ServiceController(ServiceService serviceService) {
        this.serviceService = serviceService;
    }

    @GetMapping("/read")
    public ResponseEntity<List<Service>> readAllServices() {
        return new ResponseEntity<>(serviceService.findAll(), HttpStatus.OK);
    }

    //метод возвращает автора по id
    @GetMapping("/{id}")
    public ResponseEntity<Service> getServiceById (@PathVariable("id") int id) {
        return new ResponseEntity<>(serviceService.findById(id), HttpStatus.OK);
    }

    //метод удаляет автора по id
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<HttpStatus> deleteServiceById (@PathVariable int id) {
        serviceService.deleteServiceById(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    //метод создания автора
    @PostMapping("/create")
    public ResponseEntity<HttpStatus> createService(@RequestBody Service service) {
        serviceService.save(service);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PatchMapping("/update/{id}")
    public ResponseEntity<HttpStatus> updateServiceById (@PathVariable("id") Integer id,
                                                    @RequestBody Service service) {
        serviceService.updateServiceById(id, service);
        return ResponseEntity.ok(HttpStatus.OK);
    }
}
