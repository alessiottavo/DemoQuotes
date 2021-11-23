package com.quotes.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class QuotesElement {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;
    private String author;
    private String quote;

    public QuotesElement() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getQuote() {
        return quote;
    }

    public void setQuote(String quote) {
        this.quote = quote;
    }

    /**
     * Checks if QuoteElement is correctly given in input
     *
     * @return boolean
     */
    public boolean isCorrect() {
        try {
            return !this.author.equals("") && !this.quote.equals("");
        } catch (NullPointerException e) {
            System.err.println("Autore è nullo!");
            return false;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        QuotesElement that = (QuotesElement) o;
        return Objects.equals(author, that.author) && Objects.equals(quote, that.quote);
    }
}
