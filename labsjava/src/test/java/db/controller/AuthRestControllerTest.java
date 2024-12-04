package db.controller;

import db.entity.UserEntity;
import db.repository.UserRepository;
import db.security.user.JwtResponse;
import db.security.user.LoginRequest;
import db.security.user.UserRole;
import db.security.user.JwtUtils;
import db.service.DefaultUserDetailsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthRestControllerTest {

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtUtils jwtUtils;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private UserRepository userRepository;

    @Mock
    private DefaultUserDetailsService userDetailsService;

    @InjectMocks
    private AuthRestController authRestController;

    private LoginRequest loginRequest;
    private UserEntity user;
    private UserDetails userDetails;
    private String jwtToken;

    @BeforeEach
    void setUp() {
        loginRequest = new LoginRequest("testuser", "password123");
        user = new UserEntity(null, "testuser", "password123", UserRole.USER);
        userDetails = mock(UserDetails.class);
        jwtToken = "mockJwtToken";
    }

    @Test
    void login_Success() {
        when(userDetailsService.loadUserByUsername(loginRequest.getUsername())).thenReturn(userDetails);
        when(jwtUtils.generateToken(userDetails)).thenReturn(jwtToken);

        ResponseEntity<?> response = authRestController.login(loginRequest);

        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertTrue(response.getBody() instanceof JwtResponse);
        assertEquals(jwtToken, ((JwtResponse) response.getBody()).getToken());
        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(userDetailsService).loadUserByUsername(loginRequest.getUsername());
        verify(jwtUtils).generateToken(userDetails);
    }

    @Test
    void register_Success() {
        when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.empty());
        when(passwordEncoder.encode("password123")).thenReturn("encodedPassword"); // Match exact input

        ResponseEntity<?> response = authRestController.register(user);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("User registered successfully!", response.getBody());

        verify(passwordEncoder).encode("password123"); // Verify the correct input
        verify(userRepository).save(user);
    }



    @Test
    void register_UsernameTaken() {
        when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.of(user));

        ResponseEntity<?> response = authRestController.register(user);

        assertEquals(400, response.getStatusCodeValue());
        assertEquals("Error: Username is already taken!", response.getBody());
        verify(userRepository, never()).save(any(UserEntity.class));
    }

    @Test
    void register_NullRole_ShouldAssignDefaultRole() {
        user.setRole(null);

        when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(user.getPassword())).thenReturn("encodedPassword");

        ResponseEntity<?> response = authRestController.register(user);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("User registered successfully!", response.getBody());
        assertEquals(UserRole.USER, user.getRole());
        verify(userRepository).save(user);
    }
}
