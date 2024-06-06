package model;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * La interfaz Conectable define los métodos para establecer y gestionar la conexion con una base de datos
 */
public interface Conectable {

    /**
     * Obtiene una conexion a la base de datos
     * 
     * @return Una instancia de Connection que representa la conexion a la base de datos
     * @throws SQLException Si ocurre un error al intentar establecer la conexion
     */
    public Connection getConnection() throws SQLException;

    /**
     * Establece una conexion a la base de datos utilizando el nombre de usuario especificado
     * 
     * @param nick El nombre de usuario utilizado para establecer la conexion
     * @throws SQLException Si ocurre un error al intentar establecer la conexion
     */
    public void connect(String nick) throws SQLException;

    /**
     * Cierra la conexion actualmente abierta a la base de datos
     * 
     * @throws SQLException Si ocurre un error al intentar cerrar la conexion
     */
    public void disconnect() throws SQLException;
    
}
