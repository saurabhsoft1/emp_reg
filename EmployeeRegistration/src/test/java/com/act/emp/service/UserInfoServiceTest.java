package com.act.emp.service;

import com.act.emp.entity.UserInfo;
import com.act.emp.repository.UserInfoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserInfoServiceTest {

	@Mock
	private UserInfoRepository repository;

	@Mock
	private PasswordEncoder encoder;

	@InjectMocks
	private UserInfoService userInfoService;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testUpdateUser() {
		// Arrange
		int userId = 1;
		UserInfo existingUser = new UserInfo();
		existingUser.setId(userId);
		existingUser.setPassword("oldPassword");

		UserInfo updatedUser = new UserInfo();
		updatedUser.setId(userId);
		updatedUser.setPassword("newPassword");

		Mockito.when(repository.existsById(userId)).thenReturn(true);
		Mockito.when(repository.save(any(UserInfo.class))).thenReturn(updatedUser);
		// Mockito.when(repository.encode(updatedUser.getPassword())).thenReturn("encodedNewPassword");

		// Act
		UserInfo result = userInfoService.updateUser(userId, updatedUser);

		// Assert
		assertNotNull(result);
		assertEquals(updatedUser.getId(), result.getId());
		assertNotEquals(existingUser.getPassword(), result.getPassword());
	}

	@Test
	void testDeleteUser() {
		// Arrange
		int userId = 1;

		Mockito.when(repository.existsById(userId)).thenReturn(true);

		// Act
		boolean result = userInfoService.deleteUser(userId);

		// Assert
		assertTrue(result);
		Mockito.verify(repository, Mockito.times(1)).deleteById(userId);
	}

	@Test
	void addUser_UserInfoSavedSuccessfully_ReturnsSuccessMessage() {
		UserInfo userInfo = new UserInfo();
		userInfo.setName("testUser");
		userInfo.setPassword("password");
		when(encoder.encode(userInfo.getPassword())).thenReturn("encodedPassword");

		String result = userInfoService.addUser(userInfo);

		assertEquals("User Added Successfully", result);
		verify(repository, times(1)).save(userInfo);
	}

	@Test
	void getAllUsers_ReturnsListOfUsers() {
		UserInfo user1 = new UserInfo();
		user1.setName("user1");
		user1.setPassword("password");
		UserInfo user2 = new UserInfo();
		user1.setName("user2");
		user1.setPassword("password");
		when(repository.findAll()).thenReturn(Arrays.asList(user1, user2));

		List<UserInfo> users = userInfoService.getAllUsers();

		assertEquals(2, users.size());
		assertTrue(users.contains(user1));
		assertTrue(users.contains(user2));
	}

	@Test
	void getUserById_UserExists_ReturnsUserInfo() {
		int userId = 1;
		UserInfo userInfo = new UserInfo();
		userInfo.setName("testUser");
		userInfo.setPassword("password");
		when(repository.findById(userId)).thenReturn(Optional.of(userInfo));

		UserInfo result = userInfoService.getUserById(userId);

		assertNotNull(result);
		assertEquals(userInfo, result);
	}

}
