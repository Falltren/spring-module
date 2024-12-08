package com.fallt.spring_data_projections.repository;

import com.fallt.spring_data_projections.domain.entity.Department;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface DepartmentRepository extends CrudRepository<Department, Long> {

    @Override
    List<Department> findAll();

    Optional<Department> findByName(String name);
}
