package db.service;

import db.entity.UserEntity;
import db.security.user.DefaultUserDetails;
import db.security.user.UserRole;
import db.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.EnumSet;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DefaultUserDetailsServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private DefaultUserDetailsService defaultUserDetailsService;

    @Test
    void testLoadUserByUsername_UserFound() {
        String username = "testUser";
        UserEntity user = new UserEntity();
        user.setUsername(username);
        user.setPassword("password");
        user.setRole(UserRole.USER);
        user.setId(1);
        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));

        UserDetails userDetails = defaultUserDetailsService.loadUserByUsername(username);

        assertNotNull(userDetails);
        assertEquals(username, userDetails.getUsername());

        assertTrue(userDetails.getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_USER")));


        verify(userRepository, times(1)).findByUsername(username);
    }

    @Test
    void testLoadUserByUsername_UserNotFound() {
        String username = "nonExistentUser";

        when(userRepository.findByUsername(username)).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, () -> {
            defaultUserDetailsService.loadUserByUsername(username);
        });

        verify(userRepository, times(1)).findByUsername(username);
    }
}
