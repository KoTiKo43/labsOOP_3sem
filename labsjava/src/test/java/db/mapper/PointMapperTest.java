package db.mapper;

import db.dto.PointDTO;
import db.entity.MathFunctionEntity;
import db.entity.PointEntity;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PointMapperTest {

    private final PointMapper pointMapper = new PointMapper();

    @Test
    void toEntity() {
        PointDTO pointDTO = new PointDTO(1, 1, 5.0, 2.0);
        PointEntity pointEntity = pointMapper.toEntity(pointDTO);

        assertEquals(1, pointEntity.getId());
        assertEquals(5.0, pointEntity.getXValue());
        assertEquals(2.0, pointEntity.getYValue());
        assertEquals(1, pointEntity.getFunction().getId());
    }

    @Test
    void toDTO() {
        MathFunctionEntity mathFunctionEntity = new MathFunctionEntity(1, "test", 3, 0.0, 2.0, null);
        PointEntity pointEntity = new PointEntity(1, mathFunctionEntity, 5.0, 2.0);
        PointDTO pointDTO = pointMapper.toDTO(pointEntity);

        assertEquals(1, pointDTO.getId());
        assertEquals(5.0, pointDTO.getXValue());
        assertEquals(2.0, pointDTO.getYValue());
        assertEquals(1, pointDTO.getFunctionId());
    }
}