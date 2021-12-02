package it.com.demo.service;

import it.com.demo.exception.QuoteException;
import it.com.demo.model.Author;
import it.com.demo.model.Quote;
import it.com.demo.model.ResponseModel;
import it.com.demo.repository.AuthorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
class AuthorServiceTest {
    @MockBean
    AuthorRepository authorRepository;

    @Autowired
    AuthorService service;

    private Author author;

    @BeforeEach
    public void setUp() {
        this.author = new Author(1L,"name", "surname", LocalDate.now(), LocalDate.now(),"Mr.","Worker", "www.site.com");
    }


    @Test
    void getAllAuthorsWhenFull() throws QuoteException {
        List<Author> authorList = new ArrayList<>();
        authorList.add(author);
        when(authorRepository.findAll()).thenReturn(authorList);
        assertEquals(service.getAllAuthors("", ""), ResponseModel.generateResponse("GetSuccess", HttpStatus.FOUND, authorList));
    }

    @Test
    void getAllAuthorsWhenEmpty() {
        assertThrows(QuoteException.class, ()->{
            service.getAllAuthors("","");
        });
    }

    @Test
    void patchAuthor() throws QuoteException {
        when(authorRepository.existsById(1L)).thenReturn(true);
        when(authorRepository.findById(1L)).thenReturn(Optional.of(author));
        assertEquals(service.patchAuthor(1L, author),ResponseModel.generateResponse("PatchSuccess", HttpStatus.ACCEPTED, author));
    }

    @Test
    void patchAuthorWhenDifferent() throws QuoteException {
        Author newAuthor = new Author(1L,"name2", "surname2", LocalDate.now(), LocalDate.now(),"Dr.","Worker2", "www.site2.com");
        when(authorRepository.existsById(1L)).thenReturn(true);
        when(authorRepository.findById(1L)).thenReturn(Optional.of(author));
        assertEquals(service.patchAuthor(1L, newAuthor),ResponseModel.generateResponse("PatchSuccess", HttpStatus.ACCEPTED, newAuthor));
    }


    @Test
    void patchAuthorWhenAbsent() {

        when(authorRepository.existsById(1L)).thenReturn(false);
        assertThrows(QuoteException.class, ()->{
            service.patchAuthor(1L, author);
        });
    }

    @Test
    void delAuthor() throws QuoteException {
        when(authorRepository.existsById(1L)).thenReturn(true);
        assertEquals(service.delAuthor(1L), ResponseModel.generateResponse("DeleteSuccess", HttpStatus.ACCEPTED, 1L));
    }

    @Test
    void delAuthorWhenAbsent() {
        when(authorRepository.existsById(1L)).thenReturn(false);
        assertThrows(QuoteException.class, ()->{
            service.delAuthor(1L);
        });
    }
}