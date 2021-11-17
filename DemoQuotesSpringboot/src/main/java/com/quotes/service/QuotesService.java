package com.quotes.service;

import com.quotes.exception.QuoteException;
import com.quotes.model.ErrorCodes;
import com.quotes.model.QuotesElement;
import com.quotes.model.ResponseModel;
import com.quotes.repository.QuotesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


@Service
public class QuotesService {

    @Autowired
    QuotesRepository repository;

    /**
     * If present, returns QuotesElement of id inside of response body.
     *
     * @return ResponseEntity
     */
    public ResponseEntity<Object> getQuote(Integer id) throws QuoteException {
        checkIfKeyPresent(id, 1);
        return ResponseModel.generateResponse("GetRequestSuccess", HttpStatus.FOUND, repository.get(id));
    }

    /**
     * Checks if the Map is empty.<br>
     * If not, returns all QuotesElement (in Response body)<br>
     * if it is, return an error.
     *
     * @return ResponseEntity
     */
    public ResponseEntity<Object> getAllQuotes() throws QuoteException {
        checkIfKeyPresent(0, 1);
        return ResponseModel.generateResponse("GetRequestSuccess", HttpStatus.FOUND, repository.getAll());
    }

    /**
     * Saves one QuotesElement to the Map.
     *
     * @return ResponseEntity
     */
    public ResponseEntity<Object> addQuote(QuotesElement newQuote) throws QuoteException {
        validateQuotesElement(newQuote);
        checkIfValuePresent(newQuote, 0);
        repository.addQuote(newQuote);
        return ResponseModel.generateResponse("ElementSuccessfulyAdded", HttpStatus.CREATED, newQuote);
    }

    /**
     * Checks if QuotesElement is present, if true changes it with new QuotesElement
     *
     * @return ResponseEntity
     */

    public ResponseEntity<Object> putQuote(Integer id, QuotesElement newQuote) throws QuoteException {
        validateQuotesElement(newQuote);
        checkIfKeyPresent(id, 1);
        checkIfValuePresent(newQuote, 0);
        repository.putQuote(id, newQuote);
        return ResponseModel.generateResponse("PutRequestSuccess", HttpStatus.OK, newQuote);
    }

    /**
     * Checks if the QuotesElement is present, if true changes the correct filed in the QuotesElement
     *
     * @return ResponseEntity
     */
    public ResponseEntity<Object> fixQuote(Integer id, QuotesElement newQuote) throws QuoteException {
        checkIfKeyPresent(id, 1);
        repository.fixQuote(id, newQuote);
        return ResponseModel.generateResponse("PatchRequestSuccess", HttpStatus.OK, newQuote);
    }

    /**
     * If the QuotesElement is presents it gets deleted
     *
     * @return ResponseEntity
     */
    public ResponseEntity<Object> delQuote(int id) throws QuoteException {
        checkIfKeyPresent(id, 1);
        repository.remove(id);
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
     * Checks for the presence of input ID as Key in Map.<br>
     * Use mode: 0 to throw QuoteAlreadyThereException<br>
     * Uso mode: 1 to throw QuoteNotFoundException
     */
    private void checkIfKeyPresent(Integer id, Integer mode) throws QuoteException {
        switch (mode) {
            case 1:
                if (!repository.isThere(id)) {
                    throw new QuoteException(ErrorCodes.QUOTE_NOT_FOUND);
                }
                break;
            case 0:
                if (repository.isThere(id)) {
                    throw new QuoteException(ErrorCodes.QUOTE_ALREADY_PRESENT);
                }
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + mode);
        }
    }

    /**
     * Checks for the presence of input QuotesElement as Value in Map<br>
     * Use mode: 0 to throw QuoteAlreadyPresentException<br>
     * use mode: 1 to throw QuoteNotFoundException
     */
    private void checkIfValuePresent(QuotesElement checkedElement, Integer mode) throws QuoteException {
        switch (mode) {
            case 0:
                if (repository.containsQuoteElement(checkedElement)) {
                    throw new QuoteException(ErrorCodes.QUOTE_ALREADY_PRESENT);
                }
                break;
            case 1:
                if (!repository.containsQuoteElement(checkedElement)) {
                    throw new QuoteException(ErrorCodes.QUOTE_NOT_FOUND);
                }
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + mode);
        }
    }

    /**
     * Checks for the presence of the author filed in Map repository.
     * if present, returns all quotes as string.
     *
     * @return ResponseEntity
     */
    public ResponseEntity<Object> getQuoteFrom(String author) throws QuoteException {
        isThereAuthor(author);
        return ResponseModel.generateResponse("GetRequestSuccessful", HttpStatus.FOUND, repository.getAllFromAuthor(author).stream().toString());
    }

    /**
     * Checks for the presence on an author, throws exception if absent
     */
    private void isThereAuthor(String author) throws QuoteException {
        if (!repository.isThereAuthor(author)) {
            throw new QuoteException(ErrorCodes.QUOTE_NOT_FOUND);
        }
    }
}

