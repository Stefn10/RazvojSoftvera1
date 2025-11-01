package org.raflab.studsluzba.model;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import lombok.Data;

@Entity
@Data
public class SlusaPredmet {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@ManyToOne
	private StudentIndeks studentIndeks;

	@ManyToOne
	private DrziPredmet drziPredmet;

	@ManyToOne
	private StudentVisokaUstanova studentVisokaUstanova;

	@Enumerated(EnumType.STRING)
	private Status status = Status.SLUSA;

	public enum Status {
		SLUSA,
		PRIZNAT,
		OBNAVLJA,
		ZAVRSIO;
	}
}
