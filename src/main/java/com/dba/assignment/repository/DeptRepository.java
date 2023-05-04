package com.dba.assignment.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.dba.assignment.entity.Dept;

@Repository
public interface DeptRepository extends JpaRepository<Dept, Integer> {

	@Query("SELECT new com.dba.assignment.entity.Dept(d.id, d.name) FROM Dept d")
	List<Dept> findAllDept();
	
}
