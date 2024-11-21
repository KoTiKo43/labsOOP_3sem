package db.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
class MathFunctionEntityTest {

    @Test
    void testMathFunctionEntity() {
        MathFunctionEntity mathFunctionEntity = new MathFunctionEntity(1, "test", 5, 2.0, 4.0, null);
        assertEquals(1, mathFunctionEntity.getId());
        assertEquals("test", mathFunctionEntity.getFunctionType());
        assertEquals(5, mathFunctionEntity.getCount());
        assertEquals(2.0, mathFunctionEntity.getXFrom());
        assertEquals(4.0, mathFunctionEntity.getXTo());
    }

    @Test
    void testMathFunctionEntityGettersAndSetters() {
        MathFunctionEntity mathFunctionEntity = new MathFunctionEntity(1, "test", 5, 2.0, 4.0, null);
        mathFunctionEntity.setId(2);
        mathFunctionEntity.setFunctionType("test2");
        mathFunctionEntity.setCount(4);
        mathFunctionEntity.setXFrom(1.0);
        mathFunctionEntity.setXTo(3.0);

        assertEquals(2, mathFunctionEntity.getId());
        assertEquals("test2", mathFunctionEntity.getFunctionType());
        assertEquals(4, mathFunctionEntity.getCount());
        assertEquals(1.0, mathFunctionEntity.getXFrom());
        assertEquals(3.0, mathFunctionEntity.getXTo());
    }
  
}