package db.repository;

import db.entity.MathFunctionEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;


import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MathFunctionRepositoryTest {

    @Mock
    private MathFunctionRepository mathFunctionRepository;

    private MathFunctionEntity functionEntity;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        functionEntity = new MathFunctionEntity(1, "test", 5, 2.0, 10.0, null);
    }

    @Test
    public void testFindByFunctionType() {
        when(mathFunctionRepository.findByFunctionType("test")).thenReturn(Collections.singletonList(functionEntity));

        List<MathFunctionEntity> functions = mathFunctionRepository.findByFunctionType("test");

        assertEquals(1, functions.size());
        assertEquals("test", functions.get(0).getFunctionType());
        verify(mathFunctionRepository, times(1)).findByFunctionType("test");
    }

}