package com.fallt.pageable.repository.specification;

import com.fallt.pageable.domain.dto.request.BookFilter;
import com.fallt.pageable.domain.entity.Book;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;

public interface BookSpecification {

    static Specification<Book> withFilter(BookFilter filter) {
        return Specification.where(byGenre(filter.getGenre()))
                .and(byAuthor(filter.getAuthor()))
                .and(byPublicationDateAfter(filter.getFrom()))
                .and(byPublicationDateBefore(filter.getTo()));
    }

    static Specification<Book> byGenre(String genre) {
        return ((root, query, criteriaBuilder) -> {
            if (genre == null) {
                return null;
            }
            return criteriaBuilder.equal(root.get("genre"), genre);
        });
    }

    static Specification<Book> byAuthor(String author) {
        return ((root, query, criteriaBuilder) -> {
            if (author == null) {
                return null;
            }
            String[] parts = author.split(" ");
            String firstName = parts[0];
            if (parts.length == 1) {
                return criteriaBuilder.equal(root.get("author").get("firstName"), firstName);
            }
            if (parts.length != 2) {
                return null;
            }
            String lastName = parts[1];
            return criteriaBuilder.and(
                    criteriaBuilder.equal(root.get("author").get("firstName"), firstName),
                    criteriaBuilder.equal(root.get("author").get("lastName"), lastName)
            );
        });
    }

    static Specification<Book> byPublicationDateAfter(LocalDate from) {
        return ((root, query, criteriaBuilder) -> {
            if (from == null) {
                return null;
            }
            return criteriaBuilder.greaterThanOrEqualTo(root.get("publicationDate"), from);
        });
    }

    static Specification<Book> byPublicationDateBefore(LocalDate to) {
        return ((root, query, criteriaBuilder) -> {
            if (to == null) {
                return null;
            }
            return criteriaBuilder.lessThanOrEqualTo(root.get("publicationDate"), to);
        });
    }
}
