package com.github.kevinrobayna;

import com.github.kevinrobayna.model.Book;
import com.github.kevinrobayna.repository.BookRepository;
import io.micronaut.context.BeanContext;
import io.micronaut.data.annotation.Query;
import io.micronaut.runtime.EmbeddedApplication;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@MicronautTest
class JavaproTest {

    @Inject
    EmbeddedApplication<?> application;

    @Inject
    BeanContext beanContext;

    @Inject
    BookRepository bookRepository;

    @Test
    void testItWorks() {
        Assertions.assertTrue(application.isRunning());
    }


    @Test
    void testAnnotationMetadataFind() {
        String query = beanContext.getBeanDefinition(BookRepository.class)
                .getRequiredMethod("find", String.class)
                .getAnnotationMetadata().stringValue(Query.class)
                .orElse(null);

        // This query is what micronaut generates at compiled time! No need to actually run the service
        assertEquals("SELECT book_.\"id\",book_.\"title\",book_.\"pages\" FROM \"book\" book_ WHERE (book_.\"title\" = ?)", query);
    }

    @Test
    void testAnnotationMetadataSave() {
        String query = beanContext.getBeanDefinition(BookRepository.class)
                .getRequiredMethod("save", Book.class)
                .getAnnotationMetadata().stringValue(Query.class)
                .orElse(null);

        // This query is what micronaut generates at compiled time! No need to actually run the service
        assertEquals("INSERT INTO \"book\" (\"title\",\"pages\") VALUES (?,?)", query);
    }

    @Test
    void testRepositoryFindsData() {
        bookRepository.save(new Book("Java is cool", 1));

        Book book = bookRepository.find("Java is cool");

        assertNotNull(book);
        assertEquals(1, book.getPages());
        assertEquals("Java is cool", book.getTitle());
        assertEquals(1L, book.getId());
    }

}
