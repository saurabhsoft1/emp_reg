package com.act.emp.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.util.ReflectionTestUtils;

import io.jsonwebtoken.Claims;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class JwtServiceTest {

	private JwtService jwtService;

	@BeforeEach
	void setUp() {
		jwtService = new JwtService();
	}

	@Test
	void createToken_shouldGenerateToken() {
		String userName = "testUser";
		String token = jwtService.generateToken(userName);
		assertNotNull(token);
	}

	@Test
	void extractAllClaims_shouldExtractClaims() {
		String userName = "testUser";
		String token = jwtService.generateToken(userName);

		// Use ReflectionTestUtils to invoke the private method
		Claims claims = ReflectionTestUtils.invokeMethod(jwtService, "extractAllClaims", token);

		assertNotNull(claims);
	}

	@Test
	void generateToken_shouldGenerateToken() {
		String userName = "testUser";
		String token = jwtService.generateToken(userName);
		assertNotNull(token);
	}

	@Test
	void extractClaim_shouldExtractClaim() {
		String userName = "testUser";
		String token = jwtService.generateToken(userName);
		assertNotNull(jwtService.extractClaim(token, Claims::getSubject));
	}

	@Test
	void extractUsername_shouldReturnUsername() {
		String userName = "testUser";
		String token = jwtService.generateToken(userName);
		assertEquals(userName, jwtService.extractUsername(token));
	}

	@Test
	void extractExpiration_shouldReturnExpirationDate() {
		String userName = "testUser";
		String token = jwtService.generateToken(userName);
		assertNotNull(jwtService.extractExpiration(token));
	}
}
