package org.raflab.studsluzba.model;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

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

	/**TODO Ovo izbaciti ako ne bude trebalo.
	 * 
	 * 
	 * Automatski sinhronizuje predmet na osnovu drziPredmet.predmet
	 * pre čuvanja ili ažuriranja entiteta.
	 * Osigurava konzistentnost između redundantnih veza.
	 * Direktna veza sa Predmet je zadržana za brže upite (direktan pristup za prikaz obaveza).
	 */
	@PrePersist
	@PreUpdate
	private void synchronizePredmet() {
		if (drziPredmet != null && drziPredmet.getPredmet() != null) {
			// Ako predmet nije postavljen ili se razlikuje, automatski ga postavi
			if (predmet == null || !predmet.getId().equals(drziPredmet.getPredmet().getId())) {
				predmet = drziPredmet.getPredmet();
			}
		} else if (drziPredmet == null && predmet != null) {
			// Ako je postavljen predmet ali ne i drziPredmet, to je validno stanje
			// (može biti priznat predmet bez drziPredmet)
			// Ne diramo ništa
		}
	}

	public enum Status {
		SLUSA,
		PRIZNAT,
		OBNAVLJA,
		ZAVRSIO;
	}
}
