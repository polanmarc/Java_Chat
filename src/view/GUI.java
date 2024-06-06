package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import exception.WrongMessage;
import exception.WrongUser;
import model.Message;
import model.MessageModel;
import model.User;
import model.UserModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.awt.Font;
import java.awt.GridLayout;

public class GUI implements ActionListener {

	private JFrame window;
	private JFrame aboutFrame;
	private JFrame estadisticasFrame;

	private JPanel pnlEstadistica;
	private JPanel contentPane;
	private JPanel pnlMessage;
	private JPanel pnlUser;
	private JPanel pnlOptions;
	private JPanel pnlMessageInput;
	private JPanel pnllista;

	private JLabel lblMessageTitle;
	private JLabel lblUserTitle;

	private JTextField textConnect;
	private JTextField textMessageInput;

	private JButton btnConnect;
	private JButton btnSendMessage;
	
	private JMenuBar menuBar;
    private JMenu fileMenu;
    private JMenuItem aboutItemMenu;
    private JMenuItem estadisticasItemMenu;
    private JMenuItem exitItemMenu;

	private Timer timerUser;
	private Timer timerMessage;

	private JTextArea textAreaUser;
	private JScrollPane scrollMessage;

	private UserModel mUser;
	private MessageModel mMessage;

	private int countUsers;
	private String nameUserCon;

	/**
	 * El Map nos permite acceder rápidamente a los colores asignados por cada usuario, ya que el map
	 * nos permite establecer una colección clave - valor y esto es útil al buscar los colores de cada usuario
	 * al mostrarlos en el chat.
	 *  
	 * El ArrayList es una colección que nos permite realizar búsquedas muy rápidas. Otro punto a favor es que 
	 * conserva el orden de iteración, lo cual es beneficioso, ya que nos interesa mantener el orden de 
	 * iteración para los mensajes y usuarios nuevos que se vayan conectando.
	 */
	
	private Map<String, Color> colors;
	private List<String> displayedUsers;
	private List<Message> displayedMessages;

	/**
	 * Palabaras que no se pueden decir en los mensajes ni en los usuarios.
	 */
	private static final String[] WRONGWORDS = { "imbecil", "idiota", "estupido", "tarado", "subnormal", "maldito",
			"pendejo", "gilipollas", "cabron", "zorra" };

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI frame = new GUI();
					frame.window.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public GUI() {
		this.mUser = new UserModel();
		this.mMessage = new MessageModel();
		this.colors = new HashMap<String, Color>();
		this.displayedUsers = new ArrayList<String>();
		this.displayedMessages = new ArrayList<Message>();
		inicializar();
	}

