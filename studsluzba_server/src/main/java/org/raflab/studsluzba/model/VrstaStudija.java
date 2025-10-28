package org.raflab.studsluzba.model;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

public class VrstaStudija {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long id;

    private String skraceniNaziv; //MAS
    private String punNaziv; //Master Akademske Studije

}
