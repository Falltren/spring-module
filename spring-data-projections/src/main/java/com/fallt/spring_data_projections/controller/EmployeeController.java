package com.fallt.spring_data_projections.controller;

import com.fallt.spring_data_projections.domain.dto.request.UpsertEmployeeRequest;
import com.fallt.spring_data_projections.domain.dto.response.SuccessResponse;
import com.fallt.spring_data_projections.repository.projections.EmployeeProjection;
import com.fallt.spring_data_projections.service.EmployeeService;
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
@RequestMapping("/api/v1/employees")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    @GetMapping("/{id}")
    public EmployeeProjection getById(@PathVariable Long id) {
        return employeeService.getById(id);
    }

    @GetMapping
    public List<EmployeeProjection> getAll() {
        return employeeService.getAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SuccessResponse create(@RequestBody UpsertEmployeeRequest request) {
        return employeeService.create(request);
    }

    @PutMapping("/{id}")
    public SuccessResponse update(@PathVariable Long id, @RequestBody UpsertEmployeeRequest request) {
        return employeeService.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        employeeService.delete(id);
    }
}
