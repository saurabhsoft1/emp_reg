package com.act.emp.service;

import org.junit.jupiter.api.Test;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.act.emp.entity.UserInfo;

import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class UserInfoDetailsTest {

    @Test
    public void testUserInfoDetailsConstructor() {
        // Given
        UserInfo userInfo = new UserInfo();
        userInfo.setName("testUser");
        userInfo.setPassword("testPassword");
        userInfo.setRoles("ROLE_USER,ROLE_ADMIN");

        // When
        UserInfoDetails userDetails = new UserInfoDetails(userInfo);

        // Then
        assertEquals("testUser", userDetails.getUsername());
        assertEquals("testPassword", userDetails.getPassword());

        List<GrantedAuthority> authorities = (List<GrantedAuthority>) userDetails.getAuthorities();
        assertEquals(2, authorities.size());
        assertTrue(authorities.contains(new SimpleGrantedAuthority("ROLE_USER")));
        assertTrue(authorities.contains(new SimpleGrantedAuthority("ROLE_ADMIN")));

        assertTrue(userDetails.isAccountNonExpired());
        assertTrue(userDetails.isAccountNonLocked());
        assertTrue(userDetails.isCredentialsNonExpired());
        assertTrue(userDetails.isEnabled());
    }

    // Add additional test cases for other methods if needed
}