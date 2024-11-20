package db.service;

import db.LabsJavaApplication;
import db.dto.MathFunctionDTO;
import db.dto.PointDTO;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import db.entity.PointEntity;
import db.entity.MathFunctionEntity;
import db.repository.MathFunctionRepository;
import db.repository.PointRepository;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@Sql(scripts = {"/sql/test-schema.sql"})
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class PointServiceTest {
    @Autowired
    private PointService pointService;

    @Autowired
    private MathFunctionRepository mathFunctionRepository;

    @Autowired
    private PointRepository pointRepository;

    private MathFunctionEntity function;

    @BeforeEach
    void setUp() {
        function = new MathFunctionEntity(null, "test", 5, 2.0, 4.0, null);
        mathFunctionRepository.save(function);
    }

    @Test
    void testCreate() {
        PointDTO pointDTO = new PointDTO(null, function.getId(), 5.0, 2.0);

        PointDTO point = pointService.create(pointDTO);
        assertNotNull(point);
        assertEquals(5.0, point.getXValue());
        assertEquals(2.0, point.getYValue());
        assertEquals(function.getId(), point.getFunctionId());
    }

    @Test
    void testRead() {
        PointDTO pointDTO = new PointDTO(null, function.getId(), 5.0, 2.0);
        PointDTO point = pointService.create(pointDTO);

        PointDTO readPoint = pointService.read(point.getId());
        assertNotNull(readPoint);
        assertEquals(point.getId(), readPoint.getId());
        assertEquals(point.getXValue(), readPoint.getXValue());
        assertEquals(point.getYValue(), readPoint.getYValue());
        assertEquals(point.getFunctionId(), readPoint.getFunctionId());
    }

    @Test
    void testUpdate() {
        PointDTO pointDTO = new PointDTO(null, function.getId(), 5.0, 2.0);
        PointDTO point = pointService.create(pointDTO);

        point.setXValue(10.0);
        point.setYValue(20.0);
        PointDTO updatedPoint = pointService.update(point);
        assertEquals(10.0, updatedPoint.getXValue());
        assertEquals(20.0, updatedPoint.getYValue());

    }

    @Test
    void testDelete() {
        PointDTO pointDTO = new PointDTO(null, function.getId(), 5.0, 2.0);
        PointDTO point = pointService.create(pointDTO);
        pointService.delete(point.getId());

        assertTrue(pointRepository.findById(point.getId()).isEmpty());
    }
}