package com.fallt.spring_data_jdbc.repository;

import com.fallt.spring_data_jdbc.domain.entity.Book;

import java.util.List;
import java.util.Optional;

public interface BookRepository {

    void save(Book book);

    void update(Book book);

    void deleteById(Long id);

    Optional<Book> findById(Long id);

    List<Book> findAll();
}
