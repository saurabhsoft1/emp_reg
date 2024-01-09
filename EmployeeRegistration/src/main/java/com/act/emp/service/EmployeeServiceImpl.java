package com.act.emp.service;

import java.util.List;
import java.util.Optional;
import java.util.Comparator;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.act.emp.exception.EmployeeNotFoundException;
import com.act.emp.entity.Employee;
import com.act.emp.entity.EmploymentDetails;
import com.act.emp.repository.EmployeeRepository;
import com.act.emp.repository.EmploymentDetailsRepository;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class EmployeeServiceImpl implements EmployeeService {

	@Autowired
	private EmployeeRepository employeeRepository;
	
	@Autowired
	private EmploymentDetailsRepository employmentDetailsRepository;

	@Override
	@Transactional
	public Employee registerEmployee(Employee employee) {
		log.info("Request transfer to repository to register Employee details {}",employee.toString());
		return employeeRepository.save(employee);
	}

	@Override
	@Transactional
	public EmploymentDetails getLongestEmploymentDetails(Long employeeId) {
		log.info("Request transfer to repository to getLongestEmploymentDetails of emp id {}",employeeId);
		Optional<Employee> optionalEmployee = employeeRepository.findById(employeeId);
		if (optionalEmployee.isPresent()) {
			Employee employee = optionalEmployee.get();

			List<EmploymentDetails> employmentDetails = employee.getEmploymentDetails();

			if (employmentDetails != null) {
				// Sort employments by duration
				employmentDetails.sort(Comparator.comparingLong(e -> e.getEndDate().toEpochDay() - e.getStartDate().toEpochDay()));

				// Get the longest employment details
				EmploymentDetails longestEmployment = employmentDetails.get(employmentDetails.size() - 1);
				log.info("Request receive from repository to getLongestEmploymentDetails of emp id {} is {}",employeeId, longestEmployment.toString());
				return longestEmployment;
			} else {
				return null;
			}
		} else {
			return null;
		}

	}

	@Override
	@Transactional
	public Employee updateEmployee(Long id, Employee updatedEmployee) {
		log.info("Request transfer to repository to update Employee with id={} and details are {}",id,updatedEmployee.toString());
		Employee existingEmployee = employeeRepository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException("Employee not found with id: " + id));
		// Copy properties from updatedEmployee to existingEmployee, ignoring null values
        BeanUtils.copyProperties(updatedEmployee, existingEmployee, "id", "employmentDetails");
        
        // Update employment details separately to avoid unwanted changes
        if (updatedEmployee.getEmploymentDetails() != null) {
            for (int i = 0; i < updatedEmployee.getEmploymentDetails().size(); i++) {
                EmploymentDetails updatedDetails = updatedEmployee.getEmploymentDetails().get(i);
                EmploymentDetails existingDetails = existingEmployee.getEmploymentDetails().get(i);
                BeanUtils.copyProperties(updatedDetails, existingDetails, "id");
            }
        }
        // Save the updated employee
        return employeeRepository.save(existingEmployee);
	}

	@Override
	@Transactional
	public void deleteEmployee(Long id) {
		log.info("Request transfer to repository to Delete Employee with id={} ",id);
		Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException("Employee not found with id: " + id));
		// Delete the employee
        employeeRepository.delete(employee);
        log.info("Request receive from repository to Deleted Employee with id={} ",id);
	}

	@Override
	@Transactional
	public List<Employee> getAllEmployees() {
		log.info("Request transfer to repository to get all the employee details ");
		return  employeeRepository.findAll();
	}

	@Override
	@Transactional
	public void deleteEmploymentDetails(Long employeeId, Long employmentId) {
		log.info("Request transfer to repository to delete employment detail");
		/*
		 * Employee employee = employeeRepository.findById(employeeId) .orElseThrow(()
		 * -> new EmployeeNotFoundException("Employee or Employment not found"));
		 * 
		 * List<EmploymentDetails> employmentDetailsList =
		 * employee.getEmploymentDetails();
		 * employmentDetailsList.removeIf(employmentDetails ->
		 * employmentDetails.getId().equals(employmentId));
		 * 
		 * employeeRepository.save(employee);
		 */	
        
        
        Optional<Employee> optionalEmployee = employeeRepository.findById(employeeId);
        optionalEmployee.ifPresent(employee -> {
            employee.getEmploymentDetails().removeIf(employmentDetails -> employmentDetails.getId().equals(employmentId));
            employeeRepository.save(employee);
        });
	}
	
    public void deleteEmploymentDetailFromDatabase(Long employmentId) {
    	employmentDetailsRepository.deleteById(employmentId);
    }

	@Override
	@Transactional
	public void addEmploymentDetails(Long employeeId, EmploymentDetails employmentDetails) {
		log.info("Request transfer to repository to add employment detail");
		Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        List<EmploymentDetails> employmentDetailsList = employee.getEmploymentDetails();
        employmentDetailsList.add(employmentDetails);
        employeeRepository.save(employee);
		
	}

}
