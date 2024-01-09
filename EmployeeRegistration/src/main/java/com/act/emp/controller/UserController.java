package com.act.emp.controller;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import com.act.emp.entity.AuthRequest;
import com.act.emp.entity.Employee;
import com.act.emp.entity.EmploymentDetails;
import com.act.emp.entity.ErrorResponse;
import com.act.emp.entity.UserInfo;
import com.act.emp.service.EmployeeService;
import com.act.emp.service.JwtService;
import com.act.emp.service.UserInfoService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/auth")
public class UserController {

	@Autowired
	private EmployeeService employeeService;

	@Autowired
	private UserInfoService service;

	@Autowired
	private JwtService jwtService;

	@Autowired
	private AuthenticationManager authenticationManager;

	@GetMapping("/welcome")
	public String welcome() {
		return "Welcome this endpoint is not secure";
	}

	// Create a new user
	@PostMapping("/addNewUser")
	public String addNewUser(@RequestBody UserInfo userInfo) {
		return service.addUser(userInfo);
	}

	// Get All Users
	@GetMapping("/v1/getAllUsers")
	public ResponseEntity<List<UserInfo>> getAllUsers() {
		List<UserInfo> users = service.getAllUsers();
		return new ResponseEntity<>(users, HttpStatus.OK);
	}

