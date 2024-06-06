package model;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

/**
 * Proporciona métodos para interactuar con la tabla de usuarios en la base de datos del chat
 */
public class UserModel extends Model {

    /**
     * Obtiene una lista de usuarios actualmente conectados al chat
     * 
     * @return La lista de usuarios conectados
     * @throws SQLException Si ocurre un error al obtener los usuarios conectados
     */
    public ArrayList<User> getConnectedUsers() throws SQLException {
        ArrayList<User> userList = new ArrayList<>();

        try (Connection conn = getConnection();
             CallableStatement statement = conn.prepareCall("{call getConnectedUsers()}")) {
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    String nick = resultSet.getString("nick");
                    Timestamp date_con = resultSet.getTimestamp("date_con");
                    User user = new User(nick, null, date_con, 0);
                    userList.add(user);
                }
            }
        }

        return userList;
    }
    
}


/*

===== CRUD =====


public void update(User user) throws SQLException {
        String sql = "UPDATE users SET userhost = ?, date_con = ?, last_read = ? WHERE nick = ?";
        try (Connection conn = getConnection();
             PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setString(1, user.getUserhost());
            statement.setTimestamp(2, user.getDate_con());
            statement.setInt(3, user.getLast_read());
            statement.setString(4, user.getNick());
            statement.executeUpdate();
        }
    }

    public void delete(User user) throws SQLException {
        String sql = "DELETE FROM users WHERE nick = ?";
        try (Connection conn = getConnection();
             PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setString(1, user.getNick());
            statement.executeUpdate();
        }
    }
    
    public void create(User user) throws SQLException {
        String sql = "INSERT INTO users (nick, userhost, date_con, last_read) VALUES (?, ?, ?, ?)";
        try (Connection conn = getConnection();
            PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setString(1, user.getNick());
            statement.setString(2, user.getUserhost());
            statement.setTimestamp(3, user.getDate_con());
            statement.setInt(4, user.getLast_read());
            statement.executeUpdate();
        }
    }
    
    public User search(String nick) throws SQLException {
        String sql = "SELECT * FROM users WHERE nick = ?";
        try (Connection conn = getConnection();
             PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setString(1, nick);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return new User(
                            resultSet.getString("nick"),
                            resultSet.getString("userhost"),
                            resultSet.getTimestamp("date_con"),
                            resultSet.getInt("last_read")
                    );
                }
            }
        }
        return null;
    }

*/