package utez.edu.mx.Prestamos_Libro.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import utez.edu.mx.Prestamos_Libro.model.Lector;

public interface LectorRepository extends JpaRepository<Lector, Long> {
}