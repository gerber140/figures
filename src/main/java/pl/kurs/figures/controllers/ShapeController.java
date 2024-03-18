package pl.kurs.figures.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.kurs.figures.command.CreateShapeCommand;
import pl.kurs.figures.dto.ShapeDTO;
import pl.kurs.figures.model.Shape;
import pl.kurs.figures.model.Square;
import pl.kurs.figures.service.ShapeControllerService;
import pl.kurs.figures.service.ShapeFactory;
import pl.kurs.figures.service.ShapeService;
import org.modelmapper.ModelMapper;

@RestController
@RequestMapping("/api/v1/shapes")
@AllArgsConstructor
public class ShapeController {
    private ShapeControllerService shapeControllerService;

    @PostMapping
    public ResponseEntity<ShapeDTO> addShape(@RequestBody CreateShapeCommand command) {
        return ResponseEntity.ok(shapeControllerService.addShape(command));
    }
}
