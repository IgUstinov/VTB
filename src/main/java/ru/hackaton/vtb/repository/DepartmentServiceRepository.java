package ru.hackaton.vtb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.hackaton.vtb.model.Department;
import ru.hackaton.vtb.model.DepartmentService;

import java.util.List;

public interface DepartmentServiceRepository extends JpaRepository<DepartmentService, Integer> {
    @Query("select d.department from DepartmentService d " +
            "where " +
            "6371 * 2 * ATAN2 ( SQRT ( SIN ( (?2 - d.department.latitude) * PI() / 180 / 2) * SIN ( (?2 - d.department.latitude) * PI() / 180 / 2 ) + " +
            "COS ( d.department.latitude * PI() / 180 ) * COS ( ?2 * PI() / 180 ) * SIN ( (?1 - d.department.longitude) * PI() / 180 / 2 ) * SIN ( (?1 - d.department.longitude) * PI() / 180 / 2 ) ) ," +
            "SQRT ( 1 - SIN ( (?1 - d.department.latitude) * PI() / 180 / 2) * SIN ( (?2 - d.department.latitude) * PI() / 180 / 2 ) + " +
            "COS ( d.department.latitude * PI() / 180 ) * COS ( ?2 * PI() / 180 ) * SIN ( (?1 - d.department.longitude) * PI() / 180 / 2 ) * SIN ( (?1 - d.department.longitude) * PI() / 180 / 2 )) ) <= ?3 " +
            "and d.service.id = ?4")
    List<Department> findAllByServiceIdAndRadius(Double longitude, Double latitude, Integer radius, Integer serviceId);

    @Query("select d.department from DepartmentService d " +
            "where " +
            "6371 * 2 * ATAN2 ( SQRT ( SIN ( (?2 - d.department.latitude) * PI() / 180 / 2) * SIN ( (?2 - d.department.latitude) * PI() / 180 / 2 ) + " +
            "COS ( d.department.latitude * PI() / 180 ) * COS ( ?2 * PI() / 180 ) * SIN ( (?1 - d.department.longitude) * PI() / 180 / 2 ) * SIN ( (?1 - d.department.longitude) * PI() / 180 / 2 ) ) ," +
            "SQRT ( 1 - SIN ( (?1 - d.department.latitude) * PI() / 180 / 2) * SIN ( (?2 - d.department.latitude) * PI() / 180 / 2 ) + " +
            "COS ( d.department.latitude * PI() / 180 ) * COS ( ?2 * PI() / 180 ) * SIN ( (?1 - d.department.longitude) * PI() / 180 / 2 ) * SIN ( (?1 - d.department.longitude) * PI() / 180 / 2 )) ) <= ?3 " +
            "and d.service.id = ?4 and d.workload <= ?5")
    List<Department> findAllByServiceIdAndWorkloadLessThanEqualAndRadius(Double longitude, Double latitude, Integer radius, Integer serviceId, Double workload);

    @Query("select d.department from DepartmentService d " +
            "where " +
            "6371 * 2 * ATAN2 ( SQRT ( SIN ( (?2 - d.department.latitude) * PI() / 180 / 2) * SIN ( (?2 - d.department.latitude) * PI() / 180 / 2 ) + " +
            "COS ( d.department.latitude * PI() / 180 ) * COS ( ?2 * PI() / 180 ) * SIN ( (?1 - d.department.longitude) * PI() / 180 / 2 ) * SIN ( (?1 - d.department.longitude) * PI() / 180 / 2 ) ) ," +
            "SQRT ( 1 - SIN ( (?1 - d.department.latitude) * PI() / 180 / 2) * SIN ( (?2 - d.department.latitude) * PI() / 180 / 2 ) + " +
            "COS ( d.department.latitude * PI() / 180 ) * COS ( ?2 * PI() / 180 ) * SIN ( (?1 - d.department.longitude) * PI() / 180 / 2 ) * SIN ( (?1 - d.department.longitude) * PI() / 180 / 2 )) ) <= ?3 ")
    List<Department> findAllByRadius(Double longitude, Double latitude, Integer radius);
}