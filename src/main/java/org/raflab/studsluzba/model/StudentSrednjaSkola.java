package org.raflab.studsluzba.model;

import lombok.Data;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
@Data
public class StudentSrednjaSkola {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private StudentPodaci student;
    @ManyToOne
    private SrednjaSkola srednjaSkola;

    private LocalDate datumUpisa;
    private LocalDate datumZavrsetka;
    private double uspehSaPrijemnog;
    private double uspehIzSrednjeSkole;

}
