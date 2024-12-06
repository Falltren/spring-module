package com.fallt.pageable.mapper;

import com.fallt.pageable.domain.dto.request.UpsertBookRequest;
import com.fallt.pageable.domain.dto.response.BookResponse;
import com.fallt.pageable.domain.entity.Book;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

import static org.mapstruct.factory.Mappers.getMapper;

@Mapper(componentModel = "string")
public interface BookMapper {

    BookMapper INSTANCE = getMapper(BookMapper.class);

    @Mapping(target = "genre", expression = "java(com.fallt.pageable.domain.entity.enums.Genre.valueOf(request.getGenre()))")
    @Mapping(target = "publicationDate", expression = "java(java.time.LocalDate.parse(request.getPublicationDate()))")
    Book toEntity(UpsertBookRequest request);

    @Mapping(target = "author", expression = "java(book.getAuthor().getFirstName() + \" \" + book.getAuthor().getLastName())")
    BookResponse toResponse(Book book);

    List<BookResponse> toListDto(List<Book> books);

    @Mapping(target = "id", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateBookFromDto(UpsertBookRequest request, @MappingTarget Book user);
}
