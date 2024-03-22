package pl.kurs.figures.controllers;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.kurs.figures.criteria.ShapeSearchCriteria;
import pl.kurs.figures.command.CreateShapeCommand;
import pl.kurs.figures.dto.ShapeDTO;
import pl.kurs.figures.service.ShapeControllerService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/shapes")
@AllArgsConstructor
public class ShapeController {
    private ShapeControllerService shapeControllerService;

    @PostMapping
    @Operation(summary = "Adds a new shape")
    public ResponseEntity<ShapeDTO> addShape(@RequestBody @Valid CreateShapeCommand command) {
        return ResponseEntity.status(HttpStatus.CREATED).body(shapeControllerService.addShape(command));
    }

    @GetMapping
    @Operation(summary = "Searches for shapes based on criteria")
    public ResponseEntity<List<ShapeDTO>> searchShapes(ShapeSearchCriteria criteria){
        return ResponseEntity.ok(shapeControllerService.getShapes(criteria));
    }

}
