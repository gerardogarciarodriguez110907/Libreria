package utez.edu.mx.Prestamos_Libro.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import utez.edu.mx.Prestamos_Libro.model.Libro;

public interface LibroRepository extends JpaRepository<Libro, Long> {
}