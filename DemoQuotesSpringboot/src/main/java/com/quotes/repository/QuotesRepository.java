package com.quotes.repository;

import com.quotes.model.QuotesElement;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.stream.Collectors;

@Repository
public class QuotesRepository {

    private final Map<Integer, QuotesElement> repository = new HashMap<>();

    /**
     * Returns QuoteElement of id if present.
     *
     * @return QuoteElement
     */
    public QuotesElement get(Integer id) {
        return repository.get(id);
    }

    /**
     * adds QuotesElement to Map
     */
    public void addQuote(QuotesElement newQuote) {
        repository.put(repository.size(), newQuote);
    }

    /**
     * return the complete Map
     *
     * @return "Map<Integer,QuotesElement>"
     */
    public Map<Integer, QuotesElement> getAll() {
        return repository;
    }

    /**
     * Changes QuotesElement of id
     */
    public void putQuote(Integer id, QuotesElement newQuote) {
        repository.get(id).setAuthor(newQuote.getAuthor());
        repository.get(id).setQuote(newQuote.getQuote());
    }

    /**
     * changes "author" or "quote" in QuotesElement of id
     */
    public void fixQuote(Integer id, QuotesElement fixQuote) {
        if (fixQuote.getAuthor() == null) {
            repository.get(id).setQuote(fixQuote.getQuote());
        } else {
            repository.get(id).setAuthor(fixQuote.getAuthor());
        }
    }

    /**
     * removes QuotesElement of id from Map
     */
    public void remove(Integer id) {
        repository.remove(id);
    }

    /**
     * returns true if an id is present
     *
     * @return boolean
     */
    public boolean isThere(Integer id) {
        return repository.containsKey(id);
    }

    /**
     * returns true if the Map contains the QuotesElement as Value.
     *
     * @return boolean
     */
    public boolean containsQuoteElement(QuotesElement searchedElement) {
        return repository.containsValue(searchedElement);
    }

    /**
     * return true if there is a QuoteElement with input author
     *
     * @return boolean
     */
    public boolean isThereAuthor(String author) {
        boolean result = false;
        for (QuotesElement e : repository.values()) {
            if (e.getAuthor().equals(author)) {
                result = true;
                break;
            }
        }
        return result;
    }

    /**
     * Returns a Set containing all QuotesElment elements from one author
     *
     * @return Set
     */
    public Set<QuotesElement> getAllFromAuthor(String author) {
        Set<QuotesElement> set;
        set = repository.values().stream()
                .filter(s -> s.getAuthor().equals(author))
                .collect(Collectors.toSet());
        return set;
    }
}