	// Get a particular user by ID
	@GetMapping("/v1/getUserById/{id}")
	public ResponseEntity<UserInfo> getUserById(@PathVariable int id) {
		UserInfo user = service.getUserById(id);
		if (user != null) {
			return new ResponseEntity<>(user, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	// Update a user by ID
	@PutMapping("/v1/updateUser/{id}")
	public ResponseEntity<UserInfo> updateUser(@PathVariable int id, @RequestBody UserInfo updatedUser) {
		UserInfo user = service.updateUser(id, updatedUser);
		if (user != null) {
			return new ResponseEntity<>(user, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	// Delete a user by ID
	@DeleteMapping("/v1/deleteUser/{id}")
	public ResponseEntity<String> deleteUser(@PathVariable int id) {
		boolean deleted = service.deleteUser(id);
		if (deleted) {
			return new ResponseEntity<>("User Deleted", HttpStatus.OK);
		} else {
			return new ResponseEntity<>("User Not Found", HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping("/user/userProfile")
	@PreAuthorize("hasAuthority('ROLE_USER')")
	public String userProfile() {
		return "Welcome to User Profile";
	}

	@GetMapping("/admin/adminProfile")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public String adminProfile() {
		return "Welcome to Admin Profile";
	}

	@PostMapping("/generateToken")
	public String authenticateAndGetToken(@RequestBody AuthRequest authRequest) {
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
		if (authentication.isAuthenticated()) {
			return jwtService.generateToken(authRequest.getUsername());
		} else {
			throw new UsernameNotFoundException("invalid user request !");
		}
	}

	@GetMapping("/employees/getallemployees")
	@PreAuthorize("hasAuthority('ROLE_USER')")
	public ResponseEntity<List<Employee>> getAllEmployees() {
		log.info("Get All Employee  request started");
		List<Employee> employees = employeeService.getAllEmployees();
		log.info("Get All Employee  request finished");
		return new ResponseEntity<>(employees, HttpStatus.OK);
	}

	@GetMapping("/employees/longest-employment/{employeeId}")
	@PreAuthorize("hasAuthority('ROLE_USER')")
	public ResponseEntity<EmploymentDetails> longestEmploymentDetails(@PathVariable Long employeeId) {
		log.info("Longest employment for {} started", employeeId);
		EmploymentDetails employmentDetails = employeeService.getLongestEmploymentDetails(employeeId);
		log.info("Longest employment for {} finished with {}", employeeId, employmentDetails.toString());
		return new ResponseEntity<>(employmentDetails, HttpStatus.OK);
	}

	@PostMapping("/employees/register")
	@PreAuthorize("hasAuthority('ROLE_USER')")
	public ResponseEntity<?> registerEmployee(@RequestBody Employee employee) {
		// Validate startDate and endDate for each EmploymentDetails
		for (EmploymentDetails employmentDetails : employee.getEmploymentDetails()) {
			LocalDate startDate = employmentDetails.getStartDate();
			LocalDate endDate = employmentDetails.getEndDate();

			// Check if startDate is null
			if (startDate == null) {
				log.error("startDate should not be null: " + startDate);
				return new ResponseEntity<>(new ErrorResponse("startDate should not be null"), HttpStatus.BAD_REQUEST);
			}

			// Check if endDate is null
			if (endDate == null) {
				log.error("endDate should not be null: " + endDate);
				return new ResponseEntity<>(new ErrorResponse("endDate should not be null"), HttpStatus.BAD_REQUEST);
			}

			// Check if startDate is before the current date
			if (startDate != null && startDate.isAfter(LocalDate.now())) {
				log.error("Invalid startDate: " + startDate);
				return new ResponseEntity<>(new ErrorResponse("Invalid startDate : Start Date " + startDate
						+ " must before the current date " + LocalDate.now()), HttpStatus.BAD_REQUEST);
			}

			// Check if endDate is before the current date
			if (endDate != null && endDate.isAfter(LocalDate.now())) {
				log.error("Invalid endDate: " + endDate);
				return new ResponseEntity<>(new ErrorResponse(
						"Invalid endDate : End Date " + endDate + " must before the current date " + LocalDate.now()),
						HttpStatus.BAD_REQUEST);
			}

			// Check if startDate is before endDate
			if (startDate != null && endDate != null && startDate.isAfter(endDate)) {
				log.error("Invalid date range: startDate is after endDate");
				return new ResponseEntity<>(
						"Invalid date range: startDate " + startDate + " must before endDate " + endDate,
						HttpStatus.BAD_REQUEST);
			}

		}
		log.info("Employee Registartion Started");
		Employee registeredEmployee = employeeService.registerEmployee(employee);
		log.info("Employee Registartion Success");
		return new ResponseEntity<>(registeredEmployee, HttpStatus.CREATED);
	}

	@PutMapping("/employees/update/{id}")
	@PreAuthorize("hasAuthority('ROLE_USER')")
	public ResponseEntity<Employee> updateEmployee(@PathVariable Long id, @RequestBody Employee updatedEmployee) {
		log.info("Employee id {} update started", id);
		Employee updatedEmp = employeeService.updateEmployee(id, updatedEmployee);
		log.info("Employee id {} update  completed with updated info {}", id, updatedEmp.toString());
		return new ResponseEntity<>(updatedEmp, HttpStatus.OK);
	}

	@PutMapping("/employees/addemploymentdetails/{employeeId}")
	@PreAuthorize("hasAuthority('ROLE_USER')")
	public ResponseEntity<String> addEmploymentDetails(@PathVariable Long employeeId,
			@RequestBody EmploymentDetails employmentDetails) {
		try {
			log.info("Add employment details for id {}  started", employeeId);
			employeeService.addEmploymentDetails(employeeId, employmentDetails);
			log.info("Add employment details for id {}  finished", employeeId);
			return new ResponseEntity<>("Employment details added successfully", HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>("Error adding employment details: " + e.getMessage(),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping("/employees/delete/{id}")
	@PreAuthorize("hasAuthority('ROLE_USER')")
	public ResponseEntity<String> deleteEmployee(@PathVariable Long id) {
		log.info("Employee id {} deletion started", id);
		employeeService.deleteEmployee(id);
		log.info("Employee id {} deletion completed", id);
		return new ResponseEntity<>("Employee with id " + id + " has been deleted", HttpStatus.OK);
	}

	@DeleteMapping("/employees/employmentdetaildeletion/{employeeId}/{employmentId}")
	@PreAuthorize("hasAuthority('ROLE_USER')")
	public ResponseEntity<String> deleteEmploymentDetails(@PathVariable Long employeeId,
			@PathVariable Long employmentId) {
		try {
			log.info("Delete employment details for employee id {} and employment id {} started", employeeId,
					employmentId);
			employeeService.deleteEmploymentDetails(employeeId, employmentId);
			employeeService.deleteEmploymentDetailFromDatabase(employmentId);
			log.info("Delete employment details for employee id {} and employment id {} completed", employeeId,
					employmentId);
			return new ResponseEntity<>(
					"Employment details with employmentId " + employmentId + " deleted successfully", HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>("Error deleting employment details: " + e.getMessage(),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
