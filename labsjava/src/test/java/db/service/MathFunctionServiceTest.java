package db.service;

import db.LabsJavaApplication;
import db.dto.MathFunctionDTO;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import db.entity.PointEntity;
import db.entity.MathFunctionEntity;
import db.repository.MathFunctionRepository;
import db.repository.PointRepository;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@Transactional

class MathFunctionServiceTest {
    @Autowired
    private MathFunctionService mathFunctionService;

    @Autowired
    private MathFunctionRepository mathFunctionRepository;

    @Test
    void testCreate() {
        MathFunctionDTO functionDTO = new MathFunctionDTO(null, "test", 3, 0.0, 2.0);

        MathFunctionDTO mathFunctionDTO = mathFunctionService.create(functionDTO);
        assertNotNull(mathFunctionDTO);
        assertEquals("test", mathFunctionDTO.getFunctionType());
        assertEquals(3, mathFunctionDTO.getCount());
        assertEquals(0.0, mathFunctionDTO.getXFrom());
        assertEquals(2.0, mathFunctionDTO.getXTo());
    }

    @Test
    void testRead() {
        MathFunctionDTO functionDTO = new MathFunctionDTO(null, "test", 3, 0.0, 2.0);

        MathFunctionDTO mathFunctionDTO = mathFunctionService.create(functionDTO);

        MathFunctionDTO readFunctionDTO = mathFunctionService.read(mathFunctionDTO.getId());
        assertNotNull(mathFunctionDTO);
        assertEquals(mathFunctionDTO.getId(), readFunctionDTO.getId());
    }

    @Test
    void testUpdate() {
        MathFunctionDTO functionDTO = new MathFunctionDTO(null, "test", 3, 0.0, 2.0);

        MathFunctionDTO mathFunctionDTO = mathFunctionService.create(functionDTO);

        mathFunctionDTO.setFunctionType("test2");
        
        assertNotNull(mathFunctionDTO);
        assertEquals("test2", mathFunctionDTO.getFunctionType());
    }

    @Test
    void testDelete() {
        MathFunctionDTO functionDTO = new MathFunctionDTO(null, "test", 3, 0.0, 2.0);

        MathFunctionDTO mathFunctionDTO = mathFunctionService.create(functionDTO);

        mathFunctionService.delete(mathFunctionDTO.getId());
    }
}