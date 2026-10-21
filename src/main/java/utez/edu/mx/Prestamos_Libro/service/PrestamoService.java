package utez.edu.mx.Prestamos_Libro.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import utez.edu.mx.Prestamos_Libro.dto.request.SolicitarPrestamoDTO;
import utez.edu.mx.Prestamos_Libro.dto.response.PrestamoDTO;
import utez.edu.mx.Prestamos_Libro.exception.BadRequestException;
import utez.edu.mx.Prestamos_Libro.exception.ResourceNotFoundException;
import utez.edu.mx.Prestamos_Libro.model.EstadoPrestamo;
import utez.edu.mx.Prestamos_Libro.model.Lector;
import utez.edu.mx.Prestamos_Libro.model.Libro;
import utez.edu.mx.Prestamos_Libro.model.Prestamo;
import utez.edu.mx.Prestamos_Libro.repository.LectorRepository;
import utez.edu.mx.Prestamos_Libro.repository.LibroRepository;
import utez.edu.mx.Prestamos_Libro.repository.PrestamoRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PrestamoService {

    private final PrestamoRepository prestamoRepository;
    private final LectorRepository lectorRepository;
    private final LibroRepository libroRepository;

    public PrestamoService(PrestamoRepository prestamoRepository,
                           LectorRepository lectorRepository,
                           LibroRepository libroRepository) {
        this.prestamoRepository = prestamoRepository;
        this.lectorRepository = lectorRepository;
        this.libroRepository = libroRepository;
    }

    // 1. solicitarPrestamo
    @Transactional
    public PrestamoDTO solicitarPrestamo(SolicitarPrestamoDTO request) {
        Lector lector = lectorRepository.findById(request.getLectorId())
                .orElseThrow(() -> new ResourceNotFoundException("El lector no existe."));

        if (!lector.isActivo()) {
            throw new BadRequestException("El lector está inactivo y no puede solicitar préstamos.");
        }

        Libro libro = libroRepository.findById(request.getLibroId())
                .orElseThrow(() -> new ResourceNotFoundException("El libro no existe."));

        if (libro.getEjemplaresDisponibles() < 1) {
            throw new BadRequestException("El libro no tiene ejemplares disponibles actualmente.");
        }

        long prestamosActivos = prestamoRepository.countByLectorAndEstadoIn(
                lector, List.of(EstadoPrestamo.PRESTADO, EstadoPrestamo.VENCIDO)
        );

        int limitePrestamos = lector.isMembresiaPremium() ? 5 : 3;

        if (prestamosActivos >= limitePrestamos) {
            throw new BadRequestException("El lector ha alcanzado el límite máximo de préstamos activos (" + limitePrestamos + ").");
        }

        libro.setEjemplaresDisponibles(libro.getEjemplaresDisponibles() - 1);
        libroRepository.save(libro);

        LocalDate hoy = LocalDate.now();
        Prestamo nuevoPrestamo = new Prestamo(
                lector,
                libro,
                hoy,
                hoy.plusDays(14),
                EstadoPrestamo.PRESTADO
        );

        return PrestamoDTO.fromEntity(prestamoRepository.save(nuevoPrestamo));
    }

    // 2. devolverLibro
    @Transactional
    public PrestamoDTO devolverLibro(Long prestamoId) {
        Prestamo prestamo = prestamoRepository.findById(prestamoId)
                .orElseThrow(() -> new ResourceNotFoundException("El préstamo con ID " + prestamoId + " no existe."));

        if (prestamo.getEstado() == EstadoPrestamo.DEVUELTO) {
            throw new BadRequestException("El libro ya ha sido devuelto anteriormente.");
        }

        LocalDate hoy = LocalDate.now();

        if (hoy.isAfter(prestamo.getFechaDevolucionMaxima())) {
            Lector lector = prestamo.getLector();
            lector.setActivo(false);
            lectorRepository.save(lector);
        }

        prestamo.setEstado(EstadoPrestamo.DEVUELTO);

        Libro libro = prestamo.getLibro();
        libro.setEjemplaresDisponibles(libro.getEjemplaresDisponibles() + 1);
        libroRepository.save(libro);

        return PrestamoDTO.fromEntity(prestamoRepository.save(prestamo));
    }

    // 3. renovarPrestamo
    @Transactional
    public PrestamoDTO renovarPrestamo(Long prestamoId) {
        Prestamo prestamo = prestamoRepository.findById(prestamoId)
                .orElseThrow(() -> new ResourceNotFoundException("El préstamo con ID " + prestamoId + " no existe."));

        if (prestamo.getEstado() == EstadoPrestamo.VENCIDO || prestamo.getEstado() == EstadoPrestamo.DEVUELTO) {
            throw new BadRequestException("No se puede renovar un préstamo en estado " + prestamo.getEstado() + ".");
        }

        prestamo.setFechaDevolucionMaxima(prestamo.getFechaDevolucionMaxima().plusDays(7));

        return PrestamoDTO.fromEntity(prestamoRepository.save(prestamo));
    }

    // 5. listarPrestamosActivosLector
    @Transactional(readOnly = true)
    public List<PrestamoDTO> listarPrestamosActivosLector(Long lectorId) {
        Lector lector = lectorRepository.findById(lectorId)
                .orElseThrow(() -> new ResourceNotFoundException("El lector no existe."));

        List<Prestamo> prestamosActivos = prestamoRepository.findByLectorAndEstadoIn(
                lector, List.of(EstadoPrestamo.PRESTADO, EstadoPrestamo.VENCIDO)
        );

        return prestamosActivos.stream()
                .map(PrestamoDTO::fromEntity)
                .collect(Collectors.toList());
    }
}