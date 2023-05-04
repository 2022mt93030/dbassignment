package com.dba.assignment.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.dba.assignment.entity.ServiceLogContain;
import com.dba.assignment.entity.ServiceLogContainPK;

@Repository
public interface ServiceLogContainRepository extends CrudRepository<ServiceLogContain, ServiceLogContainPK> {

}
