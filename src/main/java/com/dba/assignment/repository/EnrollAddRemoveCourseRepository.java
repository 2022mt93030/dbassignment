package com.dba.assignment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.dba.assignment.entity.EnrollAddRemoveCourse;
import com.dba.assignment.entity.EnrollAddRemoveCoursePK;

@Repository
public interface EnrollAddRemoveCourseRepository extends JpaRepository<EnrollAddRemoveCourse, EnrollAddRemoveCoursePK> {


}
