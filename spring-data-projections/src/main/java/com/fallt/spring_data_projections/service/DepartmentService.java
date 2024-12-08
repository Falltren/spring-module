package com.fallt.spring_data_projections.service;

import com.fallt.spring_data_projections.domain.dto.request.UpsertDepartmentRequest;
import com.fallt.spring_data_projections.domain.dto.response.DepartmentResponse;
import com.fallt.spring_data_projections.domain.dto.response.SuccessResponse;
import com.fallt.spring_data_projections.domain.entity.Department;
import com.fallt.spring_data_projections.exception.EntityNotFoundException;
import com.fallt.spring_data_projections.mapper.DepartmentMapper;
import com.fallt.spring_data_projections.repository.DepartmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DepartmentService {

    private final DepartmentRepository departmentRepository;

    public SuccessResponse create(UpsertDepartmentRequest request) {
        Department department = DepartmentMapper.INSTANCE.toEntity(request);
        departmentRepository.save(department);
        return SuccessResponse.builder().build();
    }

    public SuccessResponse update(Long id, UpsertDepartmentRequest request) {
        Department department = getById(id);
        DepartmentMapper.INSTANCE.updateDepartmentFromDto(request, department);
        departmentRepository.save(department);
        return SuccessResponse.builder().build();
    }

    public DepartmentResponse getDepartment(Long id) {
        return DepartmentMapper.INSTANCE.toResponse(getById(id));
    }

    public List<DepartmentResponse> getAll() {
        return DepartmentMapper.INSTANCE.toListResponse(departmentRepository.findAll());
    }

    public void delete(Long id) {
        departmentRepository.deleteById(id);
    }

    public Department getByName(String name) {
        return departmentRepository.findByName(name).orElseThrow(
                () -> new EntityNotFoundException("Department with name: " + name + " not found")
        );
    }


    private Department getById(Long id) {
        return departmentRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Department with ID: " + id + " not found")
        );
    }
}
