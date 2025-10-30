package org.raflab.studsluzba.model;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Data
public class VisokoskolskaUstanova {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    private String naziv;
    private String tipSkole;
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long sifarnik;
    private LocalDate datumUpisa;
    private LocalDate datumZavrsetka;
    private List<Ispit> polozeniIspiti; //Todo ako je student presao sa drugog fakulteta treba da mu se prenesu i priznaju ispiti

}
