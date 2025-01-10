package campus.u2.burrosu2.modelo.persistencia;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class BdConexion {

    private static final String url = "jdbc:mysql://localhost:3306/BurrosU2";
    private static final String user = "root";
    private static final String password = "J12345";
    private static Connection conexion = null;

    private BdConexion() {
    }

    public static Connection getConexion() {

        try {
            conexion = DriverManager.getConnection(url, user, password);
            if(conexion != null) {
                //System.out.println("Conexion exitosa");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return conexion;
    }

   
}
