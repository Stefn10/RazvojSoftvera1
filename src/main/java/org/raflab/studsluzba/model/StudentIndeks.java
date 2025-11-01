package org.raflab.studsluzba.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;
import lombok.Data;

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
	private StudentPodaci student;
	
	@ManyToOne
	private StudijskiProgram studijskiProgram;   // na koji studijski program je upisan
	private Integer ostvarenoEspb;

	@ManyToOne
	private Grupa grupa;

	@OneToMany(mappedBy = "studentIndeks", cascade = CascadeType.ALL)
// TODO	@OrderBy("godina, datumUpisa DESC")
	private List<UpisGodine> upisiGodina = new ArrayList<>();

}
