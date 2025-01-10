package campus.u2.burrosu2.Controlador;

import campus.u2.burrosu2.modelo.clases.Burro;
import campus.u2.burrosu2.modelo.clases.Competencia;
import campus.u2.burrosu2.modelo.clases.Raza;
import campus.u2.burrosu2.modelo.persistencia.BdConexion;
import campus.u2.burrosu2.modelo.persistencia.CRUD;
import java.util.stream.Collectors;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CompetenciaControlador {

    public static boolean crearCompetencia(String nombre, LocalDate fecha, String lugar) {
        CRUD.setConexion(BdConexion.getConexion());

        Competencia c = new Competencia(nombre, fecha, lugar);
        String sql = "INSERT INTO Competencias (Nombre, Fecha, Lugar, Estado) VALUES (?, ?, ?,?)";
        return CRUD.insertarDB(sql, c.getNombre(), c.getFecha(), c.getLugar(), false);
    }

    public static boolean editarCompetencia(String nombre, LocalDate fecha, String lugar, int id) {
        CRUD.setConexion(BdConexion.getConexion());
        Competencia c = new Competencia(nombre, fecha, lugar);

        String sql = "UPDATE Competencias set Nombre = ?, Fecha = ?, Lugar = ? WHERE IDCompetencia = ?";
        return CRUD.updateDB(sql, c.getNombre(), c.getFecha(), c.getLugar(), id);
    }

    public static boolean terminarCompetencia(Integer id) {
        CRUD.setConexion(BdConexion.getConexion());
        String sql = "UPDATE Competencias SET Estado = true WHERE IDCompetencia = ?";
        return CRUD.updateDB(sql, id);
    }

    public static ArrayList<Burro> listarBurrosCompetencia(Integer id) {
        CRUD.setConexion(BdConexion.getConexion());
        String sql = "SELECT b.IDBurro, b.Nombre, b.Edad, b.IDRaza, bc.Puesto "
                + "FROM BurrosCompetencia bc "
                + "JOIN Burros b ON b.IDBurro = bc.IDBurro "
                + "WHERE bc.IDCompetencia = ?";
        ArrayList<Burro> listaBurros = new ArrayList<>();
        ResultSet rs = CRUD.consultarDB(sql, id);

        try {
            while (rs != null && rs.next()) {
                Burro b = new Burro();
                b.setId(rs.getInt("IDBurro"));
                b.setNombre(rs.getString("Nombre"));
                b.setEdad(rs.getInt("Edad"));

                Integer razaId = rs.getInt("IDRaza");
                Raza raza = RazaControlador.obtenerRaza(razaId);
                b.setRaza(raza);

                listaBurros.add(b);
            }
        } catch (SQLException ex) {
            Logger.getLogger(BurroControlador.class.getName()).log(Level.SEVERE, null, ex);
        }

        return listaBurros;
    }

    public static List<Competencia> listarCompetencias() throws SQLException {
        CRUD.setConexion(BdConexion.getConexion());
        List<Competencia> listaCompetencias = new ArrayList<>();
        String sql = "SELECT * FROM Competencias";

        ResultSet rs = CRUD.consultarDB(sql);
        try {
            while (rs.next()) {
                Competencia competencia = new Competencia();
                competencia.setIdCompetencia(rs.getInt("IDCompetencia"));
                competencia.setNombre(rs.getString("Nombre"));
                competencia.setFecha(rs.getDate("Fecha").toLocalDate());
                competencia.setLugar(rs.getString("Lugar"));
                competencia.setEstado(rs.getBoolean("Estado"));
                listaCompetencias.add(competencia);
            }
        } catch (SQLException ex) {
            Logger.getLogger(CompetenciaControlador.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listaCompetencias;
    }

    public static boolean registrarBurroEnCompetencia(Integer idCompetencia, Integer idBurro) throws SQLException {
        CRUD.setConexion(BdConexion.getConexion());
        String sql = "INSERT INTO BurrosCompetencia (IDCompetencia, IDBurro) VALUES (?, ?)";
        return CRUD.insertarDB(sql, idCompetencia, idBurro);
    }

    public static Competencia buscarCompetenciaPorId(Integer id) {
        CRUD.setConexion(BdConexion.getConexion());
        String sql = "SELECT * FROM Competencias WHERE IDCompetencia = ?";
        ResultSet rs = CRUD.consultarDB(sql, id);
        Competencia competencia = null;

        try {
            if (rs.next()) {
                competencia = new Competencia();
                competencia.setIdCompetencia(rs.getInt("IDCompetencia"));
                competencia.setNombre(rs.getString("Nombre"));
                competencia.setFecha(rs.getDate("Fecha").toLocalDate());
                competencia.setLugar(rs.getString("Lugar"));
                competencia.setEstado(rs.getBoolean("Estado"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(CompetenciaControlador.class.getName()).log(Level.SEVERE, null, ex);
        }

        return competencia;
    }

    public static List<Competencia> buscarCompetenciasPorRangoDeFechas(LocalDate inicio, LocalDate fin) throws SQLException {
        List<Competencia> competencias = listarCompetencias();

        return competencias.stream()
                .filter(c -> !c.getFecha().isBefore(inicio) && !c.getFecha().isAfter(fin))
                .collect(Collectors.toList());
    }

    public static boolean asignarPuesto(Integer idCompetencia, Integer idBurro, Integer puesto) throws SQLException {
        CRUD.setConexion(BdConexion.getConexion());
        String sql = "UPDATE BurrosCompetencia SET Puesto = ? WHERE IDCompetencia = ? AND IDBurro = ?";
        return CRUD.updateDB(sql, puesto, idCompetencia, idBurro);
    }

    public static List<Competencia> obtenerCompetenciasActivas() throws SQLException {
        List<Competencia> competencias = listarCompetencias();

        return competencias.stream()
                .filter(Competencia::isEstado)
                .collect(Collectors.toList());
    }

    public static boolean verificarBurroEnCompetenciaActiva(Integer idCompetencia, Integer idBurro) throws SQLException {
        CRUD.setConexion(BdConexion.getConexion());
        String sql = "SELECT COUNT(*) AS Count FROM BurrosCompetencia bc "
                + "JOIN Competencias c ON bc.IDCompetencia = c.IDCompetencia "
                + "WHERE bc.IDCompetencia = ? AND bc.IDBurro = ? AND c.Estado = true";
        ResultSet rs = CRUD.consultarDB(sql, idCompetencia, idBurro);

        try {
            if (rs.next()) {
                return rs.getInt("Count") > 0;
            }
        } catch (SQLException ex) {
            Logger.getLogger(CompetenciaControlador.class.getName()).log(Level.SEVERE, null, ex);
        }

        return false;
    }

    public static List<Map.Entry<Burro, Integer>> obtenerTresPrimerosPuestos(Integer idCompetencia) throws SQLException {
        CRUD.setConexion(BdConexion.getConexion());
        String sql = "SELECT b.IDBurro, b.Nombre, bc.Puesto "
                + "FROM BurrosCompetencia bc "
                + "JOIN Burros b ON bc.IDBurro = b.IDBurro "
                + "WHERE bc.IDCompetencia = ? "
                + "ORDER BY bc.Puesto ASC "
                + "LIMIT 3";

        List<Map.Entry<Burro, Integer>> resultados = new ArrayList<>();

        try (ResultSet rs = CRUD.consultarDB(sql, idCompetencia)) {
            while (rs.next()) {
                Burro burro = new Burro();
                burro.setId(rs.getInt("IDBurro"));
                burro.setNombre(rs.getString("Nombre"));
                int puesto = rs.getInt("Puesto");

                resultados.add(new AbstractMap.SimpleEntry<>(burro, puesto));
            }
        } catch (SQLException ex) {
            Logger.getLogger(CompetenciaControlador.class.getName()).log(Level.SEVERE, null, ex);
            throw ex;
        }

        return resultados;
    }

}
