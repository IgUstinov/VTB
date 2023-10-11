package ru.hackaton.vtb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.hackaton.vtb.model.Department;

import java.util.List;

public interface DepartmentRepository extends JpaRepository<Department, Integer> {

    @Query("select d from Department d where d.longitude between ?1 and ?2 and d.latitude between ?3 and ?4")
    List<Department> findAllByLongitudeBetweenAndLatitudeBetween(Double longitude, Double longitude2,
                                                                 Double latitude, Double latitude2);

}