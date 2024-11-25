package db.controller;

import db.dto.MathFunctionDTO;
import db.service.MathFunctionService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MathFunctionRestControllerTest {

    @Mock
    private MathFunctionService mathFunctionService;

    @InjectMocks
    private MathFunctionRestController controller;

    private MathFunctionDTO functionDTO;

    @BeforeEach
    void setUp() {
        functionDTO = new MathFunctionDTO(1, "test", 5, 2.0, 4.0);
    }


    @Test
    void create() {
        when(mathFunctionService.create(functionDTO)).thenReturn(functionDTO);

        ResponseEntity<MathFunctionDTO> result = controller.create(functionDTO);

        assertEquals(201, result.getStatusCodeValue());
        assertEquals(functionDTO, result.getBody());
        verify(mathFunctionService).create(functionDTO);
    }

    @Test
    void readExist() {
        when(mathFunctionService.read(1)).thenReturn(functionDTO);

        ResponseEntity<MathFunctionDTO> result = controller.read(1);

        assertEquals(200, result.getStatusCodeValue());
        assertEquals(functionDTO, result.getBody());
        verify(mathFunctionService).read(1);
    }

    @Test
    void readNotFound() {
        when(mathFunctionService.read(52)).thenReturn(null);

        ResponseEntity<MathFunctionDTO> result = controller.read(52);

        assertEquals(404, result.getStatusCodeValue());
        assertNull(result.getBody());
        verify(mathFunctionService).read(52);
    }

    @Test
    void update() {
        MathFunctionDTO updatedFunction = new MathFunctionDTO(1, "test2", 5, 2.0, 4.0);

        when(mathFunctionService.update(functionDTO)).thenReturn(updatedFunction);

        ResponseEntity<MathFunctionDTO> result = controller.update(1, functionDTO);

        assertEquals(200, result.getStatusCodeValue());
        assertEquals(updatedFunction, result.getBody());
        verify(mathFunctionService).update(functionDTO);
    }

    @Test
    void delete() {
        ResponseEntity<Void> result = controller.delete(1);

        assertEquals(204, result.getStatusCodeValue());
        verify(mathFunctionService).delete(1);

    }
}