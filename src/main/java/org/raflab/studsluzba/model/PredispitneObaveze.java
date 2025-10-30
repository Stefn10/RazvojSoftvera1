package org.raflab.studsluzba.model;


import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class PredispitneObaveze {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String naziv;
    private int maxBodova;

    @ManyToOne
    private SlusaPredmet slusaPredmet;
    @ManyToOne
    private SkolskaGodina skolskaGodina;

}
