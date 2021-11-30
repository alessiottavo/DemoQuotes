package it.com.demo.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
//@JsonIgnoreProperties({"hibernateLazyInitializer"})
@Entity
@Table(name = "Author",
        uniqueConstraints = @UniqueConstraint(
                name = "name",
                columnNames = "name"
        ))

public class Author {
    @Id
    @SequenceGenerator(name = "author_sequence", sequenceName = "author_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "author_sequence")
    private Long id;

    @Column(name = "name",
            nullable = false)
    private String name;
    @Column(name = "surname",
            nullable = false)
    private String surname;
    @Column(name = "date_of_birth",
            nullable = false)
    private Date dateOfBirth;
    @Column(name = "date_of_death",
            nullable = true)
    private Date dateOfDeath;
    @Column(name = "title",
            nullable = false)
    private String title;
    @Column(name = "profession",
            nullable = false)
    private String profession;
    @Column(name = "website",
            nullable = false)
    private String website;

    @JsonBackReference
    @OneToMany(
            mappedBy = "author",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY
    )
    @ToString.Exclude
    private Set<Quote> quotes = new HashSet<>();

/*
    public Author(String authorname) {
        this.name = authorname;
    }
*/

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Author author = (Author) o;
        return Objects.equals(id, author.id) && Objects.equals(name, author.name) && Objects.equals(surname, author.surname) && Objects.equals(dateOfBirth, author.dateOfBirth) && Objects.equals(dateOfDeath, author.dateOfDeath) && Objects.equals(title, author.title) && Objects.equals(profession, author.profession) && Objects.equals(website, author.website) && Objects.equals(quotes, author.quotes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, surname, dateOfBirth, dateOfDeath, title, profession, website, quotes);
    }
}
