package it.com.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;

@JsonIgnoreProperties({"hibernateLazyInitializer"})
@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Table(name = "Quote")
public class Quote {

    @Id
    @SequenceGenerator(name = "quote_sequence", sequenceName = "quote_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "quote_sequence")
    private Long quoteId;

    @Column(nullable = false,
            columnDefinition = "LONGTEXT")
    private String quote;
    @Column
    private String origin;
    @Column(name = "date_of_quote")
    private LocalDate dateOfQuote;

    @JoinColumn(
            name = "author_id",
            referencedColumnName = "id"
    )
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Author author;
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Book book;

    public Quote(Long id, String quote_body, Author author) {
        this.quote = quote_body;
        this.author = author;
        this.quoteId = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Quote quote = (Quote) o;
        return quoteId != null && Objects.equals(quoteId, quote.quoteId);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
    public Quote(Long quoteId, String quote, String origin, LocalDate dateOfQuote, Author author) {
        this.quoteId = quoteId;
        this.quote = quote;
        this.origin = origin;
        this.dateOfQuote = dateOfQuote;
        this.author = author;
    }
}
