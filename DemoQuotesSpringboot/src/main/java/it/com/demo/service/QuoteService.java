package it.com.demo.service;

import it.com.demo.exception.QuoteException;
import it.com.demo.model.*;
import it.com.demo.repository.AuthorRepository;
import it.com.demo.repository.QuoteRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;


@Service
public class QuoteService {

    private static final Logger logger = LogManager.getLogger(QuoteService.class);

    @Autowired
    QuoteRepository quoteRepository;
    @Autowired
    AuthorRepository authorRepository;

    /**
     * First checks for quote redundancy, then saves the new quote correctly.
     *
     * @throws QuoteException
     */
    public ResponseEntity<Object> addQuote(Quote quote) throws QuoteException {
        checkQuoteRedundancy(quote);
        quoteRepository.save(addToAuthor(quote));
        logger.info("POST method returned");
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
        if (authorRepository.existsByNameAndSurname(quote.getAuthor().getName(), quote.getAuthor().getSurname())) {
            quote.setAuthor(authorRepository.findByNameAndSurname(quote.getAuthor().getName(), quote.getAuthor().getSurname()));
        }
        return quote;
    }

    /**
     * Obtains all records from the DB, or all records containing keyword.
     *
     * @throws QuoteException
     */
    public ResponseEntity<Object> getAllQuotes(String key) throws QuoteException {
        List<Quote> quotesMatch = quoteRepository.findAll().stream().filter(q -> q.getQuote().contains(key)).collect(Collectors.toList());
        if (quotesMatch.isEmpty()) throw new QuoteException(ErrorCodes.QUOTE_NOT_FOUND);
        logger.info("GET method returned");
        return ResponseModel.generateResponse("GetSuccess", HttpStatus.FOUND, quotesMatch);
    }

    /**
     * Checks if there is a quote for the input ID, then changes the quote.
     *
     * @throws QuoteException
     */
    public ResponseEntity<Object> putQuote(Long id, Quote quote) throws QuoteException {
        checkIfQuoteExists(id);
        quoteRepository.findById(id).get().setQuote(quote.getQuote());
        quoteRepository.findById(id).get().setAuthor(quote.getAuthor());
        logger.info("PUT method returned");
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
        checkIfQuoteExists(id);
        Quote newQuote = quoteRepository.findById(id).get();
        newQuote.setQuote(quote.getQuote());
        logger.info("PATCH method returned");
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
        logger.info("DEL method returned");
        return ResponseModel.generateResponse("DeleteSuccess", HttpStatus.ACCEPTED, id);
    }

    /**
     * Obtains all quotes from a single author.
     *
     * @throws QuoteException
     */
    public ResponseEntity<Object> getAllQuotesFromAuthor(Long authorId) throws QuoteException {
        Set<Quote> quotes = quoteRepository.findAllByAuthorId(authorId);
        if(quotes.isEmpty()) throw new QuoteException(ErrorCodes.QUOTE_NOT_FOUND);
        logger.info("GET method returned");
        return ResponseModel.generateResponse("GetSuccess", HttpStatus.FOUND, quotes);
    }

    /**
     * Provides a list of quotes ranked by size with mode:"size"<br>
     * Provides a list of authors with the nearest birthday with mode:"birthday"
     *
     * @throws QuoteException
     */
    public ResponseEntity<Object> getRankings(String key) throws QuoteException {
        List<Quote> allQuotes = quoteRepository.findAll();
        if(allQuotes.isEmpty()) throw new QuoteException(ErrorCodes.QUOTE_NOT_FOUND);
        switch (key){
            case "size":{
                return rankBiggerToSmaller(allQuotes);
            }
            case "birthday":{
                return rankAuthorByClosestBirthday(authorRepository.findAll());
            }
            default:{
                return ResponseModel.generateResponse("No ranking method provided", HttpStatus.BAD_REQUEST, null);
            }
        }
    }

    /**
     * Provides a list of authors with the nearest birthday
     * @param allAuthor
     * @return
     */
    private ResponseEntity<Object> rankAuthorByClosestBirthday(List<Author> allAuthor) {
        LocalDate today = LocalDate.now();
        List<Author> rankedByClosestDate = allAuthor.stream()
                .sorted(Comparator.comparingInt(a->Math.abs(a.getDateOfBirth().getDayOfMonth()-today.getDayOfMonth()))) //In order to get the closest date, first i order by closest day and then by closest month
                .sorted(Comparator.comparingInt(a-> Math.abs(a.getDateOfBirth().getMonthValue()-today.getMonthValue())))
                .collect(Collectors.toList());
        return ResponseModel.generateResponse("ranked by month", HttpStatus.ACCEPTED, rankedByClosestDate);
    }

    /**
     * Provides a list of quotes ranked by size
     * @param allQuotes
     * @return
     */
    private ResponseEntity<Object> rankBiggerToSmaller(List<Quote> allQuotes){
        List<String> rankedQuotes = allQuotes.stream()
                .map(Quote::getQuote)
                .sorted()
                .collect(Collectors.toList());
        return ResponseModel.generateResponse("Get Success", HttpStatus.FOUND, rankedQuotes);

    }
}
