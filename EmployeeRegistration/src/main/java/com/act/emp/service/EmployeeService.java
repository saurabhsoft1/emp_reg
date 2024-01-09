package com.act.emp.service;


import java.util.List;

import com.act.emp.entity.Employee;
import com.act.emp.entity.EmploymentDetails;

public interface EmployeeService {

	Employee registerEmployee(Employee employee);

	EmploymentDetails getLongestEmploymentDetails(Long employeeId);

	Employee updateEmployee(Long id, Employee updatedEmployee);

	void deleteEmployee(Long id);

	List<Employee> getAllEmployees();

	void deleteEmploymentDetails(Long employeeId, Long employmentId);

	void addEmploymentDetails(Long employeeId, EmploymentDetails employmentDetails);

	void deleteEmploymentDetailFromDatabase(Long employmentId);
}
