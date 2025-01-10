package campus.u2.burrosu2.Controlador;

import campus.u2.burrosu2.modelo.clases.Raza;
import campus.u2.burrosu2.modelo.persistencia.BdConexion;
import campus.u2.burrosu2.modelo.persistencia.CRUD;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RazaControlador {

    public static boolean crearRaza(String nombre) throws SQLException {
        Raza r = new Raza(nombre);
        CRUD.setConexion(BdConexion.getConexion());
        String sentencia = "INSERT INTO Razas (Nombre) values (?)";
        return CRUD.insertarDB(sentencia, r.getNombre());

    }

    public static Raza obtenerRaza(Integer id) throws SQLException {
        CRUD.setConexion(BdConexion.getConexion());
        String sql = "SELECT * FROM Razas WHERE IDRaza = ?";
        ResultSet rs = CRUD.consultarDB(sql, id);
        Raza d = new Raza();
        try {
            if (rs.next()) {
                d.setIdRaza(rs.getInt("IDRaza"));
                d.setNombre(rs.getString("Nombre"));

            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return null;
        }
        return d;
    }

    public static List<Raza> listarRazas() throws SQLException {
        CRUD.setConexion(BdConexion.getConexion());
        List<Raza> listaRazas = new ArrayList<>();

        String sql = "SELECT * FROM Razas";

        ResultSet rs = CRUD.consultarDB(sql);

        try {

            while (rs.next()) {
                Raza r = new Raza();
                r.setIdRaza(rs.getInt("IDRaza"));
                r.setNombre(rs.getString("Nombre"));
                listaRazas.add(r);
            }

        } catch (SQLException ex) {
            System.out.println(ex);
        }

        return listaRazas;
    }
}
