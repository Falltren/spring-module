package com.fallt.spring_data_projections.service;

import com.fallt.spring_data_projections.domain.dto.request.UpsertEmployeeRequest;
import com.fallt.spring_data_projections.domain.dto.response.SuccessResponse;
import com.fallt.spring_data_projections.domain.entity.Department;
import com.fallt.spring_data_projections.domain.entity.Employee;
import com.fallt.spring_data_projections.exception.EntityNotFoundException;
import com.fallt.spring_data_projections.mapper.EmployeeMapper;
import com.fallt.spring_data_projections.repository.EmployeeRepository;
import com.fallt.spring_data_projections.repository.projections.EmployeeProjection;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final DepartmentService departmentService;

    public EmployeeProjection getById(Long id) {
        EmployeeProjection employeeProjection = employeeRepository.getById(id);
        if (employeeProjection == null) {
            throw new EntityNotFoundException("Employee with ID: " + id + " not found");
        }
        return employeeProjection;
    }

    public List<EmployeeProjection> getAll() {
        return employeeRepository.findAllEmployees();
    }

    public SuccessResponse create(UpsertEmployeeRequest request) {
        Department department = departmentService.getByName(request.getDepartmentName());
        Employee employee = EmployeeMapper.INSTANCE.toEntity(request);
        employee.setDepartment(department);
        employeeRepository.save(employee);
        return SuccessResponse.builder().build();
    }

    public SuccessResponse update(Long id, UpsertEmployeeRequest request) {
        Employee employee = employeeRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Employee with ID: " + id + " not found")
        );
        EmployeeMapper.INSTANCE.updateEmployeeFromDto(request, employee);
        employeeRepository.save(employee);
        return SuccessResponse.builder().build();
    }

    public void delete(Long id) {
        employeeRepository.deleteById(id);
    }
}
