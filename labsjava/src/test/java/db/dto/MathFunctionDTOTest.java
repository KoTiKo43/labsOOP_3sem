package db.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MathFunctionDTOTest {

    @Test
    public void testMathFunctionDTO() {
        MathFunctionDTO mathFunctionDTO = new MathFunctionDTO(1, "test", 3, 0.0, 2.0);

        assertEquals(1, mathFunctionDTO.getId());
        assertEquals("test", mathFunctionDTO.getFunctionType());
        assertEquals(3, mathFunctionDTO.getCount());
        assertEquals(0.0, mathFunctionDTO.getXFrom());
        assertEquals(2.0, mathFunctionDTO.getXTo());
        assertNotNull(mathFunctionDTO);
    }

    @Test
    public void testMathFunctionDTOGettersAndSetters() {
        MathFunctionDTO mathFunctionDTO = new MathFunctionDTO(1, "test", 3, 0.0, 2.0);

        mathFunctionDTO.setId(2);
        mathFunctionDTO.setFunctionType("test2");
        mathFunctionDTO.setCount(4);
        mathFunctionDTO.setXFrom(1.0);
        mathFunctionDTO.setXTo(3.0);

        assertEquals(2, mathFunctionDTO.getId());
        assertEquals("test2", mathFunctionDTO.getFunctionType());
        assertEquals(4, mathFunctionDTO.getCount());
        assertEquals(1.0, mathFunctionDTO.getXFrom());
        assertEquals(3.0, mathFunctionDTO.getXTo());
    }

}