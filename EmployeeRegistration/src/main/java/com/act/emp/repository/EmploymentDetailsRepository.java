package com.act.emp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.act.emp.entity.EmploymentDetails;

@Repository
public interface EmploymentDetailsRepository extends JpaRepository<EmploymentDetails, Long> {

}
