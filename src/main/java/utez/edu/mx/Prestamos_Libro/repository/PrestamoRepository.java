package utez.edu.mx.Prestamos_Libro.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import utez.edu.mx.Prestamos_Libro.model.EstadoPrestamo;
import utez.edu.mx.Prestamos_Libro.model.Lector;
import utez.edu.mx.Prestamos_Libro.model.Prestamo;

import java.util.List;

public interface PrestamoRepository extends JpaRepository<Prestamo, Long> {

    long countByLectorAndEstadoIn(Lector lector, List<EstadoPrestamo> estados);

    List<Prestamo> findByLectorAndEstadoIn(Lector lector, List<EstadoPrestamo> estados);
}