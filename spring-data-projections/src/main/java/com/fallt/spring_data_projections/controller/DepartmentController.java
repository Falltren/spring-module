package com.fallt.spring_data_projections.controller;

import com.fallt.spring_data_projections.domain.dto.request.UpsertDepartmentRequest;
import com.fallt.spring_data_projections.domain.dto.response.DepartmentResponse;
import com.fallt.spring_data_projections.domain.dto.response.SuccessResponse;
import com.fallt.spring_data_projections.service.DepartmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/departments")
@RequiredArgsConstructor
public class DepartmentController {

    private final DepartmentService departmentService;

    @GetMapping
    public List<DepartmentResponse> getAll() {
        return departmentService.getAll();
    }

    @GetMapping("/{id}")
    public DepartmentResponse getById(@PathVariable Long id) {
        return departmentService.getDepartment(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SuccessResponse create(@RequestBody UpsertDepartmentRequest request) {
        return departmentService.create(request);
    }

    @PutMapping("/{id}")
    public SuccessResponse update(@PathVariable Long id, @RequestBody UpsertDepartmentRequest request) {
        return departmentService.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        departmentService.delete(id);
    }
}
