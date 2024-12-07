package com.fallt.pageable.controller;

import com.fallt.pageable.domain.dto.request.BookFilter;
import com.fallt.pageable.domain.dto.request.UpsertBookRequest;
import com.fallt.pageable.domain.dto.response.BookResponse;
import com.fallt.pageable.domain.dto.response.SuccessResponse;
import com.fallt.pageable.service.BookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/books")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @GetMapping("/{id}")
    public BookResponse getBook(@PathVariable Long id) {
        return bookService.getBookById(id);
    }

    @GetMapping
    public List<BookResponse> getAll(Pageable pageable) {
        return bookService.getAll(pageable);
    }

    @GetMapping("/filter")
    public List<BookResponse> getAllByFilter(@ModelAttribute BookFilter bookFilter) {
        return bookService.filterBy(bookFilter);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SuccessResponse create(@RequestBody @Valid UpsertBookRequest request) {
        return bookService.create(request);
    }

    @PutMapping("/{id}")
    public SuccessResponse update(@PathVariable Long id, @RequestBody UpsertBookRequest request) {
        return bookService.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        bookService.delete(id);
    }
}
