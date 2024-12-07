package com.fallt.pageable.service;

import com.fallt.pageable.domain.dto.request.BookFilter;
import com.fallt.pageable.domain.dto.request.UpsertBookRequest;
import com.fallt.pageable.domain.dto.response.BookResponse;
import com.fallt.pageable.domain.dto.response.SuccessResponse;
import com.fallt.pageable.domain.entity.Author;
import com.fallt.pageable.domain.entity.Book;
import com.fallt.pageable.exception.EntityNotFoundException;
import com.fallt.pageable.mapper.BookMapper;
import com.fallt.pageable.repository.BookRepository;
import com.fallt.pageable.repository.specification.BookSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;
    private final AuthorService authorService;

    public SuccessResponse create(UpsertBookRequest request) {
        Book book = BookMapper.INSTANCE.toEntity(request);
        Author author = authorService.getAuthorById(request.getAuthorId());
        book.setAuthor(author);
        bookRepository.save(book);
        return SuccessResponse.builder().build();
    }

    public SuccessResponse update(Long id, UpsertBookRequest request) {
        Book existedBook = findBook(id);
        BookMapper.INSTANCE.updateBookFromDto(request, existedBook);
        bookRepository.save(existedBook);
        return SuccessResponse.builder().build();
    }

    public void delete(Long id) {
        bookRepository.deleteById(id);
    }

    public BookResponse getBookById(Long id) {
        Book book = findBook(id);
        return BookMapper.INSTANCE.toResponse(book);
    }

    public List<BookResponse> getAll(Pageable pageable) {
        return BookMapper.INSTANCE.toListDto(bookRepository.findAll(pageable).getContent());
    }

    public List<BookResponse> filterBy(BookFilter bookFilter){
        return BookMapper.INSTANCE.toListDto(bookRepository.findAll(BookSpecification.withFilter(bookFilter),
                PageRequest.of(bookFilter.getOffset(), bookFilter.getLimit())).getContent());
    }

    private Book findBook(Long id) {
        return bookRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(MessageFormat.format("Book with ID: {0} not found", id)));
    }
}
