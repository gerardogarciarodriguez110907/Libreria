package utez.edu.mx.Prestamos_Libro.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import utez.edu.mx.Prestamos_Libro.exception.ResourceNotFoundException;
import utez.edu.mx.Prestamos_Libro.model.Lector;
import utez.edu.mx.Prestamos_Libro.repository.LectorRepository;

@Service
public class LectorService {

    private final LectorRepository lectorRepository;

    public LectorService(LectorRepository lectorRepository) {
        this.lectorRepository = lectorRepository;
    }

    // 4. rehabilitarLector  (10 pts)
    @Transactional
    public Lector rehabilitarLector(Long lectorId) {
        Lector lector = lectorRepository.findById(lectorId)
                .orElseThrow(() -> new ResourceNotFoundException("Lector no encontrado con ID: " + lectorId));

        lector.setActivo(true);
        return lectorRepository.save(lector);
    }

    public Lector guardar(Lector lector) {
        return lectorRepository.save(lector);
    }
}