package campus.u2.burrosu2.modelo.clases;

public class Burro {
    
    private Integer id;
    private String nombre;
    private Integer edad;
    private Raza raza;
    private Dueño dueño;

    public Burro(Integer id, String nombre, Integer edad, Raza raza, Dueño dueño) {
        this.id = id;
        this.nombre = nombre;
        this.edad = edad;
        this.raza = raza;
        this.dueño = dueño;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getEdad() {
        return edad;
    }

    public void setEdad(Integer edad) {
        this.edad = edad;
    }

    public Raza getRaza() {
        return raza;
    }

    public void setRaza(Raza raza) {
        this.raza = raza;
    }

    public Dueño getDueño() {
        return dueño;
    }

    public void setDueño(Dueño dueño) {
        this.dueño = dueño;
    }

    @Override
    public String toString() {
        return "Burro{" + "id=" + id + ", nombre=" + nombre + ", edad=" + edad + ", raza=" + raza.getNombre() + ", due\u00f1o=" + dueño.getNombre() + '}';
    }
    
    
    
}
