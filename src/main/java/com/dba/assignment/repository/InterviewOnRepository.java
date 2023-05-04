package com.dba.assignment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dba.assignment.entity.InterviewOn;
import com.dba.assignment.entity.InterviewOnPK;

@Repository
public interface InterviewOnRepository extends JpaRepository<InterviewOn, InterviewOnPK> {

}
