package com.fallt.spring_data_projections.mapper;

import com.fallt.spring_data_projections.domain.dto.request.UpsertEmployeeRequest;
import com.fallt.spring_data_projections.domain.entity.Employee;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import static org.mapstruct.factory.Mappers.getMapper;

@Mapper(componentModel = "spring")
public interface EmployeeMapper {

    EmployeeMapper INSTANCE = getMapper(EmployeeMapper.class);

    Employee toEntity(UpsertEmployeeRequest request);

    @Mapping(target = "id", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEmployeeFromDto(UpsertEmployeeRequest request, @MappingTarget Employee employee);
}
