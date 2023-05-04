package com.dba.assignment.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.dba.assignment.entity.Candidate;

@Repository
public interface CandidateRepository extends JpaRepository<Candidate, Long> {

	@Query("SELECT new com.dba.assignment.entity.Candidate(c.id, c.name, c.education, c.exp) FROM Candidate c Where c.vacancy.id=:vacancyId")
	List<Candidate> findByVacancy(@Param("vacancyId") long vacancyId);
}
