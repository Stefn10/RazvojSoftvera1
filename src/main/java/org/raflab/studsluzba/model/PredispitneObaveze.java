package org.raflab.studsluzba.model;


import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class PredispitneObaveze {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private SlusaPredmet slusaPredmet;
    @ManyToOne
    private SkolskaGodina skolskaGodina;

}
