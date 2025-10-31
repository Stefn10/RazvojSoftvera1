package org.raflab.studsluzba.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class SkolskaGodina {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String naziv; // 2022/2023
    @OneToMany(mappedBy = "skolskaGodina")
    private List<IspitniRok> ispitniRokovi = new ArrayList<>();
    @OneToMany
    private List<StudijskiProgram> studijskiProgrami;
    @OneToMany(mappedBy = "skolskaGodina")
    private List<DrziPredmet> drziPredmeti = new ArrayList<>();
    private boolean aktivna;

}
