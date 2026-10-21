package utez.edu.mx.Prestamos_Libro.dto.response;

import utez.edu.mx.Prestamos_Libro.model.EstadoPrestamo;
import utez.edu.mx.Prestamos_Libro.model.Prestamo;

import java.time.LocalDate;

public class PrestamoDTO {

    private Long id;
    private Long lectorId;
    private String nombreLector;
    private Long libroId;
    private String tituloLibro;
    private LocalDate fechaPrestamo;
    private LocalDate fechaDevolucionMaxima;
    private EstadoPrestamo estado;

    public static PrestamoDTO fromEntity(Prestamo prestamo) {
        PrestamoDTO dto = new PrestamoDTO();
        dto.setId(prestamo.getId());
        dto.setLectorId(prestamo.getLector().getId());
        dto.setNombreLector(prestamo.getLector().getNombre());
        dto.setLibroId(prestamo.getLibro().getId());
        dto.setTituloLibro(prestamo.getLibro().getTitulo());
        dto.setFechaPrestamo(prestamo.getFechaPrestamo());
        dto.setFechaDevolucionMaxima(prestamo.getFechaDevolucionMaxima());
        dto.setEstado(prestamo.getEstado());
        return dto;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getLectorId() { return lectorId; }
    public void setLectorId(Long lectorId) { this.lectorId = lectorId; }
    public String getNombreLector() { return nombreLector; }
    public void setNombreLector(String nombreLector) { this.nombreLector = nombreLector; }
    public Long getLibroId() { return libroId; }
    public void setLibroId(Long libroId) { this.libroId = libroId; }
    public String getTituloLibro() { return tituloLibro; }
    public void setTituloLibro(String tituloLibro) { this.tituloLibro = tituloLibro; }
    public LocalDate getFechaPrestamo() { return fechaPrestamo; }
    public void setFechaPrestamo(LocalDate fechaPrestamo) { this.fechaPrestamo = fechaPrestamo; }
    public LocalDate getFechaDevolucionMaxima() { return fechaDevolucionMaxima; }
    public void setFechaDevolucionMaxima(LocalDate fechaDevolucionMaxima) { this.fechaDevolucionMaxima = fechaDevolucionMaxima; }
    public EstadoPrestamo getEstado() { return estado; }
    public void setEstado(EstadoPrestamo estado) { this.estado = estado; }
}