package db.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PointDTOTest {

    @Test
    void testPointDTO() {
        PointDTO pointDTO = new PointDTO(1, 1, 1.0, 2.0);
        assertEquals(1, pointDTO.getXValue());
        assertEquals(2, pointDTO.getYValue());
    }

    @Test
    void testPointDTOGettersAndSetters() {
        PointDTO pointDTO = new PointDTO();
        pointDTO.setXValue(1.0);
        pointDTO.setYValue(2.0);
        assertEquals(1.0, pointDTO.getXValue());
        assertEquals(2.0, pointDTO.getYValue());
    }

}