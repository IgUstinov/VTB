package ru.hackaton.vtb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hackaton.vtb.model.Service;
import ru.hackaton.vtb.service.ServiceService;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ServiceController {
    //внедряем сервис и маппер (для преобразования DTO в модель и обратно)
    private final ServiceService serviceService;

    @Autowired
    public ServiceController(ServiceService serviceService) {
        this.serviceService = serviceService;
    }

    //метод возвращает список всех атворов
    @GetMapping("/services")
    public List<Service> getAuthors() {
        return new ArrayList<>(serviceService.findAll());
    }

    //метод возвращает автора по id
    @GetMapping("/service/{id}")
    public Service getAuthor(@PathVariable("id") int id) {
        return serviceService.getById(id);
    }

    //метод удаляет автора по id
    @DeleteMapping("/service/{id}")
    public ResponseEntity<HttpStatus> deleteAuthor(@PathVariable int id) {
        serviceService.delete(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    //метод создания автора
    @PostMapping("/service")
    public ResponseEntity<HttpStatus> createAuthor(@RequestBody Service service) {
        serviceService.save(service);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PatchMapping("/service")
    public ResponseEntity<HttpStatus> updateAuthor(@RequestBody Service service) {
        serviceService.update(service);
        return ResponseEntity.ok(HttpStatus.OK);
    }
}
