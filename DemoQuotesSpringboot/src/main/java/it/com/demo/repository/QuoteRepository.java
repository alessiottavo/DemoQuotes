package it.com.demo.repository;

import it.com.demo.model.Author;
import it.com.demo.model.Quote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface QuoteRepository extends JpaRepository<Quote, Long> {

    /**
     * returns true if Quote exists.
     */
    boolean existsByQuote(String quote);

    /**
     * returns a Set containing all Quotes with input author_id.
     */
    Set<Quote> findAllByAuthorId(Long id);

}
