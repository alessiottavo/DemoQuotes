package it.com.demo.service;

import it.com.demo.exception.QuoteException;
import it.com.demo.model.Author;
import it.com.demo.model.ErrorCodes;
import it.com.demo.model.Quote;
import it.com.demo.model.ResponseModel;
import it.com.demo.repository.AuthorRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuthorService {

    private static final Logger logger = LogManager.getLogger(AuthorService.class);

    @Autowired
    AuthorRepository repository;

    /**
     * Checks if the DB is empty, if not returns all authors.
     *
     * @throws QuoteException
     */
    public ResponseEntity<Object> getAllAuthors(String name, String surname) throws QuoteException {
        List<Author> authorsMatch = repository.findAll().stream().filter(q -> q.getName().contains(name)).filter(q -> q.getSurname().contains(surname)).collect(Collectors.toList());
        if (authorsMatch.isEmpty()) throw new QuoteException(ErrorCodes.QUOTE_NOT_FOUND);
        logger.info("GET method retured");
        return ResponseModel.generateResponse("GetSuccess", HttpStatus.FOUND, authorsMatch);
    }

    /**
     * Validates the Author in input, then checks for author presence and saves.
     *
     * @throws QuoteException
     */
    public ResponseEntity<Object> patchAuthor(Long authorId, Author author) throws QuoteException {
        checkIfAuthorPresent(authorId);
        Author newAuthor = repository.findById(authorId).get();
        if(!author.getName().isEmpty()) newAuthor.setName(author.getName());
        if(!author.getSurname().isEmpty()) newAuthor.setSurname(author.getSurname());
        if(!author.getProfession().isEmpty()) newAuthor.setProfession(author.getProfession());
        if(!author.getTitle().isEmpty()) newAuthor.setTitle(author.getTitle());
        if(!(author.getDateOfBirth()==null)) newAuthor.setDateOfBirth(author.getDateOfBirth());
        if(!(author.getDateOfDeath()==null)) newAuthor.setDateOfDeath(author.getDateOfDeath());
        repository.save(newAuthor);
        logger.info("PATCH method retured");
        return ResponseModel.generateResponse("PatchSuccess", HttpStatus.ACCEPTED, newAuthor);
    }

    /**
     * Checks for the presence of an Author in the DB.
     *
     * @throws QuoteException
     */
    public void checkIfAuthorPresent(Long authorId) throws QuoteException {
        if (!repository.existsById(authorId)) throw new QuoteException(ErrorCodes.AUTHOR_NOT_FOUND);
    }

    /**
     * Checks for the presence of an Author, then deletes it.
     *
     * @throws QuoteException
     */
    public ResponseEntity<Object> delAuthor(Long id) throws QuoteException {
        checkIfAuthorPresent(id);
        repository.deleteById(id);
        return ResponseModel.generateResponse("DeleteSuccess", HttpStatus.ACCEPTED, id);
    }
}
