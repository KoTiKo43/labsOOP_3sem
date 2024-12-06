package db.security.user;

import db.service.DefaultUserDetailsService;
import db.security.user.JwtUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.context.SecurityContextHolder;

import java.io.IOException;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class JwtAuthTokenFilterTest {

    @InjectMocks
    private JwtAuthTokenFilter jwtAuthTokenFilter;

    @Mock
    private JwtUtils jwtUtils;

    @Mock
    private DefaultUserDetailsService userDetailsService;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private FilterChain filterChain;

    @Mock
    private UserDetails userDetails;

    private String validJwt = "valid.jwt.token";
    private String invalidJwt = "invalid.jwt.token";

    @BeforeEach
    public void setUp() {
        SecurityContextHolder.clearContext();
        MockitoAnnotations.openMocks(this);
    }


    @Test
    public void testDoFilterInternal_withValidToken_setsAuthentication() throws ServletException, IOException {
        when(request.getHeader("Authorization")).thenReturn("Bearer " + validJwt);
        when(jwtUtils.validateToken(validJwt)).thenReturn(true);
        when(jwtUtils.getUsernameFromToken(validJwt)).thenReturn("testUser");
        when(userDetailsService.loadUserByUsername("testUser")).thenReturn(userDetails);

        jwtAuthTokenFilter.doFilterInternal(request, response, filterChain);
        verify(jwtUtils).validateToken(validJwt);
        verify(jwtUtils).getUsernameFromToken(validJwt);
        verify(userDetailsService).loadUserByUsername("testUser");
        assertNotNull(SecurityContextHolder.getContext().getAuthentication(), "Authentication should be set");
        assertEquals("userDetails", SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
    }


    @Test
    public void testDoFilterInternal_withInvalidToken_doesNotSetAuthentication() throws ServletException, IOException {
        when(request.getHeader("Authorization")).thenReturn("Bearer " + invalidJwt);
        when(jwtUtils.validateToken(invalidJwt)).thenReturn(false);

        jwtAuthTokenFilter.doFilterInternal(request, response, filterChain);

        verify(jwtUtils).validateToken(invalidJwt);
        verifyNoInteractions(userDetailsService);
        assertNull(SecurityContextHolder.getContext().getAuthentication());
    }

    @Test
    public void testDoFilterInternal_withNoToken_doesNotSetAuthentication() throws ServletException, IOException {
        when(request.getHeader("Authorization")).thenReturn(null);

        jwtAuthTokenFilter.doFilterInternal(request, response, filterChain);

        verify(jwtUtils, never()).validateToken(any());
        verifyNoInteractions(userDetailsService);
        assertNull(SecurityContextHolder.getContext().getAuthentication());
    }

    @Test
    public void testDoFilterInternal_withBearerTokenButNoUsername_doesNotSetAuthentication() throws ServletException, IOException {
        when(request.getHeader("Authorization")).thenReturn("Bearer " + validJwt);
        when(jwtUtils.validateToken(validJwt)).thenReturn(true);
        when(jwtUtils.getUsernameFromToken(validJwt)).thenReturn(null);
        when(userDetailsService.loadUserByUsername(null)).thenReturn(null);  // Mocking null return

        jwtAuthTokenFilter.doFilterInternal(request, response, filterChain);

        verify(jwtUtils).validateToken(validJwt);
        verify(jwtUtils).getUsernameFromToken(validJwt);
        verify(userDetailsService).loadUserByUsername(null);
        assertNull(SecurityContextHolder.getContext().getAuthentication());
    }

}
