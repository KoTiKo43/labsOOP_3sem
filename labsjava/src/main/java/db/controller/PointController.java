package db.controller;

import db.dto.PointDTO;
import lombok.RequiredArgsConstructor;
import db.service.PointService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/points")
public class PointController {
    private final PointService pointService;

    @GetMapping("/function/{id}")
    public ResponseEntity<List<PointDTO>> findByFunction(@PathVariable Integer id) {
        List<PointDTO> points = pointService.findByFunction(id);
        if (points == null || points.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(points);
    }


    @PostMapping
    public ResponseEntity<PointDTO> create(@RequestBody PointDTO pointDTO) {
        PointDTO created = pointService.create(pointDTO);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PointDTO> read(@PathVariable Integer id) {
        PointDTO point = pointService.read(id);

        if (point == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(point);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PointDTO> update(@PathVariable Integer id, @RequestBody PointDTO pointDTO) {
        pointDTO.setId(id);
        PointDTO updated = pointService.update(pointDTO);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        pointService.delete(id);
        return ResponseEntity.noContent().build();
    }


}
