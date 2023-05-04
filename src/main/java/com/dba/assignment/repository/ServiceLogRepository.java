package com.dba.assignment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dba.assignment.entity.ServiceLog;

@Repository
public interface ServiceLogRepository extends JpaRepository<ServiceLog, Long> {

}
