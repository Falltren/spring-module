package com.fallt.spring_data_projections.repository;

import com.fallt.spring_data_projections.domain.entity.Employee;
import com.fallt.spring_data_projections.repository.projections.EmployeeProjection;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface EmployeeRepository extends CrudRepository<Employee, Long> {

    @Query("SELECT e FROM Employee e WHERE e.id = :id")
    EmployeeProjection getById(Long id);

    @Query("SELECT e FROM Employee e")
    List<EmployeeProjection> findAllEmployees();
}
