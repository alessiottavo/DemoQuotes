package it.com.demo.service;

import it.com.demo.exception.QuoteException;
import it.com.demo.model.Author;
import it.com.demo.model.ErrorCodes;
import it.com.demo.model.Quote;
import it.com.demo.model.ResponseModel;
import it.com.demo.repository.QuoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Set;


@Service
public class QuoteService {

    @Autowired
    QuoteRepository quoteRepository;
    @Autowired
    AuthorService authorService;

    /**
     * First checks for quote redundancy, then saves the new quote correctly.
     *
     * @throws QuoteException
     */
    public ResponseEntity<Object> addQuote(Quote quote) throws QuoteException {
        //validateQuote(quote);
        checkQuoteRedundancy(quote);
        quoteRepository.save(addToAuthor(quote));
        return ResponseModel.generateResponse("PostSuccess", HttpStatus.CREATED, quote);
    }

    /**
     * checks for the presense of a quote in the DB.
     *
     * @throws QuoteException
     */
    private void checkQuoteRedundancy(Quote quote) throws QuoteException {
        if (quoteRepository.existsByQuote(quote.getQuote())) throw new QuoteException(ErrorCodes.QUOTE_ALREADY_PRESENT);
    }

    /**
     * Prevents the duplication of authors in the DB.
     *
     * @return
     */
    private Quote addToAuthor(Quote quote) {
        if (authorService.repository.existsByName(quote.getAuthor().getName())) {
            quote.setAuthor(authorService.repository.findByName(quote.getAuthor().getName()));
        }
        return quote;
    }

    /**
     * Obtains all records from the DB.
     *
     * @throws QuoteException
     */
    public ResponseEntity<Object> getAllQuotes() throws QuoteException {
        if (quoteRepository.findAll().isEmpty()) throw new QuoteException(ErrorCodes.QUOTE_NOT_FOUND);
        return ResponseModel.generateResponse("GetSuccess", HttpStatus.FOUND, quoteRepository.findAll());
    }

    /**
     * Checks if there is a quote for the input ID, then changes the quote.
     *
     * @throws QuoteException
     */
    public ResponseEntity<Object> putQuote(Long id, Quote quote) throws QuoteException {
        //ValidateQuote(quote);
        checkIfQuoteExists(id);
        quoteRepository.findById(id).get().setQuote(quote.getQuote());
        quoteRepository.findById(id).get().setAuthor(quote.getAuthor());
        return ResponseModel.generateResponse("PutSuccess", HttpStatus.ACCEPTED, quote);
    }

    /**
     * checks for the presence of a quote.
     *
     * @param id
     * @throws QuoteException
     */
    private void checkIfQuoteExists(Long id) throws QuoteException {
        if (!quoteRepository.existsById(id)) throw new QuoteException(ErrorCodes.QUOTE_NOT_FOUND);
    }

    /**
     * checks for the presence of a quote, then does a patch operation.
     *
     * @throws QuoteException
     */
    public ResponseEntity<Object> patchQuote(Long id, Quote quote) throws QuoteException {
        //validateQuote(quote.getQuote());
        checkIfQuoteExists(id);
        Quote newQuote = quoteRepository.findById(id).get();
        newQuote.setQuote(quote.getQuote());
        return ResponseModel.generateResponse("PatchSuccess", HttpStatus.ACCEPTED, newQuote);
    }

    /**
     * Deletes a quote from the DB.
     *
     * @throws QuoteException
     */
    public ResponseEntity<Object> delQuote(Long id) throws QuoteException {
        checkIfQuoteExists(id);
        quoteRepository.deleteById(id);
        return ResponseModel.generateResponse("DeleteSuccess", HttpStatus.ACCEPTED, id);
    }

    /**
     * Obtains all quotes from a single author.
     *
     * @throws QuoteException
     */
    public ResponseEntity<Object> getAllQuotesFromAuthor(Long authorId) throws QuoteException {
        authorService.checkIfAuthorPresent(authorId);
        Set<Quote> quotes = quoteRepository.findAllByAuthorId(authorId);
        return ResponseModel.generateResponse("GetSuccess", HttpStatus.FOUND, quotes);
    }
}
