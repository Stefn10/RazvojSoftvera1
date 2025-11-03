package org.raflab.studsluzba.repositories;

import java.util.List;

import org.raflab.studsluzba.model.Nastavnik;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

//https://docs.spring.io/spring-data/jpa/reference/jpa/query-methods.html

@Repository
public interface NastavnikRepository extends JpaRepository<Nastavnik, Long> {

	List<Nastavnik> findByEmailIn(List<String> emails);
}