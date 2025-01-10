
package campus.u2.burrosu2.Controlador;

import campus.u2.burrosu2.modelo.clases.Burro;
import campus.u2.burrosu2.modelo.clases.Apuesta;
import campus.u2.burrosu2.modelo.clases.Competencia;
import campus.u2.burrosu2.modelo.clases.Participante;
import campus.u2.burrosu2.modelo.persistencia.BdConexion;
import campus.u2.burrosu2.modelo.clases.EstadoApuesta;

/**
 *
 * @author LENOVO
 */
import campus.u2.burrosu2.modelo.persistencia.CRUD;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ApuestasControlador {

    public static boolean realizarApuesta(Participante participante, Burro burro, Competencia competencia, double montoApostado) {
        CRUD.setConexion(BdConexion.getConexion());

        String sql = "INSERT INTO apuestas (ID_Participante, ID_Burro, ID_Competencia, MontoApostado) VALUES (?, ?, ?, ?)";

        boolean apuestaInsertada = CRUD.insertarDB(sql, participante.getIdParticipante(), burro.getId(), competencia.getIdCompetencia(), montoApostado);
        if (apuestaInsertada) {
            System.out.println("Apuesta realizada con éxito.");
            return true;
        } else {
            System.out.println("Error al realizar la apuesta.");
        }

        return false;
    }

    public static List<Apuesta> listarApuestas() throws SQLException {
        CRUD.setConexion(BdConexion.getConexion());
        List<Apuesta> listaApuestas = new ArrayList<>();
        String sql = "SELECT * FROM Apuestas";

        ResultSet rs = CRUD.consultarDB(sql);
        try {
            while (rs.next()) {
                Apuesta apuesta = new Apuesta();
                apuesta.setIdApuesta(rs.getInt("IDApuesta"));
                apuesta.setBurro(BurroControlador.obtenerBurro(rs.getInt("IDBurro")));
                apuesta.setCompetencia(CompetenciaControlador.buscarCompetenciaPorId(rs.getInt("IDCompetencia")));
                apuesta.setParticipante(ParticipanteControlador.obtenerParticipante(rs.getInt("IDParticipante")));
                apuesta.setMontoApostado(rs.getDouble("MontoApostado"));
                apuesta.setEstado(EstadoApuesta.valueOf(rs.getString("Estado").toUpperCase()));
                listaApuestas.add(apuesta);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ApuestasControlador.class.getName()).log(Level.SEVERE, null, ex);
        }

        return listaApuestas;
    }

    public static List<Apuesta> listarApuestasPorParticipante(String cedula) throws SQLException {
        CRUD.setConexion(BdConexion.getConexion());
        List<Apuesta> listaApuestas = new ArrayList<>();
        String sql = "SELECT a.* " + "FROM Apuestas a "
                + "JOIN Participantes p ON a.IDParticipante = p.IDParticipante "
                + "WHERE p.Cedula = ?";

        ResultSet rs = CRUD.consultarDB(sql, cedula);
        try {
            while (rs.next()) {
                Apuesta apuesta = new Apuesta();
                apuesta.setIdApuesta(rs.getInt("IDApuesta"));
                apuesta.setBurro(BurroControlador.obtenerBurro(rs.getInt("IDBurro")));
                apuesta.setCompetencia(CompetenciaControlador.buscarCompetenciaPorId(rs.getInt("IDCompetencia")));
                apuesta.setParticipante(ParticipanteControlador.obtenerParticipante(rs.getInt("IDParticipante")));
                apuesta.setMontoApostado(rs.getDouble("MontoApostado"));
                apuesta.setEstado(EstadoApuesta.valueOf(rs.getString("Estado")));
                listaApuestas.add(apuesta);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ApuestasControlador.class.getName()).log(Level.SEVERE, null, ex);
        }

        return listaApuestas;
    }

    public static Apuesta obtenerUltimaApuestaPorParticipante(String cedula) throws SQLException {
    CRUD.setConexion(BdConexion.getConexion());
    String sql = "SELECT a.* " +
                 "FROM Apuestas a " +
                 "JOIN Participantes p ON a.IDParticipante = p.IDParticipante " +
                 "WHERE p.Cedula = ? " +
                 "ORDER BY a.FechaApuesta DESC " +
                 "LIMIT 1";
    
    ResultSet rs = CRUD.consultarDB(sql, cedula);
    Apuesta apuesta = null;

    try {
        if (rs.next()) {
            apuesta = new Apuesta();
            apuesta.setIdApuesta(rs.getInt("IDApuesta"));
            apuesta.setBurro(BurroControlador.obtenerBurro(rs.getInt("IDBurro")));
            apuesta.setCompetencia(CompetenciaControlador.buscarCompetenciaPorId(rs.getInt("IDCompetencia")));
            apuesta.setParticipante(ParticipanteControlador.obtenerParticipante(rs.getInt("IDParticipante")));
            apuesta.setMontoApostado(rs.getDouble("MontoApostado"));
            apuesta.setEstado(EstadoApuesta.valueOf(rs.getString("Estado")));
        }
    } catch (SQLException ex) {
        Logger.getLogger(ApuestasControlador.class.getName()).log(Level.SEVERE, null, ex);
    } 

    return apuesta;
}
    
public static double calcularGananciasApuestaPorCompetenciaYParticipante(Integer idCompetencia, String cedula) throws SQLException {
    CRUD.setConexion(BdConexion.getConexion());
    String sql = "SELECT a.MontoApostado, a.Estado " +
                 "FROM Apuestas a " +
                 "JOIN Participantes p ON a.IDParticipante = p.IDParticipante " +
                 "WHERE p.Cedula = ? AND a.IDCompetencia = ? ";
    ResultSet rs = CRUD.consultarDB(sql, cedula, idCompetencia);
    double ganancia = 0.0;

    try {
        if (rs.next()) {
            double montoApostado = rs.getDouble("MontoApostado");
            String estado = rs.getString("Estado");

            if ("GANADA".equalsIgnoreCase(estado)) {
                ganancia = montoApostado + (montoApostado * 0.25); 
            }
        }
    } catch (SQLException ex) {
        Logger.getLogger(ApuestasControlador.class.getName()).log(Level.SEVERE, null, ex);
    }

    return ganancia;
}


    
}
