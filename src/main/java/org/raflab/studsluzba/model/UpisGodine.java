package org.raflab.studsluzba.model;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class UpisGodine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate datumUpisa;
    private Integer prenetiESPB;

    @ManyToOne
    @JoinColumn(name = "student_indeks_id")
    private StudentIndeks studentIndeks;

    @ManyToOne
    @JoinColumn(name = "skolska_godina_id")
    private SkolskaGodina skolskaGodina;

    @OneToMany(mappedBy = "upisGodine", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SlusaPredmet> slusaniPredmeti = new ArrayList<>();

}
