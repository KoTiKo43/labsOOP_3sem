package db.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PointEntityTest {
    @Test
    void testPointEntity() {
        MathFunctionEntity function = new MathFunctionEntity();
        PointEntity pointEntity = new PointEntity(1, function, 5.0, 2.0);
        assertEquals(1, pointEntity.getId());
        assertEquals(5.0, pointEntity.getXValue());
        assertEquals(2.0, pointEntity.getYValue());
    }

    @Test
    void testPointEntityGettersAndSetters() {
        MathFunctionEntity function = new MathFunctionEntity();
        PointEntity pointEntity = new PointEntity(1, function, 5.0, 2.0);
        pointEntity.setId(2);
        pointEntity.setXValue(4.0);
        pointEntity.setYValue(5.0);
        assertEquals(2, pointEntity.getId());
        assertEquals(4.0, pointEntity.getXValue());
        assertEquals(5.0, pointEntity.getYValue());
    }

}