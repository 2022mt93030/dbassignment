package com.dba.assignment.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.dba.assignment.entity.Course;

@Repository
public interface CourseRepository extends JpaRepository<Course, Integer> {

	public boolean existsByTitle(String title); 
	
	@Query("SELECT c FROM Course c WHERE c.skill.id = :skillId")
	public List<Course> findBySkill(@Param("skillId") int skillId);
	
//	@Query("SELECT c FROM Course c WHERE (c.id = :courseId and c.isDisabled = false)")
//	public Optional<Course> findCourse(@Param("courseId") int courseId);
}
