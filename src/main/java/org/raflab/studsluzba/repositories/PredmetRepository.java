package org.raflab.studsluzba.repositories;

import org.raflab.studsluzba.model.Predmet;
import org.raflab.studsluzba.model.StudijskiProgram;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PredmetRepository extends CrudRepository<Predmet, Long> {
	
	@Query("select p from Predmet p where p.studProgram.godinaAkreditacije = :godinaAkreditacije")
	List<Predmet> getPredmetForGodinaAkreditacije(Integer godinaAkreditacije);

	List<Predmet> getPredmetsByStudProgramAndObavezan(StudijskiProgram studProgram, boolean obavezan);

	List<Predmet> findByIdIn(List<Long> ids);
	List<Predmet> findByNazivIn(List<String> nazivi);
}
