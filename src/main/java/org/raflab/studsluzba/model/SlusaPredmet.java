package org.raflab.studsluzba.model;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Data;

@Entity
@Data
public class SlusaPredmet {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@ManyToOne
	@JoinColumn(name = "student_indeks_id")
	private StudentIndeks studentIndeks;

	@ManyToOne
	@JoinColumn(name = "drzi_predmet_id")
	private DrziPredmet drziPredmet;

	@ManyToOne
	@JoinColumn(name = "student_visoka_ustanova_id")
	private StudentVisokaUstanova studentVisokaUstanova;

	@ManyToOne
	@JoinColumn(name = "predmet_id") 
	private Predmet predmet;

	@ManyToOne
	@JoinColumn(name = "upis_godine_id")
	private UpisGodine upisGodine;  // moze biti iz obnove ili priznat 

	@Enumerated(EnumType.STRING)
	private Status status = Status.SLUSA;

	public enum Status {
		SLUSA,
		PRIZNAT,
		OBNAVLJA,
		ZAVRSIO;
	}
}
