package it.com.demo.repository;

import it.com.demo.model.Author;
import it.com.demo.model.Quote;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
class QuoteRepositoryTest {

    @Autowired
    private QuoteRepository underTest;

/*    @Test
    void checkIfExistsByQuote() {
        //given
        Author author = new Author("gianni");
        Quote quote = new Quote("hello", author);
        underTest.save(quote);
        //when
        Boolean exist = underTest.existsByQuote("hello");
        //then
        assertThat(exist).isTrue();

    }*/
}