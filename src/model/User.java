package model;

import java.sql.Timestamp;

/**
 * Representa un usuario del chat
 */
public class User {
	
	private String nick;
	private String userhost;
	private Timestamp date_con;
	private int last_read;
	
	/**
     * Crea una instancia de User con los detalles proporcionados
     * 
     * @param nick      El apodo del usuario
     * @param userhost  El host del usuario
     * @param date_con  La fecha de conexión del usuario
     * @param last_read La marca de tiempo del último mensaje leído por el usuario
     */
	public User(String nick, String userhost, Timestamp date_con, int last_read) {
		this.nick = nick;
		this.userhost = userhost;
		this.date_con = date_con;
		this.last_read = last_read;
	}

	/**
     * Obtiene el apodo del usuario
     * 
     * @return El apodo del usuario
     */
	public String getNick() {
		return nick;
	}

	/**
     * Establece el apodo del usuario
     * 
     * @param nick El apodo del usuario
     */
	public void setNick(String nick) {
		this.nick = nick;
	}

	/**
     * Obtiene el host del usuario
     * 
     * @return El host del usuario
     */
	public String getUserhost() {
		return userhost;
	}

	/**
     * Establece el host del usuario
     * 
     * @param userhost El host del usuario
     */
	public void setUserhost(String userhost) {
		this.userhost = userhost;
	}

	/**
     * Obtiene la fecha de conexión del usuario
     * 
     * @return La fecha de conexión del usuario
     */
	public Timestamp getDate_con() {
		return date_con;
	}

	/**
     * Establece la fecha de conexión del usuario
     * 
     * @param date_con La fecha de conexión del usuario
     */
	public void setDate_con(Timestamp date_con) {
		this.date_con = date_con;
	}

	/**
     * Obtiene la marca de tiempo del último mensaje leído por el usuario
     * 
     * @return La marca de tiempo del último mensaje leído por el usuario
     */
	public int getLast_read() {
		return last_read;
	}

	/**
     * Establece la marca de tiempo del último mensaje leído por el usuario
     * 
     * @param last_read La marca de tiempo del último mensaje leído por el usuario
     */
	public void setLast_read(int last_read) {
		this.last_read = last_read;
	}

	
	
}
