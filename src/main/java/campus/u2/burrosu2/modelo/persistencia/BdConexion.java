package campus.u2.burrosu2.modelo.persistencia;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class BdConexion {

    private static final String URL = "jdbc:mysql://localhost:3306/BurrosU2";
    private static final String USER = "root";
    private static final String PASSWORD = "J12345";
    public static Connection conexion = null;

    public static Connection getConnection() {
        try {
            conexion = DriverManager.getConnection(URL, USER, PASSWORD);
            if (conexion != null) {
                System.out.println("Conexion exitosa");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return conexion;
    }
}
