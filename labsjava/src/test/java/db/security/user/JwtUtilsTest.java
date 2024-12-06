package db.security.user;

import db.security.user.JwtUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import static org.junit.jupiter.api.Assertions.*;

public class JwtUtilsTest {

    private JwtUtils jwtUtils;

    @BeforeEach
    void setUp() {
        jwtUtils = new JwtUtils();
        jwtUtils.secret = "9a4f2c8d3b7a1e6f45c8a0b3f267d8b1d4e6f3c8a9d2b5f8e3a9c8b5f6v8a3d99a4f2c8d3b7a1e6f45c8a0b3f267d8b1d4e6f3c8a9d2b5f8e3a9c8b5f6v8a3d9asdasd";
        jwtUtils.expiration = 3600000;
    }

    @Test
    void testGenerateToken() {
        String username = "testUser";
        UserDetails userDetails = User.builder()
                .username(username)
                .password("password")
                .roles("USER")
                .build();

        String token = jwtUtils.generateToken(userDetails);

        assertNotNull(token);
    }

    @Test
    void testGetUsernameFromToken() {
        String username = "testUser";
        UserDetails userDetails = User.builder()
                .username(username)
                .password("password")
                .roles("USER")
                .build();

        String token = jwtUtils.generateToken(userDetails);
        String extractedUsername = jwtUtils.getUsernameFromToken(token);

        assertEquals(username, extractedUsername);
    }

    @Test
    void testValidateToken_withValidToken_returnsTrue() {
        String username = "testUser";
        UserDetails userDetails = User.builder()
                .username(username)
                .password("password")
                .roles("USER")
                .build();

        String token = jwtUtils.generateToken(userDetails);

        boolean isValid = jwtUtils.validateToken(token);

        assertTrue(isValid);
    }

    @Test
    void testValidateToken_withMalformedToken_returnsFalse() {
        String malformedToken = "malformed.token";

        boolean isValid = jwtUtils.validateToken(malformedToken);

        assertFalse(isValid);
    }

    @Test
    void testValidateToken_withInvalidSignature_returnsFalse() {
        String username = "testUser";
        UserDetails userDetails = User.builder()
                .username(username)
                .password("password")
                .roles("USER")
                .build();

        String validToken = jwtUtils.generateToken(userDetails);

        String invalidSignatureToken = validToken.substring(0, validToken.lastIndexOf('.') + 1) + "invalidsignature";

        boolean isValid = jwtUtils.validateToken(invalidSignatureToken);

        assertFalse(isValid);
    }
}
