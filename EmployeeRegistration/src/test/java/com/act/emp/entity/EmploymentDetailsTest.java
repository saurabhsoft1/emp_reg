package com.act.emp.entity;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

class EmploymentDetailsTest {

    @Test
    void testGettersAndSetters() {
        // Arrange
        EmploymentDetails employmentDetails = new EmploymentDetails();
        
        // Act
        employmentDetails.setId(1L);
        employmentDetails.setCompanyName("ABC Company");
        employmentDetails.setTotalCTC(50000.0);
        employmentDetails.setStartDate(LocalDate.of(2022, 1, 1));
        employmentDetails.setEndDate(LocalDate.of(2023, 12, 31));
        
        // Assert
        assertEquals(1L, employmentDetails.getId());
        assertEquals("ABC Company", employmentDetails.getCompanyName());
        assertEquals(50000.0, employmentDetails.getTotalCTC());
        assertEquals(LocalDate.of(2022, 1, 1), employmentDetails.getStartDate());
        assertEquals(LocalDate.of(2023, 12, 31), employmentDetails.getEndDate());
    }

    @Test
    void testEqualsAndHashCode() {
        // Arrange
        EmploymentDetails employmentDetails1 = new EmploymentDetails(1L, "ABC Company", 50000.0, LocalDate.of(2022, 1, 1), LocalDate.of(2023, 12, 31));
        EmploymentDetails employmentDetails2 = new EmploymentDetails(1L, "ABC Company", 50000.0, LocalDate.of(2022, 1, 1), LocalDate.of(2023, 12, 31));

        // Act & Assert
        assertEquals(employmentDetails1, employmentDetails2);
        assertEquals(employmentDetails1.hashCode(), employmentDetails2.hashCode());
    }


}
