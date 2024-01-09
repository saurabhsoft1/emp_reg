package com.act.emp.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.BeanUtils;
import org.springframework.boot.test.context.SpringBootTest;

import com.act.emp.entity.Employee;
import com.act.emp.entity.EmploymentDetails;
import com.act.emp.repository.EmployeeRepository;
import com.act.emp.repository.EmploymentDetailsRepository;

@SpringBootTest
class EmployeeServiceImplTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private EmploymentDetailsRepository employmentDetailsRepository;

    @InjectMocks
    private EmployeeServiceImpl employeeService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRegisterEmployee() {
        // Mock data
        Employee employee = new Employee();
        employee.setId(1L);
        when(employeeRepository.save(any(Employee.class))).thenReturn(employee);

        // Test
        Employee registeredEmployee = employeeService.registerEmployee(new Employee());

        // Assertion
        assertNotNull(registeredEmployee);
        assertEquals(1L, registeredEmployee.getId());
    }

	/*
	 * @Test void testGetLongestEmploymentDetails() { // Mock data Long employeeId =
	 * 1L; EmploymentDetails employment1 = new EmploymentDetails(LocalDate.now(),
	 * LocalDate.now().plusYears(2)); EmploymentDetails employment2 = new
	 * EmploymentDetails(LocalDate.now().minusYears(1),
	 * LocalDate.now().plusMonths(6)); List<EmploymentDetails> employmentDetailsList
	 * = Arrays.asList(employment1, employment2); Employee employee = new
	 * Employee(); employee.setId(employeeId);
	 * employee.setEmploymentDetails(employmentDetailsList);
	 * 
	 * when(employeeRepository.findById(employeeId)).thenReturn(Optional.of(employee
	 * ));
	 * 
	 * // Test EmploymentDetails longestEmployment =
	 * employeeService.getLongestEmploymentDetails(employeeId);
	 * 
	 * // Assertion assertNotNull(longestEmployment); assertEquals(employment1,
	 * longestEmployment); }
	 * 
	 * @Test void testUpdateEmployee() { // Mock data Long employeeId = 1L; Employee
	 * existingEmployee = new Employee(); existingEmployee.setId(employeeId);
	 * 
	 * Employee updatedEmployee = new Employee(); updatedEmployee.setId(employeeId);
	 * updatedEmployee.setName("Updated Name");
	 * 
	 * when(employeeRepository.findById(employeeId)).thenReturn(Optional.of(
	 * existingEmployee));
	 * when(employeeRepository.save(any(Employee.class))).thenReturn(updatedEmployee
	 * );
	 * 
	 * // Test Employee result = employeeService.updateEmployee(employeeId,
	 * updatedEmployee);
	 * 
	 * // Assertion assertNotNull(result); assertEquals("Updated Name",
	 * result.getName()); }
	 */
    @Test
    void testDeleteEmployee() {
        // Mock data
        Long employeeId = 1L;
        Employee existingEmployee = new Employee();
        existingEmployee.setId(employeeId);

        when(employeeRepository.findById(employeeId)).thenReturn(Optional.of(existingEmployee));

        // Test
        employeeService.deleteEmployee(employeeId);

        // Verify
        verify(employeeRepository, times(1)).delete(existingEmployee);
    }

    @Test
    void testGetAllEmployees() {
        // Mock data
        List<Employee> employeeList = Arrays.asList(new Employee(), new Employee());
        when(employeeRepository.findAll()).thenReturn(employeeList);

        // Test
        List<Employee> result = employeeService.getAllEmployees();

        // Assertion
        assertNotNull(result);
        assertEquals(2, result.size());
    }

    @Test
    void testGetLongestEmploymentDetails() {
        // Mock data
        Long employeeId = 1L;
        Employee employee = new Employee();
        employee.setId(employeeId);

        EmploymentDetails employment1 = new EmploymentDetails();
        employment1.setStartDate(LocalDate.now().minusYears(3));
        employment1.setEndDate(LocalDate.now().minusYears(2));

        EmploymentDetails employment2 = new EmploymentDetails();
        employment2.setStartDate(LocalDate.now().minusYears(5));
        employment2.setEndDate(LocalDate.now().minusYears(4));

        employee.setEmploymentDetails(Arrays.asList(employment1, employment2));

        when(employeeRepository.findById(employeeId)).thenReturn(Optional.of(employee));

        // Test the service method
        EmploymentDetails longestEmployment = employeeService.getLongestEmploymentDetails(employeeId);

        // Verify the result
        assertNotNull(longestEmployment);
        assertEquals(employment2, longestEmployment);
    }
    
    @Test
    void testUpdateEmployee() {
        // Mock data
        Long employeeId = 1L;
        Employee existingEmployee = new Employee();
        existingEmployee.setId(employeeId);
        existingEmployee.setEmploymentDetails(Arrays.asList(new EmploymentDetails()));

        Employee updatedEmployee = new Employee();
        updatedEmployee.setFirstName("First Name");

        when(employeeRepository.findById(employeeId)).thenReturn(Optional.of(existingEmployee));
        when(employeeRepository.save(any(Employee.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Test the service method
        Employee resultEmployee = employeeService.updateEmployee(employeeId, updatedEmployee);

        // Verify the result
        assertNotNull(resultEmployee);
        assertEquals(updatedEmployee.getFirstName(), resultEmployee.getFirstName());
        assertEquals(existingEmployee.getEmploymentDetails(), resultEmployee.getEmploymentDetails());
    }
    
    @Test
    void testDeleteEmploymentDetailFromDatabase() {
        // Mock data
        Long employmentId = 1L;

        // Test the service method
        employeeService.deleteEmploymentDetailFromDatabase(employmentId);

        // Verify the interaction with the repository
        verify(employmentDetailsRepository).deleteById(employmentId);
    }

}