	/**
	 * Create the frame.
	 * 
	 * @return
	 */
	public void inicializar() {

		// Window

		window = new JFrame();
		window.setTitle("Chat");
		window.setResizable(false);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setBounds(100, 100, 700, 900);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		window.setContentPane(contentPane);

		// Panel Message

		pnlMessage = new JPanel(new BorderLayout());
		pnlMessage.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Margin
		pnlMessage.setBackground(Color.decode("#F5F5F5"));
		pnlMessage.setPreferredSize(new Dimension(430, 0));
		contentPane.add(pnlMessage);

		lblMessageTitle = new JLabel("Mensajes");
		lblMessageTitle.setVisible(false);
		lblMessageTitle.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		lblMessageTitle.setFont(new Font("Gargi", Font.BOLD, 18));
		pnlMessage.add(lblMessageTitle, BorderLayout.NORTH);

		pnllista = new JPanel(new GridLayout(30, 1, 0, 5));

		scrollMessage = new JScrollPane(pnllista);
		scrollMessage.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		pnlMessage.add(scrollMessage, BorderLayout.CENTER);

		pnlMessageInput = new JPanel(new FlowLayout(FlowLayout.CENTER));
		pnlMessage.add(pnlMessageInput, BorderLayout.SOUTH);
		pnlMessageInput.setVisible(false);

		textMessageInput = new JTextField();
		textMessageInput.setPreferredSize(new Dimension(300, 30));
		textMessageInput.setFont(new Font("Arial", Font.PLAIN, 14));
		textMessageInput.setBackground(Color.WHITE);
		textMessageInput.setForeground(Color.BLACK);
		textMessageInput.setCaretColor(Color.BLACK);
		textMessageInput.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createLineBorder(Color.decode("#CCCCCC")),
				BorderFactory.createEmptyBorder(5, 10, 5, 10)));
		pnlMessageInput.add(textMessageInput);

		btnSendMessage = new JButton("Enviar");
		btnSendMessage.setBackground(Color.decode("#4CAF50"));
		btnSendMessage.setForeground(Color.WHITE);
		btnSendMessage.setFocusPainted(false);
		btnSendMessage.setFont(new Font("Arial", Font.BOLD, 14));
		btnSendMessage.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));

		pnlMessageInput.add(btnSendMessage);

		// Panel User

		pnlUser = new JPanel(new BorderLayout());
		pnlUser.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		pnlUser.setBackground(Color.decode("#F5F5F5"));
		pnlUser.setPreferredSize(new Dimension(250, 0));
		contentPane.add(pnlUser, BorderLayout.EAST);

		lblUserTitle = new JLabel("Usuarios");
		lblUserTitle.setBorder(BorderFactory.createEmptyBorder(0, 0, 5, 0));
		lblUserTitle.setFont(new Font("Gargi", Font.BOLD, 18));
		pnlUser.add(lblUserTitle, BorderLayout.NORTH);

		textAreaUser = new JTextArea();
		textAreaUser.setEditable(false);
		textAreaUser.setLineWrap(true);
		textAreaUser.setWrapStyleWord(true);
		textAreaUser.setLayout(new FlowLayout(FlowLayout.LEFT));
		pnlUser.add(textAreaUser, BorderLayout.CENTER);

		// Panel Options

		pnlOptions = new JPanel();
		contentPane.add(pnlOptions, BorderLayout.SOUTH);
		pnlOptions.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		textConnect = new JTextField();
		textConnect.setPreferredSize(new Dimension(300, 30));
		textConnect.setFont(new Font("Arial", Font.PLAIN, 14));
		textConnect.setBackground(Color.WHITE);
		textConnect.setForeground(Color.BLACK);
		textConnect.setCaretColor(Color.BLACK);
		textConnect.setHorizontalAlignment(SwingConstants.CENTER);
		textConnect.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createLineBorder(Color.decode("#CCCCCC")),
				BorderFactory.createEmptyBorder(5, 10, 5, 10)));
		textConnect.setColumns(15);
		pnlOptions.add(textConnect);

		btnConnect = new JButton("Conectar");
		btnConnect.setBackground(Color.decode("#4CAF50"));
		btnConnect.setForeground(Color.WHITE);
		btnConnect.setFocusPainted(false);
		btnConnect.setFont(new Font("Arial", Font.BOLD, 14));
		btnConnect.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
		pnlOptions.add(btnConnect);
		
		// Menu 
		
		menuBar = new JMenuBar();
        window.setJMenuBar(menuBar);

		fileMenu = new JMenu("Archivo");
		menuBar.add(fileMenu);

		aboutItemMenu = new JMenuItem("About");
		aboutItemMenu.setAccelerator(KeyStroke.getKeyStroke("control T"));
		estadisticasItemMenu = new JMenuItem("Estadisticas");
		estadisticasItemMenu.setAccelerator(KeyStroke.getKeyStroke("control S"));
		exitItemMenu = new JMenuItem("Salir");
		exitItemMenu.setAccelerator(KeyStroke.getKeyStroke("control E"));

		fileMenu.add(estadisticasItemMenu);
		fileMenu.add(aboutItemMenu);
		fileMenu.addSeparator();
		fileMenu.add(exitItemMenu);

		// ActionListener
		
		aboutItemMenu.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	showAboutWindow();
            }
        });
        
        estadisticasItemMenu.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	showEstadisticasWindow();
            }
        });
        
        exitItemMenu.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	try {
					mUser.disconnect();
					System.exit(0);
				} catch (SQLException sql) {
					JOptionPane.showMessageDialog(null, "Error DataBase: " + sql.getMessage(), "DataBase", JOptionPane.ERROR_MESSAGE);
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage(), "Exception", JOptionPane.ERROR_MESSAGE);
				}
            }
        });

		btnConnect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				buttonManager(btnConnect.getText(), textConnect.getText());
			}
		});

		btnSendMessage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				buttonManager(btnSendMessage.getText(), textMessageInput.getText());
			}
		});

		window.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent windowEvent) {
				try {
		    		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
					mUser.disconnect();
				} catch (SQLException e) {
					JOptionPane.showMessageDialog(null, "Error DataBase: " + e.getMessage(), "DataBase", JOptionPane.ERROR_MESSAGE);
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, "Error: " + e.getMessage(), "Exception", JOptionPane.ERROR_MESSAGE);
				}
			}
		});

		userTimer();

	}

	/**
     * Maneja las acciones asociadas a los botones de la interfaz
     *
     * @param buttonName El nombre del botón que activó la acción
     * @param text       El texto asociado a la acción del botón
     */	
	private void buttonManager(String buttonMame, String text) {
		try {
			switch (buttonMame) {
			case "Conectar": {
				if (verifyUser(text) == true) {
					mUser.connect(text);
					btnConnect.setText("Desconectar");
					lblMessageTitle.setVisible(true);
					textConnect.setText("");
					pnlMessageInput.setVisible(true);
					nameUserCon = text;
					timerMessages();
					break;
				}
			}
			case "Desconectar": {
				mUser.disconnect();
				timerMessage.cancel();
				textConnect.requestFocus();
				lblMessageTitle.setVisible(false);
				pnlMessageInput.setVisible(false);
				btnConnect.setText("Conectar");
				displayedUsers.clear();
				textAreaUser.removeAll();
				colors.remove(nameUserCon);
				List<User> users = mUser.getConnectedUsers();
				showUsers(users);
				break;
			}
			case "Enviar": {
				if (verifyMessage(text) == true) {
					mMessage.sendMessage(text);
					textMessageInput.setText("");
					textMessageInput.requestFocus();
					break;
				}
			}
			}
		} catch (WrongUser e) {
			JOptionPane.showMessageDialog(null, "Error Usuario: " + e.getMessage(), e.getCodi(), JOptionPane.ERROR_MESSAGE);
		} catch (WrongMessage e) {
			JOptionPane.showMessageDialog(null, "Error Mensaje: " + e.getMessage(), e.getCodi(), JOptionPane.ERROR_MESSAGE);
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Error DataBase: " + e.getMessage(), "DataBase", JOptionPane.ERROR_MESSAGE);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Error: " + e.getMessage(), "Exception", JOptionPane.ERROR_MESSAGE);
		}
	}

	/**
     * Muestra los usuarios conectados en el área de usuarios de la interfaz
     *
     * @param usuarios La lista de usuarios conectados
     */
	private void showUsers(List<User> usuarios) {
		textAreaUser.removeAll();

		for (String displayedUser : displayedUsers) {
			User user = findUser(usuarios, displayedUser);
			if (user != null) {
				Color color = colors.get(displayedUser);
				UserView userView = new UserView(user, color);
				textAreaUser.add(userView);
			}
		}

		for (User user : usuarios) {
			String userName = user.getNick();
			if (!displayedUsers.contains(userName)) {
				Color randomColor = generateRandomColor();
				colors.put(userName, randomColor);
				UserView userView = new UserView(user, randomColor);
				displayedUsers.add(userName);
				textAreaUser.add(userView);
			}
		}

		this.pnlUser.revalidate();
		this.pnlUser.repaint();
	}

	/**
     * Encuentra un usuario en la lista de usuarios
     *
     * @param users     La lista de usuarios en la que buscar
     * @param userName  El nombre del usuario a buscar
     * @return          El usuario encontrado o null si no se encuentra
     */
	private User findUser(List<User> users, String userName) {
		for (User user : users) {
			if (user.getNick().equals(userName)) {
				return user;
			}
		}
		return null;
	}

	/**
     * Verifica si el nombre de usuario es válido
     *
     * @param user El nombre de usuario a verificar
     * @return     True si el nombre de usuario es válido, False de lo contrario
     * @throws WrongUser Si el nombre de usuario no es válido
     */
	private boolean verifyUser(String user) throws WrongUser {
		if (user.length() > 20) {
			throw new WrongUser("A001", "El nicknamde del usuario es demasiado largo.");
		}
		String userName = user.toLowerCase();
		for (String displayedUser : displayedUsers) {
			if (displayedUser.equals(userName)) {
				throw new WrongUser("A002", "El usuario ya existe.");
			}
		}
		for (String wrongWord : WRONGWORDS) {
			if (userName.contains(wrongWord)) {
				throw new WrongUser("A003", "El usuario no puede contener una palabra inadecuada.");
			}
		}
		return true;
	}

	/**
     * Verifica si el mensaje es válido
     *
     * @param message El mensaje a verificar
     * @return        True si el mensaje es válido, False de lo contrario
     * @throws WrongMessage Si el mensaje no es válido
     */
	private boolean verifyMessage(String message) throws WrongMessage {
		String messageText = message.toLowerCase();
		if (messageText.length() == 0) {
			throw new WrongMessage("E001", "No puedes enviar un mesnaje vacio.");
		}

		if (messageText.length() > 255) {
			throw new WrongMessage("E002", "La longitud del mensaje es muy largo.");
		}

		for (String wrongWord : WRONGWORDS) {
			if (messageText.contains(wrongWord)) {
				throw new WrongMessage("E003", "Palabara no aceptada en el chat.");
			}
		}

		return true;
	}

	/**
     * Inicia un temporizador para actualizar la lista de usuarios conectados
     */
	private void userTimer() {
		timerUser = new Timer();
		timerUser.scheduleAtFixedRate(new TimerTask() {
			public void run() {
				try {
					List<User> usuarios = mUser.getConnectedUsers();
					if (countUsers != usuarios.size()) {
						showUsers(usuarios);
						countUsers = usuarios.size();
					}
				} catch (SQLException e) {
					JOptionPane.showMessageDialog(null, "Error DataBase: " + e.getMessage(), "DataBase",
							JOptionPane.ERROR_MESSAGE);
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, "Error: " + e.getMessage(), "Exception",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		}, 0, 3000);
	}

	/**
     * Muestra los mensajes en el área de mensajes de la interfaz
     *
     * @param mensajes La lista de mensajes a mostrar
     * @throws SQLException Si hay un error al acceder a la base de datos
     */
	private void showMessages(List<Message> mensajes) throws SQLException {
		for (Message message : mensajes) {
			if (!displayedMessages.contains(message)) {
				displayedMessages.add(message);
			}
		}

		this.pnllista.removeAll();

		for (Message message : displayedMessages) {
			if (colors.get(message.getNick()) != null) {
				MessageView messageView = new MessageView(message, colors.get(message.getNick()));
				pnllista.add(messageView);
			} else {
				MessageView messageView = new MessageView(message, Color.BLACK);
				pnllista.add(messageView);
			}
		}

		for (int i = 0; i < displayedMessages.size(); i++) {
			System.out.println(displayedMessages.toArray()[i]);
		}

		this.pnllista.revalidate();
		this.pnllista.repaint();
	}

	/**
     * Inicia un temporizador para actualizar la lista de mensajes
     */
	private void timerMessages() {
		timerMessage = new Timer();
		timerMessage.scheduleAtFixedRate(new TimerTask() {
			public void run() {
				try {
					List<Message> mensajes = mMessage.getMessages();
					if (mensajes.size() != 0) {
						System.out.println("Holas");
						showMessages(mensajes);
					}
				} catch (SQLException e) {
					JOptionPane.showMessageDialog(null, "Error DataBase: " + e.getMessage(), "DataBase",
							JOptionPane.ERROR_MESSAGE);
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, "Error: " + e.getMessage(), "Exception",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		}, 0, 2000);
	}

	/**
     * Genera un color aleatorio para representar a un usuario en la interfaz
     *
     * @return El color generado
     */
	private Color generateRandomColor() {
		Random random = new Random();

		int minBrightness = 110; // Mínimo claro
		int maxBrightness = 200; // Mínimo oscuro

		while (true) {
			int r = random.nextInt(256);
			int g = random.nextInt(256);
			int b = random.nextInt(256);

			double brightness = 0.2126 * r + 0.7152 * g + 0.0722 * b;

			if (brightness >= minBrightness && brightness <= maxBrightness && isDistinctColor(r, g, b)) {
				return new Color(r, g, b);
			}
		}
	}

	/**
     * Verifica si un color es lo suficientemente distinto de otros colores
     *
     * @param r El componente rojo del color
     * @param g El componente verde del color
     * @param b El componente azul del color
     * @return  True si el color es lo suficientemente distinto, False de lo contrario
     */
	private boolean isDistinctColor(int r, int g, int b) {
		for (Color color : colors.values()) {
			double distance = Math.sqrt(Math.pow(r - color.getRed(), 2) + Math.pow(g - color.getGreen(), 2)
					+ Math.pow(b - color.getBlue(), 2));
			if (distance < 100) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Muestra la ventana "Acerca de" con información sobre la aplicación y el copyright
	 */
	private void showAboutWindow() {
	    aboutFrame = new JFrame("Acerca de");
	    aboutFrame.setSize(300, 150);
	    aboutFrame.setLocationRelativeTo(null);

	    JLabel label = new JLabel("(c) - Votacions Marc v1.0");
	    label.setHorizontalAlignment(JLabel.CENTER);

	    aboutFrame.getContentPane().add(label);
	    aboutFrame.setVisible(true);
	}

	/**
	 * Muestra la ventana de estadísticas con información sobre el número de usuarios y mensajes que hay
	 */
	private void showEstadisticasWindow() {
	    estadisticasFrame = new JFrame("Estadísticas");
	    estadisticasFrame.setSize(300, 200);
	    estadisticasFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	    estadisticasFrame.setLocationRelativeTo(null);

	    pnlEstadistica = new JPanel();
	    pnlEstadistica.setLayout(new BoxLayout(pnlEstadistica, BoxLayout.Y_AXIS));

	    int usuariosSize = displayedUsers.size();
	    int mensajesSize = displayedMessages.size();

	    JLabel labelPartit = new JLabel("Usuarios: " + usuariosSize);
	    labelPartit.setAlignmentX(Component.CENTER_ALIGNMENT);
	    labelPartit.setFont(new Font("Arial", Font.BOLD, 18));

	    JLabel labelResultat = new JLabel("Mensajes: " + mensajesSize);
	    labelResultat.setAlignmentX(Component.CENTER_ALIGNMENT);
	    labelResultat.setFont(new Font("Arial", Font.BOLD, 18));

	    pnlEstadistica.add(Box.createVerticalGlue());
	    pnlEstadistica.add(labelPartit);
	    pnlEstadistica.add(labelResultat);
	    pnlEstadistica.add(Box.createVerticalGlue());

	    estadisticasFrame.getContentPane().add(pnlEstadistica);
	    estadisticasFrame.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {}

}
