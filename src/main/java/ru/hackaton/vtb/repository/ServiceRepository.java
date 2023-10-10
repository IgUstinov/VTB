package ru.hackaton.vtb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.hackaton.vtb.model.Service;

public interface ServiceRepository extends JpaRepository<Service, Integer> {
}