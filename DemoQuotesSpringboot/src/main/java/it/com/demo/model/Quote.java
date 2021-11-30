package it.com.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import javax.persistence.*;
import java.util.Date;

@JsonIgnoreProperties({"hibernateLazyInitializer"})
@Entity
@Data
@NoArgsConstructor
@Table(name = "Quote")
public class Quote {

    @Id
    @SequenceGenerator(name = "quote_sequence", sequenceName = "quote_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "quote_sequence")
    private Long quoteId;

    @Column(nullable = false)
    private String quote;
    @Column
    private String origin;
    @Column(name = "date_of_quote")
    private Date dateOfQuote;

    @JoinColumn(
            name = "author_id",
            referencedColumnName = "id"
    )
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Author author;

    public Quote(Long id, String quote_body, Author author) {
        this.quote = quote_body;
        this.author = author;
        this.quoteId = id;
    }

/*    public Quote(String quote, Author author) {
        this.quote = quote;
        this.author = author;
    }*/
}
