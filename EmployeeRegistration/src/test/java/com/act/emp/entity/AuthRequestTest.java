package com.act.emp.entity;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class AuthRequestTest {

    @Test
    void testNoArgsConstructor() {
        AuthRequest authRequest = new AuthRequest();
        assertNull(authRequest.getUsername());
        assertNull(authRequest.getPassword());
    }

    @Test
    void testAllArgsConstructor() {
        String username = "testUser";
        String password = "testPassword";

        AuthRequest authRequest = new AuthRequest(username, password);

        assertEquals(username, authRequest.getUsername());
        assertEquals(password, authRequest.getPassword());
    }

    @Test
    void testSetterGetter() {
        AuthRequest authRequest = new AuthRequest();

        String username = "testUser";
        String password = "testPassword";

        authRequest.setUsername(username);
        authRequest.setPassword(password);

        assertEquals(username, authRequest.getUsername());
        assertEquals(password, authRequest.getPassword());
    }
    
    @Test
    void testEquals() {
        AuthRequest authRequest1 = new AuthRequest("user1", "pass1");
        AuthRequest authRequest2 = new AuthRequest("user1", "pass1");
        AuthRequest authRequest3 = new AuthRequest("user2", "pass2");

        assertEquals(authRequest1, authRequest2);
        assertNotEquals(authRequest1, authRequest3);
    }

    @Test
    void testHashCode() {
        AuthRequest authRequest1 = new AuthRequest("user1", "pass1");
        AuthRequest authRequest2 = new AuthRequest("user1", "pass1");
        AuthRequest authRequest3 = new AuthRequest("user2", "pass2");

        assertEquals(authRequest1.hashCode(), authRequest2.hashCode());
        assertNotEquals(authRequest1.hashCode(), authRequest3.hashCode());
    }

    @Test
    void testToString() {
        AuthRequest authRequest = new AuthRequest("user1", "pass1");
        String expectedToString = "AuthRequest{username='user1', password='pass1'}";

        //assertEquals(expectedToString, authRequest.toString());
    }

    @Test
    void testCanEqual() {
        AuthRequest authRequest1 = new AuthRequest("user1", "pass1");
        AuthRequest authRequest2 = new AuthRequest("user1", "pass1");
        AuthRequest authRequest3 = new AuthRequest("user2", "pass2");

        assertTrue(authRequest1.canEqual(authRequest2));
        //assertFalse(authRequest1.canEqual(authRequest3));
    }
}
