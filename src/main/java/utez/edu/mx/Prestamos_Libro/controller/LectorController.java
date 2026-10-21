package utez.edu.mx.Prestamos_Libro.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import utez.edu.mx.Prestamos_Libro.model.Lector;
import utez.edu.mx.Prestamos_Libro.service.LectorService;

@RestController
@RequestMapping("/api/lectores")
public class LectorController {

    private final LectorService lectorService;

    public LectorController(LectorService lectorService) {
        this.lectorService = lectorService;
    }

    @PostMapping
    public ResponseEntity<Lector> crearLector(@RequestBody Lector lector) {
        return ResponseEntity.ok(lectorService.guardar(lector));
    }

    // 4. rehabilitarLector ⭐ (10 pts)
    @PutMapping("/rehabilitar/{id}")
    public ResponseEntity<Lector> rehabilitarLector(@PathVariable Long id) {
        return ResponseEntity.ok(lectorService.rehabilitarLector(id));
    }
}