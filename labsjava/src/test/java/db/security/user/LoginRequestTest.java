package db.security.user;

import jakarta.validation.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Set;

public class LoginRequestTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void testValidLoginRequest() {
        LoginRequest validRequest = new LoginRequest("validUsername", "validPassword");

        Set<ConstraintViolation<LoginRequest>> violations = validator.validate(validRequest);

        assertTrue(violations.isEmpty());
    }
}
