package com.quotes.model;

import java.util.Objects;

public class QuotesElement {
    private String author;
    private String quote;

    public QuotesElement(String author, String quote) {
        this.author = author;
        this.quote = quote;
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
