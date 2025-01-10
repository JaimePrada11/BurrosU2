package campus.u2.burrosu2.Controlador;

import campus.u2.burrosu2.modelo.persistencia.BdConexion;
import campus.u2.burrosu2.modelo.persistencia.CRUD;
import campus.u2.burrosu2.modelo.clases.Burro;
import campus.u2.burrosu2.modelo.clases.Dueño;
import campus.u2.burrosu2.modelo.clases.Raza;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BurroControlador {

    public static boolean creaBurro(String nombre, Integer edad, Raza raza, Dueño dueño) throws SQLException {
        CRUD.setConexion(BdConexion.getConexion());
        String Sentencia = "INSERT INTO Burros (Nombre, Edad, IDRaza, IDDueño) VALUES (?, ?, ?, ?);";
        return CRUD.eliminarDB(Sentencia, nombre, edad, raza.getIdRaza(), dueño.getIdDueño());

    }

    public static boolean borrarBurro(Integer id) throws SQLException {
        CRUD.setConexion(BdConexion.getConexion());
        String Sentencia = "DELETE FROM Burros WHERE IDBurro= ?;";
        return CRUD.eliminarDB(Sentencia, id);

    }

    public static boolean actualizarBurro(Integer id, String nombre, Integer edad, Raza raza) throws SQLException {
        CRUD.setConexion(BdConexion.getConexion());
        Burro b = new Burro(id, nombre, edad, raza);
        String Sentencia = "UPDATE Burros SET Nombre = ?, Edad = ?, IDRaza = ? WHERE IDBurro = ?";

        return CRUD.updateDB(Sentencia, nombre, edad, raza.getIdRaza(), id);

    }

    public static Burro obtenerBurro(Integer id) {
        CRUD.setConexion(BdConexion.getConexion());

        String sql = "SELECT * FROM Burros WHERE IDBurro = ?";

        try (ResultSet rs = CRUD.consultarDB(sql, id)) {
            if (rs.next()) {
                Burro b = new Burro();
                b.setId(rs.getInt("IDBurro"));
                b.setNombre(rs.getString("Nombre"));
                b.setEdad(rs.getInt("Edad"));
                b.setRaza(RazaControlador.obtenerRaza(rs.getInt("IDRaza")));
                return b;
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return null;
    }

    public static List<Burro> listarBurros() throws SQLException {
        CRUD.setConexion(BdConexion.getConexion());
        List<Burro> listaBurros = new ArrayList<>();
        String sql = "SELECT B.IDBurro, B.Nombre AS NombreBurro, B.Edad, D.Nombre AS NombreDueño, D.Cedula AS CedulaDueño, R.IDRaza, R.Nombre AS NombreRaza "
                + "FROM Burros B "
                + "JOIN Dueños D ON B.IDDueño = D.IDDueño "
                + "JOIN Razas R ON B.IDRaza = R.IDRaza";

        try (ResultSet rs = CRUD.consultarDB(sql)) {
            while (rs.next()) {
                Burro b = new Burro();
                b.setId(rs.getInt("IDBurro"));
                b.setNombre(rs.getString("NombreBurro"));
                b.setEdad(rs.getInt("Edad"));
                Integer razaId = rs.getInt("IDRaza");
                Raza raza = RazaControlador.obtenerRaza(razaId);
                b.setRaza(raza);
                listaBurros.add(b);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return listaBurros;
    }

    public static List<Burro> mostrarBurrosDeDueño(String cedula) throws SQLException {
        List<Burro> listaBurros = new ArrayList<>();
        CRUD.setConexion(BdConexion.getConexion());

        try {
            Dueño d = DueñoControlador.obtenerDueñoCedula(cedula);
            if (d == null) {
                throw new SQLException("Dueño no encontrado.");
            }

            String sql = "SELECT * FROM Burros WHERE IDDueño = ?";

            ResultSet rs = CRUD.consultarDB(sql, d.getIdDueño());
            try {
                while (rs.next()) {
                    Burro b = new Burro();
                    b.setId(rs.getInt("IDBurro"));
                    b.setNombre(rs.getString("Nombre"));
                    b.setEdad(rs.getInt("Edad"));
                    b.setRaza(RazaControlador.obtenerRaza(rs.getInt("IDRaza")));
                    listaBurros.add(b);
                }
            } finally {
                if (rs != null) {
                    rs.close();
                }
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return listaBurros;
    }
}
