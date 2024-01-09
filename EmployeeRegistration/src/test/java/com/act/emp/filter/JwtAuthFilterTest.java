package com.act.emp.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;

import com.act.emp.service.JwtService;
import com.act.emp.service.UserInfoService;

import static org.mockito.Mockito.*;

import java.io.IOException;

@ExtendWith(MockitoExtension.class)
class JwtAuthFilterTest {

    @Mock
    private JwtService jwtService;

    @Mock
    private UserInfoService userDetailsService;

    @InjectMocks
    private JwtAuthFilter jwtAuthFilter;

    @Test
    void doFilterInternal_validToken_shouldAuthenticateUser() throws ServletException, IOException {
        // Mock HttpServletRequest, HttpServletResponse, and FilterChain
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        FilterChain filterChain = mock(FilterChain.class);

        // Mock token and username
        String token = "validToken";
        String username = "testUser";

        // Mock the behavior of the jwtService
        when(request.getHeader("Authorization")).thenReturn("Bearer " + token);
        when(jwtService.extractUsername(token)).thenReturn(username);

        // Mock UserDetails
        UserDetails userDetails = mock(UserDetails.class);
        when(userDetailsService.loadUserByUsername(username)).thenReturn(userDetails);

        // Mock the behavior of jwtService.validateToken
        when(jwtService.validateToken(token, userDetails)).thenReturn(true);

        // Call the doFilterInternal method
        jwtAuthFilter.doFilterInternal(request, response, filterChain);

        // Verify that authentication was set in SecurityContextHolder
        verify(userDetailsService, times(1)).loadUserByUsername(username);
        verify(jwtService, times(1)).validateToken(token, userDetails);
       // verify(SecurityContextHolder.getContext(), times(1)).setAuthentication(any(UsernamePasswordAuthenticationToken.class));

        // Verify that filterChain.doFilter was called
        verify(filterChain, times(1)).doFilter(request, response);
    }
}
