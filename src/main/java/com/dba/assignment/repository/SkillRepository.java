package com.dba.assignment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dba.assignment.entity.Skill;

@Repository
public interface SkillRepository extends JpaRepository<Skill, Integer> {

	public boolean existsByName(String name); 
}
