package com.act.emp.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ErrorResponseTest {

	@Test
	public void testErrorMessage() {
		// Arrange
		String expectedMessage = "Test error message";
		ErrorResponse errorResponse = new ErrorResponse(expectedMessage);

		// Act
		String actualMessage = errorResponse.getMessage();

		// Assert
		assertEquals(expectedMessage, actualMessage, "Error message should match");
	}

	@Test
	public void testGetMessage() {
		// Arrange
		String expectedMessage = "Test error message";
		ErrorResponse errorResponse = new ErrorResponse(expectedMessage);

		// Act
		String actualMessage = errorResponse.getMessage();

		// Assert
		assertEquals(expectedMessage, actualMessage, "Error message should match");
	}

	@Test
	public void testSetMessage() {
		// Arrange
		ErrorResponse errorResponse = new ErrorResponse();

		// Act
		String newMessage = "New error message";
		errorResponse.setMessage(newMessage);

		// Assert
		assertEquals(newMessage, errorResponse.getMessage(), "Error message should be set correctly");
	}
	
	  @Test
	    public void testEquals() {
	        // Arrange
	        ErrorResponse errorResponse1 = new ErrorResponse("Test message");
	        ErrorResponse errorResponse2 = new ErrorResponse("Test message");
	        ErrorResponse errorResponse3 = new ErrorResponse("Different message");

	        // Assert
	        assertTrue(errorResponse1.equals(errorResponse2), "Objects with the same message should be equal");
	        assertFalse(errorResponse1.equals(errorResponse3), "Objects with different messages should not be equal");
	    }

	    @Test
	    public void testHashCode() {
	        // Arrange
	        ErrorResponse errorResponse1 = new ErrorResponse("Test message");
	        ErrorResponse errorResponse2 = new ErrorResponse("Test message");

	        // Assert
	        assertEquals(errorResponse1.hashCode(), errorResponse2.hashCode(), "Hash codes should be equal for objects with the same message");
	    }

	    @Test
	    public void testToString() {
	        // Arrange
	        ErrorResponse errorResponse = new ErrorResponse("Test message");

	        // Act
	        String toStringResult = errorResponse.toString();

	        // Assert
	        assertTrue(toStringResult.contains("message=Test message"), "ToString should contain the error message field");
	    }

	    @Test
	    public void testCanEqual() {
	        // Arrange
	        ErrorResponse errorResponse1 = new ErrorResponse("Test message");
	        ErrorResponse errorResponse2 = new ErrorResponse("Different message");

	        // Act
	        boolean canEqualResult = errorResponse1.canEqual(errorResponse2);

	        // Assert
	        assertTrue(canEqualResult, "CanEqual should return true for objects with different messages");
	    }
}
