package pl.kurs.figures.controllers;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.kurs.figures.services.change.ShapeChangeControllerService;
import pl.kurs.figures.dto.ShapeChangeDTO;
import pl.kurs.figures.command.UpdateShapeCommand;
import pl.kurs.figures.criteria.ShapeSearchCriteria;
import pl.kurs.figures.command.CreateShapeCommand;
import pl.kurs.figures.dto.ShapeDTO;
import pl.kurs.figures.services.shape.ShapeControllerService;

import java.nio.file.AccessDeniedException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/shapes")
@AllArgsConstructor
public class ShapeController {
    private ShapeControllerService shapeControllerService;
    private ShapeChangeControllerService shapeChangeControllerService;

    @PostMapping
    @Operation(summary = "Adds a new shape")
    public ResponseEntity<ShapeDTO> addShape(@RequestBody @Valid CreateShapeCommand command) {
        return ResponseEntity.status(HttpStatus.CREATED).body(shapeControllerService.addShape(command));
    }

    @GetMapping
    @Operation(summary = "Searches for shapes based on criteria")
    public ResponseEntity<List<ShapeDTO>> searchShapes(
            ShapeSearchCriteria criteria,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size
    ) {
        return ResponseEntity.ok(shapeControllerService.getShapes(criteria, page, size));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Edits shape by id")
    public ResponseEntity<ShapeDTO> updateShape(@PathVariable("id") long id, @RequestBody UpdateShapeCommand command) throws AccessDeniedException {
        return ResponseEntity.ok(shapeControllerService.updateShape(id, command));
    }

    @GetMapping("/{id}/changes")
    @Operation(summary = "Returns shape change by id")
    public ResponseEntity<ShapeChangeDTO> getChange(@PathVariable("id") long id) throws AccessDeniedException {
        return ResponseEntity.ok(shapeChangeControllerService.getChangeById(id));
    }
}
