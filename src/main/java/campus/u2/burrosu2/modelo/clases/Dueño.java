package campus.u2.burrosu2.modelo.clases;

public class Dueño extends Persona {
    
    private Integer idDueño;

    public Dueño() {
    }

    public Dueño(Integer idDueño, String nombre, String cedula) {
        super(nombre, cedula);
        this.idDueño = idDueño;
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

    @Override
    public String toString() {
        return "Due\u00f1o{" + "idDue\u00f1o=" + idDueño + ", nombre=" + nombre + '}';
    }
    
    
    
}
