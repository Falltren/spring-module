package com.fallt.spring_data_projections;

import com.fallt.spring_data_projections.domain.dto.request.UpsertEmployeeRequest;

public final class TestConstant {

    private TestConstant() {
    }

    public static final String COMMON_EMPLOYEE_PATH = "/api/v1/employees";
    public static final String EMPLOYEE_ID_PATH = "/api/v1/employees/{id}";
    public static final UpsertEmployeeRequest EMPLOYEE_REQUEST = UpsertEmployeeRequest.builder()
            .firstName("Bob")
            .lastName("Kent")
            .position("stuff")
            .salary(200)
            .departmentName("Second")
            .build();
}
