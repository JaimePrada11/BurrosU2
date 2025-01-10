package campus.u2.burrosu2.modelo.persistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CRUD {

    private static Connection conexion;

    public static Connection getConexion() {
        return conexion;
    }

    public static Connection setConexion(Connection conexion) {
        CRUD.conexion = conexion;
        return conexion;
    }

    public static void closeConexion(Connection conexion) {
        if (conexion != null) {
            try {
                conexion.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static boolean insertarDB(String sql, Object... params) {
        PreparedStatement ps = null;
        try {
            ps = conexion.prepareStatement(sql);
            for (int i = 0; i < params.length; i++) {
                ps.setObject(i + 1, params[i]);
            }
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            throw new RuntimeException("Error al insertar en la base de datos", e);
        }
    }

    public static boolean eliminarDB(String sql, Object... params) {
        PreparedStatement ps = null;
        try {
            ps = conexion.prepareStatement(sql);
            for (int i = 0; i < params.length; i++) {
                ps.setObject(i + 1, params[i]);
            }
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Error al eliminar en la base de datos", e);
        }
    }

    public static boolean updateDB(String sql, Object... params) {
        PreparedStatement ps = null;
        try {
            ps = conexion.prepareStatement(sql);
            for (int i = 0; i < params.length; i++) {
                ps.setObject(i + 1, params[i]);
            }
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Error al actualizar en la base de datos", e);
        }
    }

    public static ResultSet consultarDB(String sql, Object... params) {
        PreparedStatement ps = null;
        try {
            ps = conexion.prepareStatement(sql);
            for (int i = 0; i < params.length; i++) {
                ps.setObject(i + 1, params[i]);
            }
            return ps.executeQuery();
        } catch (SQLException e) {
            throw new RuntimeException("Error al consultar la base de datos", e);
        }
    }
}
