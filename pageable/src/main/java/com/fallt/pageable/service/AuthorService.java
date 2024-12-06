package com.fallt.pageable.service;

import com.fallt.pageable.domain.dto.request.UpsertAuthorRequest;
import com.fallt.pageable.domain.dto.response.SuccessResponse;
import com.fallt.pageable.domain.entity.Author;
import com.fallt.pageable.exception.EntityNotFoundException;
import com.fallt.pageable.mapper.AuthorMapper;
import com.fallt.pageable.repository.AuthorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;

@Service
@RequiredArgsConstructor
public class AuthorService {

    private final AuthorRepository authorRepository;

    public SuccessResponse create(UpsertAuthorRequest request) {
        Author author = AuthorMapper.INSTANCE.toEntity(request);
        authorRepository.save(author);
        return SuccessResponse.builder().build();
    }

    public Author getAuthorById(Long id) {
        return authorRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(MessageFormat.format("Author with ID: {0} not found", id)));
    }
}
