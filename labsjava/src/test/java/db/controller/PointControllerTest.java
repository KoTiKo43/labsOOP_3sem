package db.controller;

import db.dto.PointDTO;
import db.service.PointService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PointControllerTest {

    @Mock
    private PointService pointService;

    @InjectMocks
    private PointController controller;

    private PointDTO pointDTO;

    @BeforeEach
    void setUp() {
        pointDTO = new PointDTO(1, 1, 5.0, 2.0);
    }

    @Test
    void findByFunctionExist() {
        List<PointDTO> points = List.of(pointDTO);

        when(pointService.findByFunction(1)).thenReturn(points);

        ResponseEntity<List<PointDTO>> result = controller.findByFunction(1);

        assertEquals(200, result.getStatusCodeValue());
        assertEquals(points, result.getBody());
        verify(pointService).findByFunction(1);
    }

    @Test
    void findByFunctionNotFound() {
        when(pointService.findByFunction(52)).thenReturn(null);

        ResponseEntity<List<PointDTO>> result = controller.findByFunction(52);

        assertEquals(404, result.getStatusCodeValue());
        assertNull(result.getBody());
        verify(pointService).findByFunction(52);
    }

    @Test
    void create() {
        when(pointService.create(pointDTO)).thenReturn(pointDTO);

        ResponseEntity<PointDTO> result = controller.create(pointDTO);

        assertEquals(201, result.getStatusCodeValue());
        assertEquals(pointDTO, result.getBody());
        verify(pointService).create(pointDTO);
    }

    @Test
    void readExist() {
        when(pointService.read(1)).thenReturn(pointDTO);

        ResponseEntity<PointDTO> result = controller.read(1);

        assertEquals(200, result.getStatusCodeValue());
        assertEquals(pointDTO, result.getBody());
        verify(pointService).read(1);
    }


    @Test
    void readNotFound() {
        when(pointService.read(52)).thenReturn(null);

        ResponseEntity<PointDTO> result = controller.read(52);

        assertEquals(404, result.getStatusCodeValue());
        assertNull(result.getBody());
        verify(pointService).read(52);
    }

    @Test
    void update() {
        PointDTO updatedPoint = new PointDTO(1, 1, 52.0, 2.0);

        when(pointService.update(pointDTO)).thenReturn(updatedPoint);

        ResponseEntity<PointDTO> result = controller.update(1, pointDTO);

        assertEquals(200, result.getStatusCodeValue());
        assertEquals(updatedPoint, result.getBody());
        verify(pointService).update(pointDTO);
    }

    @Test
    void delete() {
        ResponseEntity<Void> result = controller.delete(1);

        assertEquals(204, result.getStatusCodeValue());
        verify(pointService).delete(1);
    }
}