package org.raflab.studsluzba.model;


import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class PredispitneObaveze {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private enum Vrsta {
        KOL1,
        KOL2,
        DOMACI,
        PROJEKAT;
    }
    private int maxBodova;
    private int osvojeniBodovi;


    @ManyToOne
    private SlusaPredmet slusaPredmet;
    @ManyToOne
    private SkolskaGodina skolskaGodina;

}
