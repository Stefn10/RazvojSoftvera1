package org.raflab.studsluzba.repositories;


import java.util.List;

import org.raflab.studsluzba.model.StudijskiProgram;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudijskiProgramRepository extends CrudRepository<StudijskiProgram, Long> {
	
	@Query("select distinct oznaka from StudijskiProgram")
    List<String> findAllOznaka();
	
	@Query("select sp from StudijskiProgram sp order by sp.godinaAkreditacije desc")
	List<StudijskiProgram> getAllSortedByGodinaDesc();
}