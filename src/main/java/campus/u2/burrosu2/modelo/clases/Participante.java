package campus.u2.burrosu2.modelo.clases;

public class Participante extends Persona {

    private Integer idParticipante;
    private Double saldoDisponible;

    public Participante() {
    }

    public Participante(Integer idParticipante, Double saldoDisponible, String nombre, String cedula) {
        super(nombre, cedula);
        this.idParticipante = idParticipante;
        this.saldoDisponible = saldoDisponible;
    }

    public Participante(Integer idParticipante, Double saldoDisponible) {
        this.idParticipante = idParticipante;
        this.saldoDisponible = saldoDisponible;
    }

    public Participante(Double saldoDisponible, String nombre, String cedula) {
        super(nombre, cedula);
        this.saldoDisponible = saldoDisponible;
    }
    
    public Integer getIdParticipante() {
        return idParticipante;
    }

    public void setIdParticipante(Integer idParticipante) {
        this.idParticipante = idParticipante;
    }

    public Double getSaldoDisponible() {
        return saldoDisponible;
    }

    public void setSaldoDisponible(Double saldoDisponible) {
        this.saldoDisponible = saldoDisponible;
    }

    public void descontarSaldo(double monto) {
        if (monto <= saldoDisponible) {
            saldoDisponible -= monto;
        } else {
            throw new IllegalArgumentException("Saldo insuficiente para realizar la apuesta.");
        }
    }

}
