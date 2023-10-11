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
    @Query("select d from DepartmentService d " +
            "where (d.department.longitude - ?1) * (d.department.longitude - ?1)" +
            "+ (d.department.latitude - ?2) * (d.department.latitude - ?2) <= ?3" +
            "and d.service.id = ?4 and d.workload <= ?5")
    List<Department> findAllByServiceIdAndWorkloadLessThanEqualAndRadius(Double longitude, Double latitude, Integer radius, Integer serviceId, Double workload);
    @Query("select d.department from DepartmentService d " +
            "where " +
            "6371 * 2 * ATAN2 ( SQRT ( SIN ( (?2 - d.department.latitude) * PI() / 180 / 2) * SIN ( (?2 - d.department.latitude) * PI() / 180 / 2 ) + " +
            "COS ( d.department.latitude * PI() / 180 ) * COS ( ?2 * PI() / 180 ) * SIN ( (?1 - d.department.longitude) * PI() / 180 / 2 ) * SIN ( (?1 - d.department.longitude) * PI() / 180 / 2 ) ) ," +
            "SQRT ( 1 - SIN ( (?1 - d.department.latitude) * PI() / 180 / 2) * SIN ( (?2 - d.department.latitude) * PI() / 180 / 2 ) + " +
            "COS ( d.department.latitude * PI() / 180 ) * COS ( ?2 * PI() / 180 ) * SIN ( (?1 - d.department.longitude) * PI() / 180 / 2 ) * SIN ( (?1 - d.department.longitude) * PI() / 180 / 2 )) ) <= 50.00 ")
    List<Department> findAllByRadius(Double longitude, Double latitude, Integer radius);
    /*
    * d = 6371 * (2 * ATAN(SQRT((SIN(
    * ((d.department.lat - ?2) * PI/180) / 2) +
    *
    * )), SQRT(1 - ())
    * */
/*
*  d = 6371 * c
*  c = 2 * ATAN ( SQRT ( a ) , SQRT ( 1 - a) )
*  a = SIN ( dLat / 2) * SIN ( dLat / 2 ) + COS ( lat1 * PI / 180 ) * COS ( lat2 * PI / 180 ) * SIN ( dLon / 2 ) * SIN ( dLon / 2 )
*  dLat = (lat2 - lat1) * PI / 180
*  dLon = (lon2 - lon1) * PI / 180
* */
    /*
    * d = 6371 * 2 * ATAN ( SQRT ( SIN ( (lat2 - lat1) * PI / 180 / 2) * SIN ( (lat2 - lat1) * PI / 180 / 2 ) + COS ( lat1 * PI / 180 ) * COS ( lat2 * PI / 180 ) * SIN ( (lon2 - lon1) * PI / 180 / 2 ) * SIN ( (lon2 - lon1) * PI / 180 / 2 ) ) , SQRT ( 1 - SIN ( (lat2 - lat1) * PI / 180 / 2) * SIN ( (lat2 - lat1) * PI / 180 / 2 ) + COS ( lat1 * PI / 180 ) * COS ( lat2 * PI / 180 ) * SIN ( (lon2 - lon1) * PI / 180 / 2 ) * SIN ( (lon2 - lon1) * PI / 180 / 2 )) )
    *
    *
    * */
    /*
    @Query("select d.department from DepartmentService d where d.department.longitude between ?1 and ?2 and d.department.latitude between ?3 and ?4")
    List<Department> findAllByLongitudeBetweenAndLatitudeBetween(Double longitude, Double longitude2,
                                                                 Double latitude, Double latitude2);

  @Query("select d.department from DepartmentService d where d.department.longitude between ?1 and ?2 " +
            "and d.department.latitude between ?3 and ?4 " +
            "and d.service.id = ?5")
    List<Department> findAllByServiceIdAndLongitudeBetweenAndLatitudeBetween(Double longitude, Double longitude2,
                                                                             Double latitude, Double latitude2,
                                                                             Integer serviceId);
    @Query("select d from DepartmentService d where d.department.longitude between ?1 and ?2 " +
            "and d.department.latitude between ?3 and ?4 " +
            "and d.service.id = ?5 and d.workload <= ?6")
    List<Department> findAllByServiceIdAndWorkloadLessThanEqualAndRadius(Double longitude, Double longitude2,
                                                                                                        Double latitude, Double latitude2,
                                                                                                        Integer serviceId, Double workload);

     */
}