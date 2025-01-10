package campus.u2.burrosu2.modelo.clases;

public class Apuesta {

    private Integer idApuesta;
    private Burro burro;
    private Competencia competencia;
    private Participante participante;
    private Double montoApostado;
    private EstadoApuesta estado;

    public Apuesta(int idApuesta, Burro burro, Competencia competencia, Participante participante, Double montoApostado) {
        this.idApuesta = idApuesta;
        this.burro = burro;
        this.competencia = competencia;
        this.participante = participante;
        this.montoApostado = montoApostado;
        this.estado = EstadoApuesta.PENDIENTE;
    }

    public Integer getIdApuesta() {
        return idApuesta;
    }

    public void setIdApuesta(Integer idApuesta) {
        this.idApuesta = idApuesta;
    }

    public Burro getBurro() {
        return burro;
    }

    public void setBurro(Burro burro) {
        this.burro = burro;
    }

    public Competencia getCompetencia() {
        return competencia;
    }

    public void setCompetencia(Competencia competencia) {
        this.competencia = competencia;
    }

    public Participante getParticipante() {
        return participante;
    }

    public void setParticipante(Participante participante) {
        this.participante = participante;
    }

    public Double getMontoApostado() {
        return montoApostado;
    }

    public void setMontoApostado(Double montoApostado) {
        this.montoApostado = montoApostado;
    }

    public EstadoApuesta getEstado() {
        return estado;
    }

    public void setEstado(EstadoApuesta estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return "Apuesta{"
                + "idApuesta=" + idApuesta
                + ", burro=" + burro.getNombre()
                + ", competencia=" + competencia.getNombre()
                + ", participante=" + participante.getNombre()
                + ", montoApostado=" + montoApostado
                + ", estado=" + estado
                + '}';
    }

}
