package com.fallt.spring_data_jdbc.service;

import com.fallt.spring_data_jdbc.domain.dto.request.UpsertBookRequest;
import com.fallt.spring_data_jdbc.domain.dto.response.BookResponse;
import com.fallt.spring_data_jdbc.domain.dto.response.SuccessResponse;
import com.fallt.spring_data_jdbc.domain.entity.Book;
import com.fallt.spring_data_jdbc.exception.EntityNotFoundException;
import com.fallt.spring_data_jdbc.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;

    public SuccessResponse create(UpsertBookRequest request) {
        Book book = Book.toEntity(request);
        bookRepository.save(book);
        return SuccessResponse.builder().build();
    }

    public SuccessResponse update(Long id, UpsertBookRequest request){
        Book book = getBookById(id);
        book.updateNotNullField(request);
        bookRepository.update(book);
        return SuccessResponse.builder().build();
    }

    public void delete(Long id){
        bookRepository.deleteById(id);
    }

    public BookResponse getById(Long id){
        Book book = getBookById(id);
        return book.toResponse();
    }

    public List<BookResponse> getAll(){
        List<Book> books = bookRepository.findAll();
        return books.stream().map(Book::toResponse).toList();
    }

    private Book getBookById(Long id) {
        return bookRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(MessageFormat.format("Book with ID: {0} not found", id))
        );
    }
}
