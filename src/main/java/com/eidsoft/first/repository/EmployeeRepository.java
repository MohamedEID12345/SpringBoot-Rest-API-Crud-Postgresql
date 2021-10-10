package com.eidsoft.first.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.eidsoft.first.models.Emploee;

@Repository
public interface EmployeeRepository extends JpaRepository<Emploee, Long>{

}
