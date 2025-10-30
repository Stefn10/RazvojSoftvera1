package org.raflab.studsluzba.model;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
public class SkolskaGodina {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToMany
    private List<IspitniRok> ispitniRokovi;
    @OneToMany
    private List<StudijskiProgram> studijskiProgrami;
    private boolean aktivna;

}
