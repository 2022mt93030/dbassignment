package com.dba.assignment.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.dba.assignment.entity.Vacancy;

@Repository
public interface VacancyRepository extends JpaRepository<Vacancy, Long> {
	
	List<Vacancy> findByDept(final long deptId);
	
	@Query("SELECT new com.dba.assignment.entity.Vacancy(v.id, v.title, v.description, v.status, v.count) FROM Vacancy v")
	List<Vacancy> findAll();

}
