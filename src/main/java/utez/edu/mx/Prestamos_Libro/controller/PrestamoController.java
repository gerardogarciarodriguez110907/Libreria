package utez.edu.mx.Prestamos_Libro.controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import utez.edu.mx.Prestamos_Libro.dto.request.SolicitarPrestamoDTO;
import utez.edu.mx.Prestamos_Libro.dto.response.PrestamoDTO;
import utez.edu.mx.Prestamos_Libro.service.PrestamoService;

import java.util.List;

@RestController
@RequestMapping("/api/prestamos")
public class PrestamoController {

    private final PrestamoService prestamoService;

    public PrestamoController(PrestamoService prestamoService) {
        this.prestamoService = prestamoService;
    }

    // 1. solicitarPrestamo
    @PostMapping("/solicitar")
    public ResponseEntity<PrestamoDTO> solicitarPrestamo(@Valid @RequestBody SolicitarPrestamoDTO request) {
        return new ResponseEntity<>(prestamoService.solicitarPrestamo(request), HttpStatus.CREATED);
    }

    // 2. devolverLibro
    @PutMapping("/devolver/{id}")
    public ResponseEntity<PrestamoDTO> devolverLibro(@PathVariable Long id) {
        return ResponseEntity.ok(prestamoService.devolverLibro(id));
    }

    // 3. renovarPrestamo
    @PutMapping("/renovar/{id}")
    public ResponseEntity<PrestamoDTO> renovarPrestamo(@PathVariable Long id) {
        return ResponseEntity.ok(prestamoService.renovarPrestamo(id));
    }

    // 5. listarPrestamosActivosLector
    @GetMapping("/lector/{lectorId}/activos")
    public ResponseEntity<List<PrestamoDTO>> listarPrestamosActivosLector(@PathVariable Long lectorId) {
        return ResponseEntity.ok(prestamoService.listarPrestamosActivosLector(lectorId));
    }
}