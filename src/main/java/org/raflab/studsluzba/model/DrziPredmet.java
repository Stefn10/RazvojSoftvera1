package org.raflab.studsluzba.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Data;

@Entity
@Data
public class DrziPredmet {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "nastavnik_id")
	private Nastavnik nastavnik;
	
	@ManyToOne
	@JoinColumn(name = "predmet_id")
	private Predmet predmet;

	@ManyToOne
	@JoinColumn(name = "skolska_godina_id")
	private SkolskaGodina skolskaGodina;

	@ManyToOne
	@JoinColumn(name = "grupa_id")
	private Grupa grupa;
}
