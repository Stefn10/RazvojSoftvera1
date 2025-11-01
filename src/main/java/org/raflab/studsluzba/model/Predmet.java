package org.raflab.studsluzba.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import lombok.Data;
import lombok.ToString;

@Entity
@Data
@ToString
public class Predmet {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	private String sifra;
	private String naziv;
	private String opis;
	private Integer espb;
	@ManyToOne
	// @ToString.Exclude  // Prevent circular reference in toString()
	private StudijskiProgram studProgram;
	
	@OneToMany(mappedBy = "predmet")
	// @ToString.Exclude  // Prevent circular reference in toString()
	private List<DrziPredmet> drziPredmeti = new ArrayList<>();
	
	@OneToMany(mappedBy = "predmet")
	// @ToString.Exclude  // Prevent circular reference in toString()
	private List<Ispit> ispiti = new ArrayList<>();
	private boolean obavezan;
//	@ManyToOne
//	@JoinColumn(name = "ispit_id")
//	private Ispit ispit;

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((sifra == null) ? 0 : sifra.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Predmet other = (Predmet) obj;
		if (sifra == null) {
			if (other.sifra != null)
				return false;
		} else if (!sifra.equals(other.sifra))
			return false;
		return true;
	}

}
