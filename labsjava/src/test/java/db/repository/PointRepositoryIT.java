package db.repository;

import db.LabsJavaApplication;
import db.entity.MathFunctionEntity;
import db.entity.PointEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ContextConfiguration(classes = LabsJavaApplication.class)
@Sql(scripts = {"/sql/test-schema.sql", "/sql/points.sql"})
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)

class PointRepositoryIT {

    @Autowired
    private PointRepository pointRepository;

    @Autowired
    private MathFunctionRepository mathFunctionRepository;

    @BeforeEach
    public void setup() {
        MathFunctionEntity functionEntity = new MathFunctionEntity();
        functionEntity.setId(1);
        functionEntity.setFunctionType("test_function");
        functionEntity.setCount(5);
        functionEntity.setXFrom(2D);
        functionEntity.setXTo(10D);
        mathFunctionRepository.save(functionEntity);
    }

    @Test
    public void testFindByFunctionEntity() {
        MathFunctionEntity functionEntity = mathFunctionRepository.findById(1).orElseThrow();

        List<PointEntity> points = pointRepository.findByFunction(functionEntity);

        assertEquals(4, points.size());
        assertEquals(1, points.get(0).getId());
    }

}