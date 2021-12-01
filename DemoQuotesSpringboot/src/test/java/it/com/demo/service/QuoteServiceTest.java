package it.com.demo.service;

import it.com.demo.exception.QuoteException;
import it.com.demo.model.Author;
import it.com.demo.model.Quote;
import it.com.demo.model.ResponseModel;
import it.com.demo.repository.AuthorRepository;
import it.com.demo.repository.QuoteRepository;
import org.assertj.core.internal.bytebuddy.dynamic.DynamicType;
import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import java.time.LocalDate;
import java.util.*;

import static java.util.Optional.of;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
class QuoteServiceTest {

    @Mock
    QuoteRepository quoteRepository;
    @Mock
    AuthorRepository authorRepository;

    @InjectMocks
    QuoteService service;

    private Author author;
    private Quote quote;
    @BeforeEach
    void setUp() {
        this.author = new Author(1L,"name", "surname", LocalDate.now(), LocalDate.now(),"Mr.","Worker", "www.site.com");
        this.quote = new Quote(1L, "text","Origin", LocalDate.now(), author);
        Mockito.reset();
    }

    @Test
    void addQuoteWhenOriginal() throws QuoteException {
        when(authorRepository.existsByNameAndSurname(any(),any())).thenReturn(false);
        assertEquals(service.addQuote(quote), ResponseModel.generateResponse("PostSuccess", HttpStatus.CREATED, quote));
    }

    @Test
    void addQuoteWhenNotOriginal() {
        when(quoteRepository.existsByQuote(quote.getQuote())).thenReturn(true);
        assertThrows(QuoteException.class, () -> {
            service.addQuote(quote);
        });
    }


    @Test
    void getAllQuotesWhenPresent() throws QuoteException {
        List<Quote> quotes = new ArrayList<>();
        quotes.add(quote);
        when(quoteRepository.findAll()).thenReturn(quotes);
        assertEquals(service.getAllQuotes(""), ResponseModel.generateResponse("GetSuccess", HttpStatus.FOUND, quotes));
    }

    @Test
    void getAllQuotesWhenAbsent() {
        List<Quote> quotes = new ArrayList<>();
        when(quoteRepository.findAll()).thenReturn(quotes);
        assertThrows(QuoteException.class, ()->{
            service.getAllQuotes("");
        });
    }

    @Test

    void putQuote() throws QuoteException {
        when(quoteRepository.existsById(any())).thenReturn(true);
        when(quoteRepository.findById(any())).thenReturn(Optional.of(quote));
        assertEquals(service.putQuote(any(),quote), ResponseModel.generateResponse("PutSuccess", HttpStatus.ACCEPTED, quote));
    }

    @Test
    void patchQuote() throws QuoteException {
        when(quoteRepository.existsById(1L)).thenReturn(true);
        when(quoteRepository.findById(1L)).thenReturn(Optional.of(quote));
        assertEquals(service.patchQuote(1L,quote), ResponseModel.generateResponse("PatchSuccess", HttpStatus.ACCEPTED, quote));
    }

    @Test
    void delQuote() throws QuoteException {
        when(quoteRepository.existsById(any())).thenReturn(true);
        assertEquals(service.delQuote(any()), ResponseModel.generateResponse("DeleteSuccess", HttpStatus.ACCEPTED, any()));
    }

    @Test
    void getAllQuotesFromAuthor() throws QuoteException {
        Set<Quote> quoteSet = new HashSet<>();
        quoteSet.add(quote);
        when(quoteRepository.findAllByAuthorId(any())).thenReturn(quoteSet);
        assertEquals(service.getAllQuotesFromAuthor(any()), ResponseModel.generateResponse("GetSuccess", HttpStatus.FOUND, quoteSet));
    }
    @Test
    @Disabled
    void getAllQuotesFromAuthorWhenEmpty(){
        Set<Quote> quoteSet = new HashSet<>();
        when(quoteRepository.findAllByAuthorId(1L)).thenReturn(quoteSet);
        assertThrows(QuoteException.class, ()->{
            service.getAllQuotesFromAuthor(1L);
        });
    }

    @Test
    void getRankingsWhenSize() throws QuoteException {
/*        Author author = new Author(1L,"name", "surname", LocalDate.now(), LocalDate.now(),"Mr.","Worker", "www.site.com");
        Quote quote = new Quote(1L, "text","Origin", LocalDate.now(), author);*/
        List<Quote> quotes = new ArrayList<>();
        quotes.add(quote);
        List<String> rquotes = new ArrayList<>();
        rquotes.add(quotes.get(0).getQuote());
        when(quoteRepository.findAll()).thenReturn(quotes);
        assertEquals(service.getRankings("size"), ResponseModel.generateResponse("Get Success", HttpStatus.FOUND, rquotes));
    }

    @Test
    void getRankingsWhenBirthday() throws QuoteException {
        Set<Quote> quotes = new HashSet<>();
        List<Quote> quoteList = new ArrayList<>();
        List<Author> ranked = new ArrayList<>();
        quotes.add(quote);
        quoteList.add(quote);
        author.setQuotes(quotes);
        ranked.add(author);
        when(quoteRepository.findAll()).thenReturn(quoteList);
        when(authorRepository.findAll()).thenReturn(ranked);
        assertEquals(service.getRankings("birthday"), ResponseModel.generateResponse("ranked by month", HttpStatus.ACCEPTED, ranked));
    }

    @Test
    void getRankingsWhenDefault() {
        assertThrows(QuoteException.class, ()->{
            service.getRankings("");
        });
    }
}