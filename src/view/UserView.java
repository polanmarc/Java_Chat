package view;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import model.User;

/**
 * La clase UserView representa la vista de un usuario en un canvas
 */
public class UserView extends Canvas {
	
    private static final long serialVersionUID = 1L;
    private User user;
    private Color colorText;
    private String userName;

    /**
     * Constructor de la clase UserView
     * 
     * @param user  El usuario a visualizar
     * @param color El color del texto del usuario
     */
    public UserView(User user, Color color) {
    	setSize(200, 50); 
    	this.user = user;
        this.colorText = color;
        this.userName = user.getNick();
    }

    /**
     * Método para dibujar la vista del usuario en el canvas
     * 
     * @param g El contexto gráfico utilizado para dibujar
     */
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        if (user.getNick() != null) {
            g.setColor(new Color(245, 245, 245));
            g.fillRoundRect(5, 5, getWidth() - 10, getHeight() - 10, 10, 10);

            g.setColor(this.colorText);
            g.setFont(new Font("Arial", Font.BOLD, 14));
            
            String name = user.getNick();
            String date = getHours(user.getDate_con());
            
            int x = 20;
            int y = (getHeight() - 12) - g.getFontMetrics().getHeight();
            g.drawString(name, x, y);
            
            y += g.getFontMetrics().getHeight(); 
            g.drawString(date, x, y); 
        }
    }
    
    /**
     * Método para obtener la hora formateada de una marca de tiempo
     * 
     * @param date La marca de tiempo de la que se desea obtener la hora
     * @return La hora formateada como una cadena de caracteres
     */
    private String getHours(Timestamp date) {
        LocalDateTime dateTime = date.toLocalDateTime();
        String time = dateTime.toLocalTime().toString();
        return time;
    }
    
    /**
     * Método para obtener el nombre de usuario asociado a esta vista
     * 
     * @return El nombre de usuario asociado a esta vista
     */
    public String getUserName() {
        return userName;
    }

}