package model;

import java.io.Serializable;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * Proporciona métodos para interactuar con la tabla de mensajes en la base de datos del chat
 */
public class MessageModel extends Model implements Serializable {

    private static final long serialVersionUID = 1L;

	/**
     * Envía un mensaje al chat
     * 
     * @param message El mensaje que se enviará
     * @throws SQLException Si ocurre un error al enviar el mensaje
     */
    public void sendMessage(String message) throws SQLException {
        try (Connection conn = getConnection(); 
             CallableStatement statement = conn.prepareCall("{call send(?)}")) {
            statement.setString(1, message);
            statement.execute();
        }
    }

    /**
     * Obtiene todos los mensajes del chat
     * 
     * @return Una lista de mensajes del chat
     * @throws SQLException Si ocurre un error al obtener los mensajes
     */
    public List<Message> getMessages() throws SQLException {
        List<Message> messages = new ArrayList<>();
        try (Connection conn = getConnection();
             CallableStatement statement = conn.prepareCall("{call getMessages()}")) {
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    String nick = resultSet.getString("nick");
                    String message = resultSet.getString("message");
                    Timestamp ts = resultSet.getTimestamp("ts");
                    messages.add(new Message(0, nick, message, ts));
                }
            }
        }
        return messages;
    }

    /**
     * Obtiene todos los mensajes almacenados en la base de datos
     * 
     * @return Una lista de todos los mensajes almacenados en la base de datos
     * @throws SQLException Si ocurre un error al obtener los mensajes almacenados
     */
    public List<Message> readAll() throws SQLException {
        List<Message> messages = new ArrayList<>();
        String sql = "SELECT * FROM messages";
        try (Connection conn = getConnection();
             PreparedStatement statement = conn.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                messages.add(new Message(resultSet.getInt("id_message"), resultSet.getString("nick"),
                        resultSet.getString("message"), resultSet.getTimestamp("ts")));
            }
        }
        return messages;
    }
}


//  === Update y Delete ===

//	public void update(Message message) throws SQLException {
//		String sql = "UPDATE messages SET nick = ?, message = ?, ts = ? WHERE id_message = ?";
//		try (Connection conn = getConnection(); PreparedStatement statement = conn.prepareStatement(sql)) {
//			statement.setString(1, message.getNick());
//			statement.setString(2, message.getMessage());
//			statement.setTimestamp(3, message.getTs());
//			statement.setInt(4, message.getId());
//			statement.executeUpdate();
//		}
//	}
//	
//	public void delete(Message message) throws SQLException {
//		String sql = "DELETE FROM messages WHERE id_message = ?";
//		try (Connection conn = getConnection(); PreparedStatement statement = conn.prepareStatement(sql)) {
//			statement.setInt(1, message.getId());
//			statement.executeUpdate();
//		}
//	}
