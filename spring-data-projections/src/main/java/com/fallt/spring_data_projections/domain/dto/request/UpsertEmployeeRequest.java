package com.fallt.spring_data_projections.domain.dto.request;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpsertEmployeeRequest {

    @JsonAlias("first_name")
    private String firstName;

    @JsonAlias("last_name")
    private String lastName;
    private String position;
    private Integer salary;

    @JsonAlias("department_name")
    private String departmentName;
}
