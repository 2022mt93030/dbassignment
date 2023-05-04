package com.dba.assignment.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.dba.assignment.entity.Project;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {

	@Query("SELECT p FROM Project p WHERE p.dept.id = :deptId")
	public Page<Project> findByDeptId(@Param("deptId") int deptId, Pageable pageable);
	
	@Query("SELECT new com.dba.assignment.entity.Project(p.id, p.name, p.client, p.startDate, p.endDate) FROM Project p WHERE p.dept.id = :deptId")
	public List<Project> findWithDeptId(@Param("deptId") int deptId);
}
