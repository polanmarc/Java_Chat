package model;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * La clase Model implementa la interfaz Conectable y proporciona métodos para interactuar con una base de datos
 */
public class Model implements Conectable {

    private static final String URL = "jdbc:mysql://localhost/chat"; // jdbc:mysql://192.168.119.13/chat
    private static final String USERNAME = "appuser";
    private static final String PASSWORD = "TiC.123456";

    /**
     * Obtiene una conexión a la base de datos.
     * 
     * @return Una instancia de Connection que representa la conexión a la base de datos
     * @throws SQLException Si ocurre un error al intentar establecer la conexión
     */
    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }
    
    /**
     * Establece una conexión a la base de datos y ejecuta un procedimiento almacenado para conectar un usuario
     * 
     * @param nick El nombre de usuario que se desea conectar
     * @throws SQLException Si ocurre un error al intentar establecer la conexión o ejecutar el procedimiento almacenado
     */
    public void connect(String nick) throws SQLException {
        try (Connection conn = getConnection();
             CallableStatement statement = conn.prepareCall("{call connect(?)}")) {
            statement.setString(1, nick);
            statement.execute();
        }
    }

    /**
     * Cierra la conexión actualmente abierta a la base de datos
     * 
     * @throws SQLException Si ocurre un error al intentar cerrar la conexión
     */
    public void disconnect() throws SQLException {
        try (Connection conn = getConnection();
             CallableStatement statement = conn.prepareCall("{call disconnect()}")) {
            statement.execute();
        }
    }
}
