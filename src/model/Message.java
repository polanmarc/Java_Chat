package model;

import java.sql.Timestamp;

/**
 * Representa un mensaje del chat
 */
public class Message {

    private int id;
    private String nick; 
    private String message; 
    private Timestamp ts; 

    /**
     * Crea una instancia de Message con los detalles proporcionados
     * 
     * @param id      El identificador unico del mensaje
     * @param nick    El apodo del usuario que envio el mensaje
     * @param message El contenido del mensaje
     * @param ts      La marca de tiempo del mensaje
     */
    public Message(int id, String nick, String message, Timestamp ts) {
        this.id = id;
        this.nick = nick;
        this.message = message;
        this.ts = ts;
    }

    /**
     * Obtiene el identificador único del mensaje
     * 
     * @return El identificador único del mensaje
     */
    public int getId() {
        return id;
    }

    /**
     * Establece el identificador único del mensaje
     * 
     * @param id El identificador único del mensaje
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Obtiene el apodo del usuario que envió el mensaje
     * 
     * @return El apodo del usuario que envió el mensaje
     */
    public String getNick() {
        return nick;
    }

    /**
     * Establece el apodo del usuario que envió el mensaje
     * 
     * @param nick El apodo del usuario que envió el mensaje
     */
    public void setNick(String nick) {
        this.nick = nick;
    }

    /**
     * Obtiene el contenido del mensaje
     * 
     * @return El contenido del mensaje
     */
    public String getMessage() {
        return message;
    }

    /**
     * Establece el contenido del mensaje
     * 
     * @param message El contenido del mensaje
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * Obtiene la marca de tiempo del mensaje
     * 
     * @return La marca de tiempo del mensaje
     */
    public Timestamp getTs() {
        return ts;
    }

    /**
     * Establece la marca de tiempo del mensaje
     * 
     * @param ts La marca de tiempo del mensaje
     */
    public void setTs(Timestamp ts) {
        this.ts = ts;
    }

}
