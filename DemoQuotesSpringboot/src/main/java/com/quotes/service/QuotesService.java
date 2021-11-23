package com.quotes.service;

import com.quotes.exception.QuoteException;
import com.quotes.model.ErrorCodes;
import com.quotes.model.QuotesElement;
import com.quotes.model.ResponseModel;
import com.quotes.repository.QuotesDBRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;


@Service
public class QuotesService {

    @Autowired
    QuotesDBRepository repository;

    /**
     * If present, returns QuotesElement of id inside of response body.
     *
     * @return ResponseEntity
     */
    public ResponseEntity<Object> getQuote(Long id) throws QuoteException {
        checkIfKeyPresent(id);
        return ResponseModel.generateResponse("GetRequestSuccess", HttpStatus.FOUND, repository.findById(id));
    }

    /**
     * Checks if the DB is empty.<br>
     * If not, returns all QuotesElement (in Response body)<br>
     * if empty, returns an error.
     *
     * @return ResponseEntity
     */
    public ResponseEntity<Object> getAllQuotes() throws QuoteException {
        if (!repository.findById(1L).isPresent()) throw new QuoteException(ErrorCodes.QUOTE_NOT_FOUND);
        return ResponseModel.generateResponse("GetRequestSuccess", HttpStatus.FOUND, repository.findAll());
    }

    /**
     * Checks for both validity and non redundancy of input QuotesElement.<br>
     * Saves one QuotesElement to the DB.
     *
     * @return ResponseEntity
     */
    public ResponseEntity<Object> addQuote(QuotesElement newQuote) throws QuoteException {
        validateQuotesElement(newQuote);
        checkIfValuePresent(newQuote);
        repository.save(newQuote);
        return ResponseModel.generateResponse("ElementSuccessfulyAdded", HttpStatus.CREATED, newQuote);
    }

    /**
     * Checks if QuotesElement is present, if true changes it with new QuotesElement
     *
     * @return ResponseEntity
     */
    public ResponseEntity<Object> putQuote(Long id, QuotesElement newQuote) throws QuoteException {
        validateQuotesElement(newQuote);
        checkIfValuePresent(newQuote);
        try {
            QuotesElement a = repository.findById(id).get();
            a.setAuthor(newQuote.getAuthor());
            a.setQuote(newQuote.getQuote());
            repository.save(a);
        } catch (NoSuchElementException e) {
            throw new QuoteException(ErrorCodes.QUOTE_NOT_FOUND);
        }
        return ResponseModel.generateResponse("PutRequestSuccess", HttpStatus.OK, newQuote);
    }

    /**
     * Checks if the QuotesElement is present, if true changes the correct field in the QuotesElement
     *
     * @return ResponseEntity
     */
    public ResponseEntity<Object> fixAuthor(Long id, String newAuthor) throws QuoteException {
        checkIfKeyPresent(id);
        QuotesElement a = repository.findById(id).get();
        a.setAuthor(newAuthor);
        repository.save(a);
        return ResponseModel.generateResponse("PatchRequestSuccess", HttpStatus.OK, newAuthor);
    }

    /**
     * Checks if the QuotesElement is present, if true changes the correct field in the QuotesElement
     *
     * @return ResponseEntity
     */
    public ResponseEntity<Object> fixQuote(Long id, String newQuote) throws QuoteException {
        checkIfKeyPresent(id);
        QuotesElement a = repository.findById(id).get();
        a.setQuote(newQuote);
        repository.save(a);
        return ResponseModel.generateResponse("PatchRequestSuccess", HttpStatus.OK, newQuote);
    }

    /**
     * Checks for the presence of the author filed in Map repository.
     * if present, returns all quotes as string.
     *
     * @return ResponseEntity
     */
    public ResponseEntity<Object> getQuoteFrom(String author) throws QuoteException {
        isThereAuthor(author);
        return ResponseModel.generateResponse("GetRequestSuccessful", HttpStatus.FOUND, repository.getQuotesElementByAuthor(author));
    }

    /**
     * If the QuotesElement is presents it gets deleted
     *
     * @return ResponseEntity
     */
    public ResponseEntity<Object> delQuote(long id) throws QuoteException {
        checkIfKeyPresent(id);
        repository.deleteById(id);
        return ResponseModel.generateResponse("ElementDeleted", HttpStatus.OK, null);
    }

    /**
     * QuotesElement validation checks if the object is correctly written
     */
    private void validateQuotesElement(QuotesElement o) throws QuoteException {
        if (!o.isCorrect()) {
            throw new QuoteException(ErrorCodes.QUOTE_BADLY_WRITTEN);
        }
    }

    /**
     * Checks for the presence of input ID as Key in DB.
     */
    private void checkIfKeyPresent(Long id) throws QuoteException {
        if (!repository.findById(id).isPresent()) {
            throw new QuoteException(ErrorCodes.QUOTE_NOT_FOUND);
        }
    }

    /**
     * Checks for the presence of input QuotesElement as Value in DB<br>
     */
    private void checkIfValuePresent(QuotesElement checkedElement) throws QuoteException {
        boolean result = false;
        for (QuotesElement next : repository.findAll()) {//create custom Query
            if (next.equals(checkedElement)) {
                result = true;
                break;
            }
        }
        if (result) throw new QuoteException(ErrorCodes.QUOTE_ALREADY_PRESENT);
    }

    /**
     * Checks for the presence on an author, throws exception if absent
     */
    private void isThereAuthor(String author) throws QuoteException {
        if (repository.getQuotesElementByAuthor(author).isEmpty())
            throw new QuoteException(ErrorCodes.QUOTE_BADLY_WRITTEN);
    }
}
