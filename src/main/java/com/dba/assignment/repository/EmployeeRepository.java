package com.dba.assignment.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.dba.assignment.entity.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long>{

	@Query("SELECT e FROM Employee e WHERE e.department.id = :deptId")
	List<Employee> findByDept(@Param("deptId") int deptId);
	
	@Query("SELECT e FROM Employee e WHERE e.id in (SELECT DISTINCT p.supervisor.id FROM Employee p WHERE p.department.id = :deptId)")
	List<Employee> findSupervisorsByDept(@Param("deptId") int deptId);
}
