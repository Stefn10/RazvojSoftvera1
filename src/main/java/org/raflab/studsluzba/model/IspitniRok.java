package org.raflab.studsluzba.model;

import lombok.Data;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class IspitniRok {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate datumPocetka;
    private LocalDate datumZavrsetka;
// Da li je potrebno neku anotaciju staviti
    private int tipRoka; // sifarnik: Januar, Februar...

    @ManyToOne
    @JoinColumn(name = "skolska_godina_id")
    private SkolskaGodina skolskaGodina;

    @OneToMany(mappedBy = "ispitniRok", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Ispit> ispiti = new ArrayList<>();
}
