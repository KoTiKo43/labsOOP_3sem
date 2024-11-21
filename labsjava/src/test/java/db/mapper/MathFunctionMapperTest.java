package db.mapper;

import db.dto.MathFunctionDTO;
import db.entity.MathFunctionEntity;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MathFunctionMapperTest {
    private final MathFunctionMapper mathFunctionMapper = new MathFunctionMapper();
    @Test
    void toEntity() {
        MathFunctionDTO mathFunctionDTO = new MathFunctionDTO(1, "test", 3, 0.0, 2.0);
        MathFunctionEntity mathFunctionEntity = mathFunctionMapper.toEntity(mathFunctionDTO);

        assertEquals(1, mathFunctionEntity.getId());
        assertEquals("test", mathFunctionEntity.getFunctionType());
        assertEquals(3, mathFunctionEntity.getCount());
        assertEquals(0.0, mathFunctionEntity.getXFrom());
        assertEquals(2.0, mathFunctionEntity.getXTo());
    }

    @Test
    void toDTO() {
        MathFunctionEntity mathFunctionEntity = new MathFunctionEntity(1, "test", 3, 0.0, 2.0, null);
        MathFunctionDTO mathFunctionDTO = mathFunctionMapper.toDTO(mathFunctionEntity);

        assertEquals(1, mathFunctionDTO.getId());
        assertEquals("test", mathFunctionDTO.getFunctionType());
        assertEquals(3, mathFunctionDTO.getCount());
        assertEquals(0.0, mathFunctionDTO.getXFrom());
        assertEquals(2.0, mathFunctionDTO.getXTo());
    }
}