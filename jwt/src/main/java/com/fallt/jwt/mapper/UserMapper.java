package com.fallt.jwt.mapper;

import com.fallt.jwt.domain.dto.request.UpsertUserRequest;
import com.fallt.jwt.domain.dto.response.UserResponse;
import com.fallt.jwt.domain.entity.User;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import static org.mapstruct.factory.Mappers.getMapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserMapper INSTANCE = getMapper(UserMapper.class);

    @Mapping(target = "role", expression = "java(com.fallt.jwt.domain.entity.enums.Role.USER)")
    User toEntity(UpsertUserRequest request);

    UserResponse toResponse(User user);

    @Mapping(target = "id", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateUserFromDto(UpsertUserRequest request, @MappingTarget User user);
}
