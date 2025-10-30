package org.raflab.studsluzba.model;


import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Data
public class Ispit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate datum_drzavanja;
    private LocalTime vreme_odrzavanja;
    @OneToMany
    private Predmet ispitniPredmet;
//    @ManyToOne
//    @JoinColumn(name = "nastavnik_id")
//    private Nastavnik nastavnik;
    private boolean zakljucen;

}
