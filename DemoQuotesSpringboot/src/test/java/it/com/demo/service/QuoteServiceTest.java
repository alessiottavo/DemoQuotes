package it.com.demo.service;

import it.com.demo.exception.QuoteException;
import it.com.demo.repository.QuoteRepository;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;

class QuoteServiceTest {

    @Mock
    private QuoteRepository quoteRepository;
    private AutoCloseable closeable;
    @InjectMocks
    private QuoteService underTest;

    @BeforeEach
    void setUp() {
        closeable = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void tearDown() throws Exception {
        closeable.close();
    }
//this doesnt work!
/*    @Test
    void canGetAllQuotes() throws QuoteException {
        //when
        underTest.getAllQuotes();
        //then
        verify(quoteRepository).findAll();
    }*/
}