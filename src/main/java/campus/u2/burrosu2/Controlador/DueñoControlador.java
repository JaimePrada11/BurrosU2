package campus.u2.burrosu2.Controlador;

import campus.u2.burrosu2.modelo.persistencia.BdConexion;
import campus.u2.burrosu2.modelo.persistencia.CRUD;
import campus.u2.burrosu2.modelo.clases.Dueño;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DueñoControlador {

    public static boolean crearDueño(String nombre, String cedula) throws SQLException {
        Dueño d = new Dueño(nombre, cedula);
        CRUD.setConexion(BdConexion.getConexion());
        String sentencia = "INSERT INTO Dueños (Nombre, Cedula) values (?, ?)";

        return CRUD.insertarDB(sentencia, d.getNombre(), d.getCedula());
    }

    public static boolean actualizarDueño(int id, String nombre, String cedula) throws SQLException {
        Dueño d = new Dueño(nombre, cedula);
        CRUD.setConexion(BdConexion.getConexion());
        String Sentencia = "UPDATE Dueño SET Nombre = ?, Cedula = ? WHERE IDDueño = ?";
        return CRUD.updateDB(Sentencia, d.getNombre(), d.getCedula());

    }

    public static Dueño obtenerDueño(int id) throws SQLException {
        CRUD.setConexion(BdConexion.getConexion());
        String sql = "SELECT * FROM Dueños WHERE IDDueño = ?";

        try (ResultSet rs = CRUD.consultarDB(sql, id)) {
            if (rs.next()) {
                Dueño d = new Dueño();
                d.setIdDueño(rs.getInt("IDDueño"));
                d.setNombre(rs.getString("Nombre"));
                d.setCedula(rs.getString("Cedula"));
                return d;
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return null;
    }

    public static Dueño obtenerDueñoCedula(String cedula) throws SQLException {
        CRUD.setConexion(BdConexion.getConexion());
        String sql = "SELECT * FROM Dueños WHERE Cedula ='" + cedula + "';";

        try (ResultSet rs = CRUD.consultarDB(sql)) {
            if (rs.next()) {
                Dueño d = new Dueño();
                d.setIdDueño(rs.getInt("IDDueño"));
                d.setNombre(rs.getString("Nombre"));
                d.setCedula(rs.getString("Cedula"));
                return d;
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return null;
    }

    public static List<Dueño> listarDueños() throws SQLException {
        CRUD.setConexion(BdConexion.getConexion());
        List<Dueño> listaDueños = new ArrayList<>();
        String sql = "SELECT * FROM Dueños";

        try (ResultSet rs = CRUD.consultarDB(sql)) {
            while (rs.next()) {
                Dueño d = new Dueño();
                d.setIdDueño(rs.getInt("IDDueño"));
                d.setNombre(rs.getString("Nombre"));
                d.setCedula(rs.getString("Cedula"));
                listaDueños.add(d);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return listaDueños;
    }

}
