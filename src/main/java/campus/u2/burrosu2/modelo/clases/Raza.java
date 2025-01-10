package campus.u2.burrosu2.modelo.clases;

public class Raza {
    
    private Integer idRaza;
    private String nombre;

    public Raza() {
    }

    public Raza(Integer idRaza, String nombre) {
        this.idRaza = idRaza;
        this.nombre = nombre;
    }

    public Integer getIdRaza() {
        return idRaza;
    }

    public void setIdRaza(Integer idRaza) {
        this.idRaza = idRaza;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return "Raza{" + "idRaza=" + idRaza + ", nombre=" + nombre + '}';
    }
    
    
}
