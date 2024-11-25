package db.controller;

import db.dto.MathFunctionDTO;
import lombok.RequiredArgsConstructor;
import db.service.MathFunctionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/functions")
public class MathFunctionRestController {
    private final MathFunctionService mathFunctionService;

    @PostMapping
    public ResponseEntity<MathFunctionDTO> create(@RequestBody MathFunctionDTO functionDTO) {
        MathFunctionDTO created = mathFunctionService.create(functionDTO);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MathFunctionDTO> read(@PathVariable Integer id) {
        MathFunctionDTO function = mathFunctionService.read(id);

        if (function == null)
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(function);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MathFunctionDTO> update(@PathVariable Integer id, @RequestBody MathFunctionDTO functionDTO) {
        functionDTO.setId(id);
        MathFunctionDTO updated = mathFunctionService.update(functionDTO);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        mathFunctionService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
