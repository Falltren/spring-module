package com.fallt.spring_data_projections.mapper;

import com.fallt.spring_data_projections.domain.dto.request.UpsertDepartmentRequest;
import com.fallt.spring_data_projections.domain.dto.response.DepartmentResponse;
import com.fallt.spring_data_projections.domain.entity.Department;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

import static org.mapstruct.factory.Mappers.getMapper;

@Mapper(componentModel = "spring")
public interface DepartmentMapper {

    DepartmentMapper INSTANCE = getMapper(DepartmentMapper.class);

    Department toEntity(UpsertDepartmentRequest request);

    @Mapping(target = "id", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateDepartmentFromDto(UpsertDepartmentRequest request, @MappingTarget Department department);

    DepartmentResponse toResponse(Department department);

    List<DepartmentResponse> toListResponse(List<Department> departments);
}
