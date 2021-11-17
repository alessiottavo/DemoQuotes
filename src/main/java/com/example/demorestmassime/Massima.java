package com.example.demorestmassime;

import javax.persistence.*;

@Entity
public class Massima {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;
    private String autore;
    private String contenuto;

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public Massima(){}

    public Massima(String autore, String contenuto) {
        this.autore = autore;
        this.contenuto = contenuto;
    }
}
