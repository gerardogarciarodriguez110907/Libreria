package utez.edu.mx.Prestamos_Libro.model;

import jakarta.persistence.*;

@Entity
@Table(name = "lectores")
public class Lector {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private boolean activo = true;

    @Column(nullable = false)
    private boolean membresiaPremium = false;

    public Lector() {}

    public Lector(String nombre, boolean activo, boolean membresiaPremium) {
        this.nombre = nombre;
        this.activo = activo;
        this.membresiaPremium = membresiaPremium;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public boolean isActivo() { return activo; }
    public void setActivo(boolean activo) { this.activo = activo; }

    public boolean isMembresiaPremium() { return membresiaPremium; }
    public void setMembresiaPremium(boolean membresiaPremium) { this.membresiaPremium = membresiaPremium; }
}