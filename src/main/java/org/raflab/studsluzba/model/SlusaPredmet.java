package org.raflab.studsluzba.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;

import lombok.Data;

@Entity
@Data
public class SlusaPredmet {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	@ManyToOne	
	private StudentIndeks studentIndeks;
	
	@ManyToOne
	private DrziPredmet drziPredmet;

	@ManyToOne
	@JoinColumn(name = "upis_godine_id")
	private UpisGodine upisGodine;

	@ManyToOne
	@JoinColumn(name = "student_visoka_ustanova_id")
	private StudentVisokaUstanova studentVisokaUstanova;

}