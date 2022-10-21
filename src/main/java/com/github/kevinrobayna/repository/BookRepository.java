package com.github.kevinrobayna.repository;

import com.github.kevinrobayna.model.Book;
import io.micronaut.context.annotation.Executable;
import io.micronaut.data.jdbc.annotation.JdbcRepository;
import io.micronaut.data.model.query.builder.sql.Dialect;
import io.micronaut.data.repository.CrudRepository;

import java.util.List;

@JdbcRepository(dialect = Dialect.POSTGRES)
public interface BookRepository extends CrudRepository<Book, Long> {
    @Executable
    Book find(String title);

    @Executable
    List<Book> findAllByTitle(String t);
}