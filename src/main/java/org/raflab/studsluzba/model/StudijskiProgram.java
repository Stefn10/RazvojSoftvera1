package org.raflab.studsluzba.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.ToString;

@Entity
@Data
public class StudijskiProgram {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	private String oznaka;  // RN, RM
	private String naziv;   
	private Integer godinaAkreditacije;
	private String zvanje;
	private Integer trajanjeGodina;
	private Integer trajanjeSemestara;
	private String vrstaStudija; // OAS - osnovne akademske studje, OSS - osnovne strukovne, 	MAS - master akademske studije
	private Integer ukupnoEspb;

	@ManyToOne
	private SkolskaGodina skolskaGodina;
	
	@JsonIgnore
	@OneToMany(mappedBy = "studProgram")
	// @ToString.Exclude  // Prevent circular reference in toString()
	private List<Predmet> predmeti = new ArrayList<>();

	@OneToMany(mappedBy = "studijskiProgram")
    // @ToString.Exclude  // Prevent circular reference in toString()
    private List<StudentIndeks> studentIndeksi = new ArrayList<>();

    @OneToMany(mappedBy = "studijskiProgram")
    @ToString.Exclude  // Prevent circular reference in toString()
    private List<Grupa> grupe = new ArrayList<>();


}
