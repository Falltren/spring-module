package com.example.json_view_example.mapper;

import com.example.json_view_example.domain.dto.request.UpsertUserRequest;
import com.example.json_view_example.domain.entity.User;
import org.mapstruct.Mapper;

import static org.mapstruct.factory.Mappers.getMapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserMapper INSTANCE = getMapper(UserMapper.class);

    User toEntity(UpsertUserRequest request);
}
