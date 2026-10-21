package utez.edu.mx.Prestamos_Libro.service;

import org.springframework.stereotype.Service;
import utez.edu.mx.Prestamos_Libro.model.Libro;
import utez.edu.mx.Prestamos_Libro.repository.LibroRepository;

@Service
public class LibroService {

    private final LibroRepository libroRepository;

    public LibroService(LibroRepository libroRepository) {
        this.libroRepository = libroRepository;
    }

    public Libro guardar(Libro libro) {
        return libroRepository.save(libro);
    }
}