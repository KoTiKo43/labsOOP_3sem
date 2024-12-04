package db.entity;

import db.security.user.UserRole;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserEntityTest {

    @Test
    void testUserEntityConstructor() {
        UserEntity user = new UserEntity(1, "testUser", "password123", UserRole.USER);

        assertEquals(1, user.getId());
        assertEquals("testUser", user.getUsername());
        assertEquals("password123", user.getPassword());
        assertEquals(UserRole.USER, user.getRole());
    }

    @Test
    void testGettersAndSetters() {
        UserEntity user = new UserEntity();
        user.setId(2);
        user.setUsername("newUser");
        user.setPassword("newPassword");
        user.setRole(UserRole.ADMIN);

        assertEquals(2, user.getId());
        assertEquals("newUser", user.getUsername());
        assertEquals("newPassword", user.getPassword());
        assertEquals(UserRole.ADMIN, user.getRole());
    }

}
