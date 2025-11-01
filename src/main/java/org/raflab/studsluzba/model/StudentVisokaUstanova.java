package org.raflab.studsluzba.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class StudentVisokaUstanova {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private StudentPodaci student;

    @ManyToOne
    private VisokoskolskaUstanova visokaUstanova;

    private LocalDate datumUpisa;
    private LocalDate datumZavrsetka;
    private String napomena;

    @OneToMany(mappedBy = "studentVisokaUstanova")
    private List<SlusaPredmet> priznatiPredmeti = new ArrayList<>();
}
