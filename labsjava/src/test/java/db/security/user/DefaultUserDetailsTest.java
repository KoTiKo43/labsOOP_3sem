package db.security.user;

import db.entity.UserEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;

import static db.security.user.UserRole.USER;
import static org.junit.jupiter.api.Assertions.*;

public class DefaultUserDetailsTest {

    @Mock
    private UserEntity userEntity;

    private DefaultUserDetails defaultUserDetails;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        userEntity = new UserEntity(1, "testUser", "password", USER);
        defaultUserDetails = DefaultUserDetails.build(userEntity);
    }

    @Test
    public void testConstructorInitializesFields() {
        assertEquals(1, defaultUserDetails.getId());
        assertEquals("testUser", defaultUserDetails.getUsername());
        assertEquals("password", defaultUserDetails.getPassword());
    }

    @Test
    public void testBuildCreatesUserDetailsFromUserEntity() {
        DefaultUserDetails userDetails = DefaultUserDetails.build(userEntity);
        assertNotNull(userDetails);
        assertEquals(userEntity.getUsername(), userDetails.getUsername());
        assertEquals(userEntity.getPassword(), userDetails.getPassword());
    }

    @Test
    public void testGetAuthoritiesReturnsCorrectAuthorities() {
        Collection<? extends GrantedAuthority> authorities = defaultUserDetails.getAuthorities();
        assertNotNull(authorities);
        assertEquals(1, authorities.size());
        assertTrue(authorities.contains(new SimpleGrantedAuthority("ROLE_USER")));
    }

    @Test
    public void testGetUsername_returnsCorrectUsername() {
        assertEquals("testUser", defaultUserDetails.getUsername());
    }

    @Test
    public void testGetPassword_returnsCorrectPassword() {
        assertEquals("password", defaultUserDetails.getPassword());
    }

    @Test
    public void testIsAccountNonExpired_returnsTrue() {
        assertTrue(defaultUserDetails.isAccountNonExpired());
    }

    @Test
    public void testIsAccountNonLocked_returnsTrue() {
        assertTrue(defaultUserDetails.isAccountNonLocked());
    }

    @Test
    public void testIsCredentialsNonExpired_returnsTrue() {
        assertTrue(defaultUserDetails.isCredentialsNonExpired());
    }

    @Test
    public void testIsEnabled_returnsTrue() {
        assertTrue(defaultUserDetails.isEnabled());
    }
}
