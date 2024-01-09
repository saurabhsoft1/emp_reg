package com.act.emp.entity;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

public class EmployeeTest {

    @Test
    public void testEmployeeConstructorAndGetters() {
        // Create sample data
        Long id = 1L;
        String firstName = "John";
        String lastName = "Doe";
        String address = "123 Main St";
        int age = 30;
        String maritalStatus = "Single";
        double expectedSalary = 50000.0;

        // Create Employee object using constructor
        Employee employee = new Employee(id, firstName, lastName, address, age, maritalStatus, expectedSalary, new ArrayList<>());

        // Test getter methods
        assertEquals(id, employee.getId());
        assertEquals(firstName, employee.getFirstName());
        assertEquals(lastName, employee.getLastName());
        assertEquals(address, employee.getAddress());
        assertEquals(age, employee.getAge());
        assertEquals(maritalStatus, employee.getMaritalStatus());
        assertEquals(expectedSalary, employee.getExpectedSalary());
        assertTrue(employee.getEmploymentDetails().isEmpty()); // Assuming no employment details for simplicity
    }

    @Test
    public void testEmployeeSetters() {
        // Create Employee object
        Employee employee = new Employee();

        // Set values using setter methods
        Long id = 1L;
        String firstName = "John";
        String lastName = "Doe";
        String address = "123 Main St";
        int age = 30;
        String maritalStatus = "Single";
        double expectedSalary = 50000.0;

        employee.setId(id);
        employee.setFirstName(firstName);
        employee.setLastName(lastName);
        employee.setAddress(address);
        employee.setAge(age);
        employee.setMaritalStatus(maritalStatus);
        employee.setExpectedSalary(expectedSalary);

        // Test getter methods to ensure values are set correctly
        assertEquals(id, employee.getId());
        assertEquals(firstName, employee.getFirstName());
        assertEquals(lastName, employee.getLastName());
        assertEquals(address, employee.getAddress());
        assertEquals(age, employee.getAge());
        assertEquals(maritalStatus, employee.getMaritalStatus());
        assertEquals(expectedSalary, employee.getExpectedSalary());
    }
    
    @Test
    void testSetEmploymentDetails() {
        // Arrange
        Employee employee = new Employee();
        List<EmploymentDetails> employmentDetailsList = new ArrayList<>();

        // Act
        employee.setEmploymentDetails(employmentDetailsList);

        // Assert
        assertEquals(employmentDetailsList, employee.getEmploymentDetails());
    }

    @Test
    void testHashCode() {
        // Arrange
        Employee employee1 = new Employee();
        employee1.setId(1L);

        Employee employee2 = new Employee();
        employee2.setId(1L);

        // Act & Assert
        assertEquals(employee1.hashCode(), employee2.hashCode());
    }

    @Test
    void testEquals() {
        // Arrange
        Employee employee1 = new Employee();
        employee1.setId(1L);

        Employee employee2 = new Employee();
        employee2.setId(1L);

        Employee employee3 = new Employee();
        employee3.setId(2L);

        // Act & Assert
        assertEquals(employee1, employee2);
        assertNotEquals(employee1, employee3);
    }
}
