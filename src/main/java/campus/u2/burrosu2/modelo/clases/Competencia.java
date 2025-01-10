package campus.u2.burrosu2.modelo.clases;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Competencia {

    private Integer idCompetencia;
    private String nombre;
    private LocalDate fecha;
    private String lugar;
    private boolean estado;
    private List<Burro> burrosParticipantes;
    private Map<Burro, Integer> puestosBurros;

    public Competencia() {
        this.burrosParticipantes = new ArrayList<>();
        this.puestosBurros = new HashMap<>();
    }

    public Competencia(int idCompetencia, String nombre, LocalDate fecha, String lugar) {
        this.idCompetencia = idCompetencia;
        this.nombre = nombre;
        this.fecha = fecha;
        this.lugar = lugar;
        this.estado = false;
        this.burrosParticipantes = new ArrayList<>();
        this.puestosBurros = new HashMap<>();
    }

    public Competencia(String nombre, LocalDate fecha, String lugar) {
        this.nombre = nombre;
        this.fecha = fecha;
        this.lugar = lugar;
        this.estado = false;
        this.burrosParticipantes = new ArrayList<>();
        this.puestosBurros = new HashMap<>();
    }

    public Integer getIdCompetencia() {
        return idCompetencia;
    }

    public void setIdCompetencia(Integer idCompetencia) {
        this.idCompetencia = idCompetencia;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public String getLugar() {
        return lugar;
    }

    public void setLugar(String lugar) {
        this.lugar = lugar;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public void agregarBurro(Burro burro) {
        burrosParticipantes.add(burro);
    }

    public void eliminarBurro(Burro burro) {
        burrosParticipantes.remove(burro);
    }

    public List<Burro> getBurros() {
        return burrosParticipantes;
    }

    public void asignarPuesto(Burro burro, int puesto) {
        if (!burrosParticipantes.contains(burro)) {
            throw new IllegalArgumentException("El burro no está participando en la competencia.");
        }
        if (puesto <= 0 || puestosBurros.containsValue(puesto)) {
            throw new IllegalArgumentException("Puesto inválido o ya asignado.");
        }
        puestosBurros.put(burro, puesto);
    }

    public int obtenerPuesto(Burro burro) {
        return puestosBurros.getOrDefault(burro, 0);
    }

    public void finalizarCompetencia() {
        this.estado = true;
    }

    @Override
    public String toString() {
        return "Competencia{"
                + "idCompetencia=" + idCompetencia
                + ", nombre='" + nombre + '\''
                + ", fecha=" + fecha
                + ", lugar='" + lugar + '\''
                + ", estado=" + (estado ? "Finalizada" : "En curso")
                 +'}';
    }

}
