import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.UnknownHostException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class MovingSpriteEx extends JFrame {
	
	public static Server server;
	public static Client client;
	static Scanner sc = new Scanner(System.in);
	
	static MovingSpriteEx ex;

    public MovingSpriteEx(Server server) {
        
        initUI(server);
    }
    
public MovingSpriteEx(Client client) {
        
        initUI(client);
    }
    
    private void initUI(Server server) {
        
        add(new Board(server));
        
        setSize(Craft.WINDOW_WIDTH, Craft.WINDOW_HEIGHT);
        setResizable(false);
        
        setTitle("Space Craft");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
private void initUI(Client client) {
        
        add(new Board(client));
        
        setSize(Craft.WINDOW_WIDTH, Craft.WINDOW_HEIGHT);
        setResizable(false);
        
        setTitle("Space Craft");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
    	
    	JFrame fenetre = new JFrame();
		JButton start, join;

		JTextField txtField = new JTextField();
		txtField.setBounds(325, 560, 250, 45);

		start = new JButton("Lancer une nouvelle partie");
		start.setBounds(325, 150, 250, 100);

		join = new JButton("Rejoindre une partie existante");
		join.setBounds(325, 450, 250, 100);

		fenetre.setLayout(null);
		fenetre.setTitle("Network SpaceCraft");
		fenetre.setSize(900, 800);
		fenetre.setLocationRelativeTo(null);
		fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		fenetre.add(start);
		fenetre.add(join);
		fenetre.add(txtField);
		fenetre.setVisible(true);
		
		//Si on lance une nouvelle partie (Mode serveur)
		start.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				server = new Server(6665);

				start.setVisible(false);
				join.setVisible(false);
				txtField.setVisible(false);

				JLabel label;
				try {
					label = new JLabel("En attente d'un client..\n" + InetAddress.getLocalHost().getHostAddress());
					label.setSize(1000, 600);
					label.setLocation(55, 100);
					label.setFont(new Font("Arial", Font.BOLD, 50));
					fenetre.add(label);
				} catch (UnknownHostException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				fenetre.repaint();
				
				EventQueue.invokeLater(new Runnable() {
		            @Override
		            public void run() {
		                
		                ex = new MovingSpriteEx(server);
		            }
		        });

				Thread t = new Thread(new Runnable() {
					// Thread de connexion, puis d'écoute du client
					@Override
					public void run() {
						try {
							server.serverSock = new ServerSocket(server.port);
							server.client = server.serverSock.accept();
							server.dis = new DataInputStream(server.client.getInputStream());
							server.dos = new DataOutputStream(server.client.getOutputStream());
							
							fenetre.setVisible(false);
							ex.setVisible(true);
							
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
							System.exit(NORMAL);
						}

					}
				});
				t.start();

			}
		});
		
		//Si l'on rejoint une partie existante (mode Client)
		join.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String ipAddress = txtField.getText();
				//On vérifie que l'ip saisie a un format correcte
				if (isIp(ipAddress)) {
					client = new Client(ipAddress, 6665);
					fenetre.setVisible(false);
					
					EventQueue.invokeLater(new Runnable() {
			            @Override
			            public void run() {
			                ex = new MovingSpriteEx(client);
			                ex.setVisible(true);
			            }
			        });

				}
				else
					JOptionPane.showMessageDialog(fenetre, "Veuillez saisir une adresse valide !!", "Attention",
							JOptionPane.WARNING_MESSAGE);
			}
		}); 	
		
		
		Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
		    public void run() {
		        // Appelée à la fin du programme (pour quitter proprement).
		    	if (server != null)
					server.write("exit");
				else if(client != null)
					client.write("exit");
		    }
		}));
    	
    }
    
    public static boolean isIp(String text) {
		Pattern p = Pattern.compile(
				"^(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$");
		Matcher m = p.matcher(text);
		return m.find();
	}
}