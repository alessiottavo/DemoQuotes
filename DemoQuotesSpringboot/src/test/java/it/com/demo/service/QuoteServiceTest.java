/*
package it.com.demo.service;

import it.com.demo.exception.QuoteException;
import it.com.demo.model.*;
import it.com.demo.repository.QuoteRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class QuoteServiceTest {

    @MockBean
    QuoteRepository mockRepository;

    @Autowired
    QuoteService underTest;

    private static final Author author = new Author("nome");
    private static final Quote quote = new Quote(1L,"quote text", author);



    @Test
    void getAllQuotesWhenPresent() throws QuoteException {
        List<Quote> quotesList = new ArrayList<>();
        quotesList.add(quote);
        when(mockRepository.findAll()).thenReturn(quotesList);
        assertEquals(underTest.getAllQuotes(), ResponseModel.generateResponse("GetSuccess", HttpStatus.FOUND, quotesList));
    }

    @Test
    void getAllQuotesWhenVoid() {
        List<Quote> quotesList = new ArrayList<>();
        when(mockRepository.findAll()).thenReturn(quotesList);
        assertThrows(QuoteException.class, () -> {
            underTest.getAllQuotes();
        });
    }

    @Test
    void addQuoteWhenCorrect() throws QuoteException {
        assertEquals(underTest.addQuote(quote), ResponseModel.generateResponse("PostSuccess", HttpStatus.CREATED, quote));
    }

    @Test
    void addQuoteWhenRedundant() {
        when(mockRepository.existsByQuote(quote.getQuote())).thenReturn(true);
        assertThrows(QuoteException.class, () -> {
            underTest.addQuote(quote);
        });
    }
*/
/*    @Test(expected = RepositoryException.class)
    public void testException() throws RepositoryException {
        ErrorResponse err = new ErrorResponse(ErrorType.EMPTY_REPOSITORY.value(), ErrorType.EMPTY_REPOSITORY.getMessage());
        ResponseEntity resp =  new ResponseEntity<>(err, HttpStatus.NOT_FOUND);
        Mockito.when(libraryService.getAllMovies()).thenThrow(RepositoryException.class);
        Assert.assertEquals(movieController.getAllMovies(), resp);
    }    *//*

    @Test
    void putQuoteWhenPresent() throws QuoteException {
        when(mockRepository.existsById(any())).thenReturn(true);
        doReturn(Optional.of(quote)).when(mockRepository).findById(any());
        assertEquals(underTest.putQuote(1L ,quote),ResponseModel.generateResponse("PutSuccess", HttpStatus.ACCEPTED, quote));
    }

    @Test
    @Disabled
    void patchQuote() {
    }

    @Test
    @Disabled
    void delQuote() {
    }

    @Test
    @Disabled
    void getAllQuotesFromAuthor() {
    }
}*/
