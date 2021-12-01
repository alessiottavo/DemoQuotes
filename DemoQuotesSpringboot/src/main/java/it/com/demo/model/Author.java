package it.com.demo.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
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
    private LocalDate dateOfBirth;
    @Column(name = "date_of_death",
            nullable = true)
    private LocalDate dateOfDeath;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Author author = (Author) o;
        return id != null && Objects.equals(id, author.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    public Author(Long id, String name, String surname, LocalDate dateOfBirth, LocalDate dateOfDeath, String title, String profession, String website) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.dateOfBirth = dateOfBirth;
        this.dateOfDeath = dateOfDeath;
        this.title = title;
        this.profession = profession;
        this.website = website;
    }
}
