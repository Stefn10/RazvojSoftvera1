package org.raflab.studsluzba.repositories;


import org.raflab.studsluzba.model.DrziPredmet;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DrziPredmetRepository extends CrudRepository<DrziPredmet, Long> {
}