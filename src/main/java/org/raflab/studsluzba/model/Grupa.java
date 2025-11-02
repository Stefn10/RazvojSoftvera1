package org.raflab.studsluzba.model;

import lombok.Data;

import java.util.List;

import javax.persistence.*;

@Entity
@Data
public class Grupa {
	
	@Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "studijski_program_id")
	private StudijskiProgram studijskiProgram;

	@ManyToOne
	@JoinColumn(name = "skolska_godina_id")
	private SkolskaGodina skolskaGodina;

	@OneToMany(mappedBy = "grupa")
	private List<DrziPredmet> drziPredmeti;
}
