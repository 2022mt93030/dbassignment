package com.dba.assignment.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.dba.assignment.entity.EmpSkill;
import com.dba.assignment.entity.EmpSkillPK;

@Repository
public interface EmpSkillRepository extends JpaRepository<EmpSkill, EmpSkillPK> {

	@Query("SELECT es FROM EmpSkill es WHERE es.id.empId = :empId")
	public List<EmpSkill> findByEmpId(@Param("empId") Long empId);
}
