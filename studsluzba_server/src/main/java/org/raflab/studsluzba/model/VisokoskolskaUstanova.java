package org.raflab.studsluzba.model;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

public class VisokoskolskaUstanova {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long id;

    private String naziv;
    private String mesto;
    private String vrsta;


}
