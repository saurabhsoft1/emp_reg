package com.act.emp.entity;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UserInfoTest {

    @Test
    public void testGetterAndSetterMethods() {
        UserInfo userInfo = new UserInfo();
        userInfo.setId(1);
        userInfo.setName("John Doe");
        userInfo.setEmail("john.doe@example.com");
        userInfo.setPassword("password123");
        userInfo.setRoles("ROLE_USER");

        assertEquals(1, userInfo.getId());
        assertEquals("John Doe", userInfo.getName());
        assertEquals("john.doe@example.com", userInfo.getEmail());
        assertEquals("password123", userInfo.getPassword());
        assertEquals("ROLE_USER", userInfo.getRoles());
    }

    @Test
    public void testEqualsAndHashCodeMethods() {
        UserInfo userInfo1 = new UserInfo(1, "John Doe", "john.doe@example.com", "password123", "ROLE_USER");
        UserInfo userInfo2 = new UserInfo(1, "John Doe", "john.doe@example.com", "password123", "ROLE_USER");
        UserInfo userInfo3 = new UserInfo(2, "Jane Doe", "jane.doe@example.com", "password456", "ROLE_ADMIN");

        assertEquals(userInfo1, userInfo2);
        assertNotEquals(userInfo1, userInfo3);
        assertEquals(userInfo1.hashCode(), userInfo2.hashCode());
        assertNotEquals(userInfo1.hashCode(), userInfo3.hashCode());
    }

    // Add other test methods based on your specific implementation, such as save, findById, delete, etc.
}
