package com.fallt.spring_data_projections.repository;

import com.fallt.spring_data_projections.domain.entity.Employee;
import com.fallt.spring_data_projections.repository.projections.EmployeeProjection;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface EmployeeRepository extends CrudRepository<Employee, Long> {

    EmployeeProjection getById(Long id);

    List<EmployeeProjection> findAllEmployees();
}
