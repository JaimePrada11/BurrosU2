package campus.u2.burrosu2.modelo.clases;

public class Burro {

    private Integer id;
    private String nombre;
    private Integer edad;
    private Raza raza;

    public Burro(Integer id, String nombre, Integer edad, Raza raza) {
        this.id = id;
        this.nombre = nombre;
        this.edad = edad;
        this.raza = raza;
    }

    public Burro() {
    }

    public Burro(String nombre, Integer edad, Raza raza) {
        this.nombre = nombre;
        this.edad = edad;
        this.raza = raza;
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

    @Override
    public String toString() {
        return "Burro{" + "id=" + id + ", nombre=" + nombre + ", edad=" + edad + ", raza=" + raza + '}';
    }

}
