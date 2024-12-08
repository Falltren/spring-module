package com.fallt.spring_data_jdbc.repository.impl;

import com.fallt.spring_data_jdbc.domain.entity.Book;
import com.fallt.spring_data_jdbc.mapper.BookRowMapper;
import com.fallt.spring_data_jdbc.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.ArgumentPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapperResultSetExtractor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class BookRepositoryImpl implements BookRepository {

    private final JdbcTemplate jdbcTemplate;

    public void save(Book book) {
        String sql = "INSERT INTO books (title, author, publication_year) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, book.getTitle(), book.getAuthor(), book.getPublicationYear());
    }

    public void update(Book book) {
        String sql = "UPDATE books SET title=?, author=?, publication_year=? WHERE id=?";
        jdbcTemplate.update(sql, book.getTitle(), book.getAuthor(), book.getPublicationYear(), book.getId());
    }

    public void deleteById(Long id) {
        String sql = "DELETE FROM books WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    public Optional<Book> findById(Long id) {
        String sql = "SELECT * FROM books WHERE id = ?";
        Book contact = DataAccessUtils.singleResult(
                jdbcTemplate.query(
                        sql,
                        new ArgumentPreparedStatementSetter(new Object[]{id}),
                        new RowMapperResultSetExtractor<>(new BookRowMapper(), 1)
                )
        );
        return Optional.ofNullable(contact);
    }

    public List<Book> findAll() {
        String sql = "SELECT * FROM books ORDER BY id";
        return jdbcTemplate.query(sql, new BookRowMapper());
    }
}
