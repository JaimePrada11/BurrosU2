package campus.u2.burrosu2.Controlador;

import campus.u2.burrosu2.modelo.persistencia.BdConexion;
import campus.u2.burrosu2.modelo.clases.Participante;
import campus.u2.burrosu2.modelo.persistencia.CRUD;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ParticipanteControlador {

    public static boolean registrarParticipante(Double saldoDisponible, String nombre, String cedula) {

        Participante p = new Participante(saldoDisponible, nombre, cedula);
        CRUD.setConexion(BdConexion.getConexion());
        String sql = "INSERT INTO Participantes (Nombre, Cedula, SaldoDisponible) values (?, ?, ?)";
        return CRUD.insertarDB(sql, p.getNombre(), p.getCedula(), p.getSaldoDisponible());
    }

    public static boolean editarParticipante(String nombre, String cedula, double saldo, int id) {
        CRUD.setConexion(BdConexion.getConexion());
        Participante p = new Participante(saldo, nombre, cedula);

        String sql = "UPDATE participantes set Nombre = ?, Cedula = ?, SaldoDisponible = ? WHERE IDParticipante = ?";
        return CRUD.updateDB(sql, p.getNombre(), p.getCedula(), p.getSaldoDisponible());
    }

    public static boolean eliminarParticipante(String cedula) {
        CRUD.setConexion(BdConexion.getConexion());
        String sql = "DELETE FROM Participantes WHERE Cedula = ?";
        return CRUD.eliminarDB(sql, cedula);
    }

    public static ArrayList<Participante> listarParticipantes() {
        CRUD.setConexion(BdConexion.getConexion());
        String sql = "select * from participantes";
        Participante participante = null;
        ArrayList<Participante> listaParticipantes = new ArrayList();
        ResultSet rs = CRUD.consultarDB(sql);
        try {
            while (rs != null && rs.next()) {
                participante = new Participante();
                participante.setCedula(rs.getString("Cedula"));
                participante.setIdParticipante(rs.getInt("IDParticipante"));
                participante.setNombre(rs.getString("Nombre"));
                participante.setSaldoDisponible(rs.getDouble("SaldoDisponible"));
                listaParticipantes.add(participante);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ParticipanteControlador.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listaParticipantes;
    }

    public static Participante obtenerParticipante(String cedula) {
        CRUD.setConexion(BdConexion.getConexion());
        String sql = "select * from participantes where Cedula = ?";
        Participante participante = null;
        ResultSet rs = CRUD.consultarDB(sql, cedula);
        try {
            while (rs != null && rs.next()) {
                participante = new Participante();
                participante.setCedula(rs.getString("Cedula"));
                participante.setIdParticipante(rs.getInt("IDParticipante"));
                participante.setNombre(rs.getString("Nombre"));
                participante.setSaldoDisponible(rs.getDouble("SaldoDisponible"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(ParticipanteControlador.class.getName()).log(Level.SEVERE, null, ex);

        }
        return participante;
    }
    
    public static Participante obtenerParticipante(Integer id) {
        CRUD.setConexion(BdConexion.getConexion());
        String sql = "select * from participantes where IDParticipante = ?";
        Participante participante = null;
        ResultSet rs = CRUD.consultarDB(sql, id);
        try {
            while (rs != null && rs.next()) {
                participante = new Participante();
                participante.setCedula(rs.getString("Cedula"));
                participante.setIdParticipante(rs.getInt("IDParticipante"));
                participante.setNombre(rs.getString("Nombre"));
                participante.setSaldoDisponible(rs.getDouble("SaldoDisponible"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(ParticipanteControlador.class.getName()).log(Level.SEVERE, null, ex);

        }
        return participante;
    }

}
