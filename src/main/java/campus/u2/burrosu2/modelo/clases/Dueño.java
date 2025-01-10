package campus.u2.burrosu2.modelo.clases;

import java.util.ArrayList;
import java.util.List;

public class Dueño extends Persona {

    private Integer idDueño;
    private List<Burro> burros;

    public Dueño() {
        this.burros = new ArrayList<>();
    }

    public Dueño(Integer idDueño, String nombre, String cedula) {
        super(nombre, cedula);
        this.idDueño = idDueño;
        this.burros = new ArrayList<>();
    }

    public Dueño(String nombre, String cedula) {
        super(nombre, cedula);
        this.burros = new ArrayList<>();
    }

    public Dueño(Integer idDueño) {
        this.idDueño = idDueño;
    }

    public Integer getIdDueño() {
        return idDueño;
    }

    public void setIdDueño(Integer idDueño) {
        this.idDueño = idDueño;
    }

    public List<Burro> getBurros() {
        return burros;
    }

    public void setBurros(List<Burro> burros) {
        this.burros = burros;
    }

    public void agregarBurro(Burro burro) {
        burros.add(burro);
    }

    public void eliminarBurro(Burro burro) {
        burros.remove(burro);
    }

    @Override
    public String toString() {
        return "Due\u00f1o{" + "idDue\u00f1o=" + idDueño + " " + super.toString() + '}';
    }

}
