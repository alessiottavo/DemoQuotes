package it.com.demo.repository;

import it.com.demo.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author, Long> {

    /**
     * Return true if author with input name exists.
     */
    boolean existsByName(String authorName);

    /**
     * Returns an Author with input name.
     */
    Author findByName(String name);

}
