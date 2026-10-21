package utez.edu.mx.Prestamos_Libro.dto.request;

import jakarta.validation.constraints.NotNull;

public class SolicitarPrestamoDTO {

    @NotNull(message = "El ID del lector es obligatorio.")
    private Long lectorId;

    @NotNull(message = "El ID del libro es obligatorio.")
    private Long libroId;

    public Long getLectorId() { return lectorId; }
    public void setLectorId(Long lectorId) { this.lectorId = lectorId; }

    public Long getLibroId() { return libroId; }
    public void setLibroId(Long libroId) { this.libroId = libroId; }
}