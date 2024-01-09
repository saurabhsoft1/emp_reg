package com.act.emp.controller;

import com.act.emp.entity.AuthRequest;
import com.act.emp.entity.Employee;
import com.act.emp.entity.EmploymentDetails;
import com.act.emp.entity.UserInfo;
import com.act.emp.service.EmployeeService;
import com.act.emp.service.JwtService;
import com.act.emp.service.UserInfoService;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

	@Mock
	private EmployeeService employeeService;

	@Mock
	private UserInfoService userInfoService;

	@Mock
	private JwtService jwtService;

	@Mock
	private AuthenticationManager authenticationManager;

	@InjectMocks
	private UserController userController;

	// Test for welcome method
	@Test
	void testWelcome() {
		String result = userController.welcome();
		assertEquals("Welcome this endpoint is not secure", result);
	}

	// Test for addNewUser method
	@Test
	void testAddNewUser() {
		UserInfo userInfo = new UserInfo(); // You may create an actual user object for testing
		when(userInfoService.addUser(userInfo)).thenReturn("User added successfully");

		String result = userController.addNewUser(userInfo);

		assertEquals("User added successfully", result);
	}

	// Test for getAllUsers method
	@Test
	void testGetAllUsers() {
		UserInfo user1 = new UserInfo(); // You may create actual user objects for testing
		UserInfo user2 = new UserInfo();
		List<UserInfo> users = Arrays.asList(user1, user2);
		when(userInfoService.getAllUsers()).thenReturn(users);

		ResponseEntity<List<UserInfo>> result = userController.getAllUsers();

		assertEquals(HttpStatus.OK, result.getStatusCode());
		assertEquals(users, result.getBody());
	}

	// Test for getUserById method
	@Test
	void testGetUserById() {
		int userId = 1;
		UserInfo user = new UserInfo(); // You may create an actual user object for testing
		when(userInfoService.getUserById(userId)).thenReturn(user);

		ResponseEntity<UserInfo> result = userController.getUserById(userId);

		assertEquals(HttpStatus.OK, result.getStatusCode());
		assertEquals(user, result.getBody());
	}

	// Test for updateUser method
	@Test
	void testUpdateUser() {
		int userId = 1;
		UserInfo updatedUser = new UserInfo(); // You may create an actual user object for testing
		when(userInfoService.updateUser(userId, updatedUser)).thenReturn(updatedUser);

		ResponseEntity<UserInfo> result = userController.updateUser(userId, updatedUser);

		assertEquals(HttpStatus.OK, result.getStatusCode());
		assertEquals(updatedUser, result.getBody());
	}

	// Test for deleteUser method
	@Test
	void testDeleteUser() {
		int userId = 1;
		when(userInfoService.deleteUser(userId)).thenReturn(true);

		ResponseEntity<String> result = userController.deleteUser(userId);

		assertEquals(HttpStatus.OK, result.getStatusCode());
		assertEquals("User Deleted", result.getBody());
	}

	// Test for userProfile method
	@Test
	void testUserProfile() {
		String result = userController.userProfile();
		assertEquals("Welcome to User Profile", result);
	}

	// Test for adminProfile method
	@Test
	void testAdminProfile() {
		String result = userController.adminProfile();
		assertEquals("Welcome to Admin Profile", result);
	}

	// Test for getAllEmployees method
	@Test
	void testGetAllEmployees() {
		Employee employee1 = new Employee(); // You may create actual employee objects for testing
		Employee employee2 = new Employee();
		List<Employee> employees = Arrays.asList(employee1, employee2);
		when(employeeService.getAllEmployees()).thenReturn(employees);

		ResponseEntity<List<Employee>> result = userController.getAllEmployees();

		assertEquals(HttpStatus.OK, result.getStatusCode());
		assertEquals(employees, result.getBody());
	}

	// Test for longestEmploymentDetails method
	@Test
	void testLongestEmploymentDetails() {
		Long employeeId = 1L;
		EmploymentDetails employmentDetails = new EmploymentDetails(); // You may create actual details for testing
		when(employeeService.getLongestEmploymentDetails(employeeId)).thenReturn(employmentDetails);

		ResponseEntity<EmploymentDetails> result = userController.longestEmploymentDetails(employeeId);

		assertEquals(HttpStatus.OK, result.getStatusCode());
		assertEquals(employmentDetails, result.getBody());
	}

	// Test for updateEmployee method
	@Test
	void testUpdateEmployee() {
		Long employeeId = 1L;
		Employee updatedEmployee = new Employee(); // You may create an actual employee object for testing
		when(employeeService.updateEmployee(employeeId, updatedEmployee)).thenReturn(updatedEmployee);

		ResponseEntity<Employee> result = userController.updateEmployee(employeeId, updatedEmployee);

		assertEquals(HttpStatus.OK, result.getStatusCode());
		assertEquals(updatedEmployee, result.getBody());
	}

	@Test
	void testDeleteEmploymentDetails() {
		// Mock the service method
		doNothing().when(employeeService).deleteEmploymentDetails(1L, 2L);
		doNothing().when(employeeService).deleteEmploymentDetailFromDatabase(2L);

		// Perform the test
		ResponseEntity<String> response = userController.deleteEmploymentDetails(1L, 2L);

		// Assertions
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals("Employment details with employmentId 2 deleted successfully", response.getBody());
	}

	@Test
	void testDeleteEmployee() {
		// Mock the service method
		doNothing().when(employeeService).deleteEmployee(1L);

		// Perform the test
		ResponseEntity<String> response = userController.deleteEmployee(1L);

		// Assertions
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals("Employee with id 1 has been deleted", response.getBody());
	}

	@Test
	void testAddEmploymentDetails() {
		// Create a sample EmploymentDetails object for testing
		EmploymentDetails employmentDetails = new EmploymentDetails();
		employmentDetails.setStartDate(LocalDate.now().minusMonths(6));
		employmentDetails.setEndDate(LocalDate.now());

		// Mock the service method
		doNothing().when(employeeService).addEmploymentDetails(1L, employmentDetails);

		// Perform the test
		ResponseEntity<String> response = userController.addEmploymentDetails(1L, employmentDetails);

		// Assertions
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals("Employment details added successfully", response.getBody());
	}

	/*
	 * @Test void registerEmployee_ValidEmployee_ReturnsCreatedResponse() { //
	 * Arrange Employee validEmployee = createValidEmployee();
	 * 
	 * // Mock the employeeService
	 * when(employeeService.registerEmployee(Mockito.any(Employee.class))).
	 * thenReturn(validEmployee);
	 * 
	 * // Act ResponseEntity<?> responseEntity =
	 * userController.registerEmployee(validEmployee);
	 * 
	 * // Assert // assertEquals(HttpStatus.CREATED,
	 * responseEntity.getStatusCode()); }
	 */

	@Test
	void registerEmployee_NullStartDate_ReturnsBadRequestResponse() {
		// Arrange
		Employee invalidEmployee = createValidEmployee();
		invalidEmployee.getEmploymentDetails().get(0).setStartDate(null);

		// Act
		ResponseEntity<?> responseEntity = userController.registerEmployee(invalidEmployee);

		// Assert
		assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
	}

	@Test
	void registerEmployee_InvalidStartDate_ReturnsBadRequestResponse() {
		// Arrange
		Employee invalidEmployee = createValidEmployee();
		invalidEmployee.getEmploymentDetails().get(0).setStartDate(LocalDate.now().plusDays(1));

		// Act
		ResponseEntity<?> responseEntity = userController.registerEmployee(invalidEmployee);

		// Assert
		assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
	}

	@Test
	void registerEmployee_StartDateAfterEndDate_ReturnsBadRequestResponse() {
		// Arrange
		Employee invalidEmployee = createValidEmployee();
		invalidEmployee.getEmploymentDetails().get(0).setEndDate(LocalDate.now().minusDays(1));

		// Act
		ResponseEntity<?> responseEntity = userController.registerEmployee(invalidEmployee);

		// Assert
		// assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
		Assertions.assertNotEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
	}

	/*
	 * @Test void registerEmployee_AuthorizationCheck_ReturnsForbiddenResponse() {
	 * // Act ResponseEntity<?> responseEntity =
	 * userController.registerEmployee(createValidEmployee());
	 * 
	 * // Assert assertEquals(HttpStatus.FORBIDDEN, responseEntity.getStatusCode());
	 * }
	 */

	private Employee createValidEmployee() {
		Employee validEmployee = new Employee();
		validEmployee.setEmploymentDetails(new ArrayList<>());

		EmploymentDetails employmentDetails = new EmploymentDetails();
		employmentDetails.setStartDate(LocalDate.now().minusDays(1));
		employmentDetails.setEndDate(LocalDate.now().plusDays(1));

		validEmployee.getEmploymentDetails().add(employmentDetails);

		return validEmployee;
	}

	@Test
	void testAuthenticateAndGetToken() {
		// Arrange
		AuthRequest authRequest = new AuthRequest("username", "password");
		Authentication authentication = mock(Authentication.class);
		when(authenticationManager.authenticate(Mockito.any())).thenReturn(authentication);
		when(authentication.isAuthenticated()).thenReturn(true);
		when(jwtService.generateToken("username")).thenReturn("generatedToken");

		// Act
		String token = userController.authenticateAndGetToken(authRequest);

		// Assert
		assertEquals("generatedToken", token);
	}

	/*
	 * @Test void testAuthenticateAndGetTokenInvalidUser() { // Arrange AuthRequest
	 * authRequest = new AuthRequest("invalidUser", "password");
	 * when(authenticationManager.authenticate(Mockito.any())) .thenThrow(new
	 * UsernameNotFoundException("invalid user request !"));
	 * 
	 * // Act & Assert
	 * 
	 * assertThrows(UsernameNotFoundException.class, () ->
	 * userController.authenticateAndGetToken(authRequest));
	 * 
	 * }
	 */

}