package it.com.demo.service;

import it.com.demo.exception.QuoteException;
import it.com.demo.model.Author;
import it.com.demo.model.ErrorCodes;
import it.com.demo.model.ResponseModel;
import it.com.demo.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class AuthorService {
    @Autowired
    AuthorRepository repository;

    /**
     * Checks if the DB is empty, if not returns all authors.
     *
     * @throws QuoteException
     */
    public ResponseEntity<Object> getAllAuthors() throws QuoteException {
        if (repository.findAll().isEmpty()) throw new QuoteException(ErrorCodes.QUOTE_NOT_FOUND);
        return ResponseModel.generateResponse("GetSuccess", HttpStatus.FOUND, repository.findAll());
    }

    /**
     * Validates the Author in input, then checks for author presence and saves.
     *
     * @throws QuoteException
     */
    public ResponseEntity<Object> patchAuthor(Long authorId, Author author) throws QuoteException {
        //validateAuthor(author);
        checkIfAuthorPresent(authorId);
        Author newAuthor = repository.findById(authorId).get();
        newAuthor.setName(author.getName());
        repository.save(newAuthor);
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