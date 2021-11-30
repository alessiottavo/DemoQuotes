package it.com.demo.repository;

import it.com.demo.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AuthorRepository extends JpaRepository<Author, Long> {

    /**
     * Return true if author with input name exists.
     */
    boolean existsByNameAndSurname(String authorName, String authorSurname);

    /**
     * Returns an Author with input name.
     */
    Author findByNameAndSurname(String name,String surname);
}
