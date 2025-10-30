package org.raflab.studsluzba.model;

import lombok.Data;
import org.apache.tomcat.jni.Local;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Data
public class IspitniRok {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate datum_pocetka;
    private LocalDate datum_zavrsetka;
    @OneToMany
    private List<Ispit> ispiti;
}
