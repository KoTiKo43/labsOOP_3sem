package db.security.user;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class JwtResponseTest {

    @Test
    public void testJwtResponseConstructor() {
        String token = "sample.jwt.token";
        JwtResponse jwtResponse = new JwtResponse(token);

        assertEquals(token, jwtResponse.getToken());
    }

    @Test
    public void testJwtResponseDefaultConstructor() {
        JwtResponse jwtResponse = new JwtResponse();

        assertNull(jwtResponse.getToken());
    }

    @Test
    public void testSetToken() {
        JwtResponse jwtResponse = new JwtResponse();
        String token = "new.token";
        jwtResponse.setToken(token);

        assertEquals(token, jwtResponse.getToken());
    }

    @Test
    public void testGetToken() {
        String token = "token.get";
        JwtResponse jwtResponse = new JwtResponse(token);

        assertEquals(token, jwtResponse.getToken());
    }

    @Test
    public void testJwtResponseEquality() {
        String token = "token1";
        JwtResponse jwtResponse1 = new JwtResponse(token);
        JwtResponse jwtResponse2 = new JwtResponse(token);

        assertEquals(jwtResponse1, jwtResponse2);
    }

    @Test
    public void testJwtResponseHashCode() {
        String token = "token2";
        JwtResponse jwtResponse1 = new JwtResponse(token);
        JwtResponse jwtResponse2 = new JwtResponse(token);

        assertEquals(jwtResponse1.hashCode(), jwtResponse2.hashCode());
    }
}
