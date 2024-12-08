package com.fallt.spring_data_projections.repository;

import com.fallt.spring_data_projections.domain.entity.Department;
import org.springframework.data.repository.CrudRepository;

public interface DepartmentRepository extends CrudRepository<Department, Long> {
}
