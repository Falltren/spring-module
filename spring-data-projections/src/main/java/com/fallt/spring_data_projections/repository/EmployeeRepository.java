package com.fallt.spring_data_projections.repository;

import com.fallt.spring_data_projections.domain.entity.Employee;
import org.springframework.data.repository.CrudRepository;

public interface EmployeeRepository extends CrudRepository<Employee, Long> {

    EmployeeProjection getById(Long id);
}
