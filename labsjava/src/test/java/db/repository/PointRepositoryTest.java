package db.repository;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import db.entity.MathFunctionEntity;
import db.entity.PointEntity;

import java.util.Collections;
import java.util.List;

public class PointRepositoryTest {

    @Mock
    private PointRepository pointRepository;

    private PointEntity pointEntity;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        MathFunctionEntity functionEntity = new MathFunctionEntity(1, "test", 5, 2.0, 10.0, null);
        pointEntity = new PointEntity(1, functionEntity, 5.0, 10.0);
    }

    @Test
    public void testFindByFunctionEntity() {
        MathFunctionEntity functionEntity = new MathFunctionEntity(1, "test", 5, 2.0, 10.0, null);
        when(pointRepository.findByFunction(functionEntity)).thenReturn(Collections.singletonList(pointEntity));

        List<PointEntity> points = pointRepository.findByFunction(functionEntity);

        assertEquals(1, points.size());
        assertEquals(pointEntity.getId(), points.get(0).getId());
        verify(pointRepository, times(1)).findByFunction(functionEntity);
    }
}