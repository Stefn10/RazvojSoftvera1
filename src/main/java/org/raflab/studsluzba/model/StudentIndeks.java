package org.raflab.studsluzba.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;
import lombok.Data;
import lombok.ToString;

@Entity
@Data
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"broj", "godina", "studProgramOznaka", "aktivan"}))
public class StudentIndeks {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	private int broj;
	private int godina;
	private String studProgramOznaka;
	private String nacinFinansiranja;
	private boolean aktivan; 
	private LocalDate vaziOd;
	@ManyToOne
	// @ToString.Exclude  // Prevent circular reference in toString()
	private StudentPodaci student;
	
	@ManyToOne
	// @ToString.Exclude  // Prevent circular reference in toString()
	private StudijskiProgram studijskiProgram;   // na koji studijski program je upisan
	private Integer ostvarenoEspb;

	@ManyToOne
	// @ToString.Exclude  // Prevent circular reference in toString()
	private Grupa grupa;

	@OneToMany(mappedBy = "studentIndeks", cascade = CascadeType.ALL)
	// @ToString.Exclude  // Prevent circular reference in toString()
// TODO	@OrderBy("godina, datumUpisa DESC")
	private List<UpisGodine> upisiGodina = new ArrayList<>();

}
