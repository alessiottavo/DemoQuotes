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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;


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
     * Checks if the Map is empty.<br>
     * If not, returns all QuotesElement (in Response body)<br>
     * if it is, return an error.
     *
     * @return ResponseEntity
     */
    public ResponseEntity<Object> getAllQuotes() throws QuoteException {
        //long num = 0;
        //checkIfKeyPresent(num); //fixthis
        return ResponseModel.generateResponse("GetRequestSuccess", HttpStatus.FOUND, repository.findAll());
    }

    /**
     * Saves one QuotesElement to the Map.
     *
     * @return ResponseEntity
     */
    public ResponseEntity<Object> addQuote(QuotesElement newQuote) throws QuoteException {
        validateQuotesElement(newQuote);
        //checkIfValuePresent(newQuote);
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
        checkIfKeyPresent(id);
        checkIfValuePresent(newQuote);
        repository.findById(id).get().setAuthor(newQuote.getAuthor());
        repository.findById(id).get().setQuote(newQuote.getQuote());
        return ResponseModel.generateResponse("PutRequestSuccess", HttpStatus.OK, newQuote);
    }

    /**
     * Checks if the QuotesElement is present, if true changes the correct filed in the QuotesElement
     *
     * @return ResponseEntity
     */
    public ResponseEntity<Object> fixQuote(Long id, QuotesElement newQuote) throws QuoteException {
        checkIfKeyPresent(id);
        QuotesElement elementToFix = repository.findById(id).get();
        if (newQuote.getAuthor().equals("")) {
            repository.findById(id).get().setQuote(newQuote.getQuote());
        } else {
            repository.findById(id).get().setAuthor(newQuote.getAuthor());
        }
        return ResponseModel.generateResponse("PatchRequestSuccess", HttpStatus.OK, newQuote);
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
     * Checks for the presence of input ID as Key in Map.<br>
     * Use mode: 0 to throw QuoteAlreadyThereException<br>
     * Uso mode: 1 to throw QuoteNotFoundException
     */
    private void checkIfKeyPresent(Long id) throws QuoteException {
        if (!repository.findById(id).isPresent()) { //va in null
            throw new QuoteException(ErrorCodes.QUOTE_NOT_FOUND);
        }
    }

    /**
     * Checks for the presence of input QuotesElement as Value in Map<br>
     * Use mode: 0 to throw QuoteAlreadyPresentException<br>
     * use mode: 1 to throw QuoteNotFoundException
     */
    private void checkIfValuePresent(QuotesElement checkedElement) throws QuoteException {
        Iterator<QuotesElement> iterator = repository.findAll().iterator();
        boolean result = false;
        while (iterator.hasNext()) {
            QuotesElement next = iterator.next();
            if (next.equals(checkedElement)) {
                result = true;
            }
            iterator.next();
        }
        if (result) throw new QuoteException(ErrorCodes.QUOTE_ALREADY_PRESENT);
    }

    /**
     * Checks for the presence of the author filed in Map repository.
     * if present, returns all quotes as string.
     *
     * @return ResponseEntity
     */
    public ResponseEntity<Object> getQuoteFrom(String author) throws QuoteException {
        isThereAuthor(author);
        return ResponseModel.generateResponse("GetRequestSuccessful", HttpStatus.FOUND, getAllFromAuthor(author).stream().toString());
    }

    private List<QuotesElement> getAllFromAuthor(String author) {
        List<QuotesElement> list = new ArrayList<>();
        Iterator<QuotesElement> iterator = repository.findAll().iterator();
        while (iterator.hasNext()) {
            QuotesElement next = iterator.next();
            if (next.getAuthor().equals(author)) {
                list.add(next);
            }
            iterator.next();
        }
        return list;
    }

    /**
     * Checks for the presence on an author, throws exception if absent
     */
    private void isThereAuthor(String author) throws QuoteException {
        Iterator<QuotesElement> iterator = repository.findAll().iterator();
        boolean result = true;
        while (iterator.hasNext()) {
            QuotesElement next = iterator.next();
            if (next.getAuthor().equals(author)) {
                result = false;
            }
            iterator.next();
        }
        if (result) throw new QuoteException(ErrorCodes.QUOTE_BADLY_WRITTEN);
    }
}
