package db.repository;

import db.LabsJavaApplication;
import db.entity.MathFunctionEntity;
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
@Sql(scripts = {"/sql/test-schema.sql", "/sql/math_functions.sql"})
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class MathFunctionRepositoryIT {

    @Autowired
    private MathFunctionRepository mathFunctionRepository;


    @Test
    public void testFindByFunctionType_ReturnsFunctionList() {
        List<MathFunctionEntity> functions = this.mathFunctionRepository.findByFunctionType("test");

        assertEquals(1, functions.size());
        assertEquals("test", functions.get(0).getFunctionType());
    }
}
