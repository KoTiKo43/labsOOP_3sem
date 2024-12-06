package db.repository;

import static db.security.user.UserRole.USER;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import db.entity.UserEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

public class UserRepositoryTest {

    @Mock
    private UserRepository userRepository;

    private UserEntity userEntity;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        userEntity = new UserEntity(1, "testUser", "password", USER);
    }

    @Test
    public void testFindByUsername() {
        when(userRepository.findByUsername("testUser")).thenReturn(Optional.of(userEntity));

        Optional<UserEntity> user = userRepository.findByUsername("testUser");

        assertTrue(user.isPresent());
        assertEquals(userEntity.getUsername(), user.get().getUsername());
        assertEquals(userEntity.getId(), user.get().getId());
        verify(userRepository, times(1)).findByUsername("testUser");
    }

    @Test
    public void testFindByUsernameNotFound() {
        when(userRepository.findByUsername("nonExistentUser")).thenReturn(Optional.empty());

        Optional<UserEntity> user = userRepository.findByUsername("nonExistentUser");

        assertFalse(user.isPresent());
        verify(userRepository, times(1)).findByUsername("nonExistentUser");
    }
}
