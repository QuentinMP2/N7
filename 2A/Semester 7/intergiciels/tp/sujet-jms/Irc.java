
import java.awt.Button;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Enumeration;
import java.util.Objects;
import java.util.Vector;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.StreamMessage;
import javax.jms.Topic;
import javax.jms.TextMessage;
import javax.jms.Destination;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

public class Irc {
	public static TextArea		text;
	public static TextField		data;
	public static Frame 		frame;

	public static String url = ActiveMQConnection.DEFAULT_BROKER_URL;
	public static String subject = "MyQueue";

	public static Vector<String> users = new Vector<String>();
	public static String myName;
	public static boolean connected;

	public static ConnectionFactory	connectionFactory;
	public static Connection       	connection;
	public static Session			session;
	public static MessageConsumer	consumer;
	public static MessageProducer	producer;
	public static Topic				topic;

	public static void main(String argv[]) {

		if (argv.length != 1) {
			System.out.println("java Irc <name>");
			return;
		}
		myName = argv[0];

		// creation of the GUI 
		frame=new Frame();
		frame.setLayout(new FlowLayout());

		text=new TextArea(10,55);
		text.setEditable(false);
		text.setForeground(Color.red);
		frame.add(text);

		data=new TextField(55);
		frame.add(data);

		Button write_button = new Button("write");
		write_button.addActionListener(new writeListener());
		frame.add(write_button);

		Button connect_button = new Button("connect");
		connect_button.addActionListener(new connectListener());
		frame.add(connect_button);

		Button who_button = new Button("who");
		who_button.addActionListener(new whoListener());
		frame.add(who_button);

		Button leave_button = new Button("leave");
		leave_button.addActionListener(new leaveListener());
		frame.add(leave_button);

		frame.setSize(470,300);
		text.setBackground(Color.black); 
		frame.setVisible(true);
	}

	/* allow to print something in the window */
	public static void print(String msg) {
		try {
			text.append(msg+"\n");
		} catch (Exception ex) {
			ex.printStackTrace();
		}	
	}
}



// action invoked when the "write" button is clicked
class writeListener implements ActionListener {
	public void actionPerformed (ActionEvent ae) {
		try {
			// Créer le message à partir de ce que contient le TextField.
			Message message = Irc.session.createTextMessage(Irc.myName + ": " + Irc.data.getText());

			// Envoyer le message.
			Irc.producer.send(message);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}

// action invoked when the "connect" button is clicked
class connectListener implements ActionListener {
	public void actionPerformed (ActionEvent ae) {
		if (!Irc.connected) {
			try {
				// Initialiser la session.
				Irc.connectionFactory = new ActiveMQConnectionFactory(Irc.url);
				Irc.connection = Irc.connectionFactory.createConnection();
				Irc.connection.start();
				Irc.session = Irc.connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
				Destination destination = Irc.session.createTopic(Irc.subject);
				Irc.producer = Irc.session.createProducer(destination);
				Irc.consumer = Irc.session.createConsumer(destination);

				// Listener pour les messages.
				MessageListener listener = new MessageListener() {
					public void onMessage(Message msg) {
						try {
							// Tableau contenant le message d'information (Connected, Leaved, Here)
							// et le nom de l'utilisateur.
							String[] infoMsg = ((TextMessage) msg).getText().split(": ");
							if (Objects.equals(infoMsg[0], "Connected") && !(Irc.users.contains(infoMsg[1]))) {
								Irc.users.add(infoMsg[1]);

								/* Si le message ne vient pas de moi-même,
								 * je signale aux autres que je suis là.
								 */
								if (!Objects.equals(infoMsg[1], Irc.myName)) {
									TextMessage here = Irc.session.createTextMessage("Here: " + Irc.myName);
									Irc.producer.send(here);
								}
							} else if (Objects.equals(infoMsg[0], "Leaved")) {
								Irc.users.remove(infoMsg[1]);
							} else if (Objects.equals(infoMsg[0], "Here") && !(Irc.users.contains(infoMsg[1]))) {
								Irc.users.add(infoMsg[1]);
							} else {
								TextMessage textMsg = (TextMessage) msg;
								Irc.print(textMsg.getText());
							}
						} catch (Exception ex) {
							ex.printStackTrace();
						}
					}
				};

				Irc.connected = true;

				// Prévenir qu'un utilisateur s'est connecté.
				TextMessage message = Irc.session.createTextMessage("Connected: " + Irc.myName);
				Irc.producer.send(message);

				// Commencer à écouter les messages.
				Irc.consumer.setMessageListener(listener);
			} catch(Exception ex){
				ex.printStackTrace();
			}
		}
	}
}  



// action invoked when the "who" button is clicked
class whoListener implements ActionListener {
	public void actionPerformed (ActionEvent ae) {
		try {
			Irc.print("List of connected users:");

			// Lister tous les utilisateurs présents.
			for (String user : Irc.users) {
				Irc.print("  - " + user);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}


// action invoked when the "leave" button is clicked
class leaveListener implements ActionListener {
	public void actionPerformed (ActionEvent ae) {
		try {
			// Prévenir qu'on se déconnecte.
			TextMessage leave = Irc.session.createTextMessage("Leaved: " + Irc.myName);
			Irc.producer.send(leave);

			/* Supprimer tous les utilisateurs.
			 * On crée un nouveau vecteur au lieu de supprimer
			 * un à un tous les utilisateurs.
			 */
			Irc.users = new Vector<String>();

			// On ferme la connexion.
			Irc.connected = false;
			Irc.connection.close();

		} catch (Exception e) {
			/* On a essayé de se déconnecter alors qu'on n'était
			 * pas connecté.
			 */
			System.out.println("Impossible de se déconnecter alors qu'on n'est pas connecté.");
		}
	}
}

