package com.fallt.pageable.mapper;

import com.fallt.pageable.domain.dto.request.UpsertAuthorRequest;
import com.fallt.pageable.domain.entity.Author;
import org.mapstruct.Mapper;

import static org.mapstruct.factory.Mappers.getMapper;

@Mapper(componentModel = "spring")
public interface AuthorMapper {

    AuthorMapper INSTANCE = getMapper(AuthorMapper.class);

    Author toEntity(UpsertAuthorRequest request);
}
