package ru.hackaton.vtb.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import ru.hackaton.vtb.model.Service;
import ru.hackaton.vtb.repository.ServiceRepository;
import ru.hackaton.vtb.util.ServiceNotFoundException;

import java.util.List;
import java.util.Optional;

@org.springframework.stereotype.Service
@Transactional(readOnly = true)
public class ServiceService {
    final private ServiceRepository serviceRepository;

    @Autowired
    public ServiceService(ServiceRepository serviceRepository) {
        this.serviceRepository = serviceRepository;
    }

    @Transactional
    public void save(Service service) {
        serviceRepository.save(service);
    }

    @Transactional
    public void deleteServiceById (int id) {
        Service service = this.findById(id);
        serviceRepository.delete(service);
    }

    @Transactional
    public void updateServiceById(Integer id, Service updateService) {
        Service newService = this.findById(id);
        newService.setService(updateService.getService());
        serviceRepository.save(updateService);
    }

    public List<Service> findAll() {
        return serviceRepository.findAll();
    }

    public Service findById(int id) {
        Optional<Service> service = serviceRepository.findById(id);
        return service.orElseThrow(ServiceNotFoundException::new);
    }
}
