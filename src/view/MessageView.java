package view;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import model.Message;

/**
 * La clase MessageView representa la vista de un mensaje en un canvas.
 */
public class MessageView extends Canvas {

    private static final long serialVersionUID = 1L;
    private Message message;
    private Color colorText;

    /**
     * Constructor para inicializar la vista de un mensaje.
     * 
     * @param message El mensaje a visualizar.
     * @param color El color del texto del mensaje.
     */
    public MessageView(Message message, Color color) {
        this.calcCanvasSize(message.getMessage());
        this.message = message;
        this.colorText = color;
    }

    /**
     * Metodo para dibujar el mensaje en el canvas.
     * 
     * @param g El contexto gráfico utilizado para dibujar.
     */
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        if (message != null) {
            g.setColor(new Color(245, 245, 245));
            g.fillRoundRect(5, 5, getWidth() - 10, getHeight() - 10, 10, 10);

            g.setFont(new Font("Arial", Font.PLAIN, 14));

            String sender = message.getNick(); 
            String timestamp = getFormattedTimestamp(message.getTs()); 
            String messageText = message.getMessage();

            int x = 20;
            int y = 25; 
            
            g.setColor(this.colorText);
            g.drawString(sender + " - " + timestamp, x, y);
            y += g.getFontMetrics().getHeight();

            g.setColor(Color.BLACK);
            
            int maxLength = 40;
            int lineBreak = 0;
            
            for (int i = 0; i < messageText.length(); i += maxLength) {
                int endIndex = Math.min(i + maxLength, messageText.length());
                String line = messageText.substring(i, endIndex);
                g.drawString(line, x, y);
                y += g.getFontMetrics().getHeight();
                lineBreak++;
                System.out.println(lineBreak);
            }
            
        }
    }
    
    /**
     * Metodo para calcular el tamaño del canvas en función del mensaje.
     * 
     * @param message El mensaje a considerar para el cálculo del tamaño.
     */
    private void calcCanvasSize(String message) {
    	int maxLength = 40;
        int lineBreak = (int) Math.ceil(message.length() / maxLength);
    	
        System.out.println(lineBreak);
        
    	setSize(380, ((lineBreak * 30) + 60));
    }
    
    /**
     * Metodo para obtener la marca de tiempo formateada como una cadena de caracteres.
     * 
     * @param timestamp La marca de tiempo a formatear.
     * @return La marca de tiempo formateada como una cadena de caracteres.
     */
    private String getFormattedTimestamp(Timestamp timestamp) {
        LocalDateTime dateTime = timestamp.toLocalDateTime(); 
        String formattedTime = dateTime.toLocalTime().toString();
        return formattedTime;
    }

}