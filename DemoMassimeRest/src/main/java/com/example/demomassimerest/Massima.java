package com.example.demomassimerest;

import javax.persistence.*;

@Entity
public class Massima {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    //Column(name = "id", nullable = false)
    private Long id;
    private String autore;
    private String content;

    public Massima() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Massima(String autore, String content) {
        super();
        this.autore = autore;
        this.content = content;
    }
}
