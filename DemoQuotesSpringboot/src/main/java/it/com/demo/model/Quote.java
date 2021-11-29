package it.com.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import javax.persistence.*;

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

    @Column
    private String quote;

    @JoinColumn(
            name = "author_id",
            referencedColumnName = "id"
    )
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Author author;

/*    public Quote(String quote, Author author) {
        this.quote = quote;
        this.author = author;
    }*/
}
