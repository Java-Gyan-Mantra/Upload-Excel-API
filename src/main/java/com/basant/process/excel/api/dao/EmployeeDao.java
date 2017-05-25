package com.basant.process.excel.api.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.basant.process.excel.api.pojo.EmployeeModel;

@RepositoryRestResource
public interface EmployeeDao extends JpaRepository<EmployeeModel, Integer> {

}
