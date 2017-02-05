package presentationLayer;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Insets;
import javax.swing.border.EmptyBorder;
import buisnessLayer.Server;
import buisnessLayer.User;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import javax.swing.JPasswordField;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;
import java.awt.event.ActionEvent;

public class LoginGUI {

	private JFrame frmThesisatorExtra;
	private JTextField textLogin;
	private JPasswordField textPassword;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginGUI window = new LoginGUI();
					window.frmThesisatorExtra.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 * @throws Exception 
	 */
	public LoginGUI() throws Exception {
		System.setProperty("java.security.policy", "client.policy");
		System.setSecurityManager(new RMISecurityManager());
		Context namingContext = new InitialContext();
		server = (Server)namingContext.lookup("rmi://localhost/Server");
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmThesisatorExtra = new JFrame();
		frmThesisatorExtra.setMaximumSize(new Dimension(300, 500));
		frmThesisatorExtra.setMinimumSize(new Dimension(100, 220));
		frmThesisatorExtra.setTitle("THESISATOR EXTRA");
		frmThesisatorExtra.setBounds(100, 100, 300, 211);
		frmThesisatorExtra.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmThesisatorExtra.getContentPane().setLayout(new BoxLayout(frmThesisatorExtra.getContentPane(), BoxLayout.Y_AXIS));
		
		JLabel lblLogin = new JLabel("Login:");
		lblLogin.setBorder(new EmptyBorder(10, 10, 10, 10));
		lblLogin.setToolTipText("");
		lblLogin.setAlignmentX(Component.CENTER_ALIGNMENT);
		frmThesisatorExtra.getContentPane().add(lblLogin);
		
		textLogin = new JTextField();
		textLogin.setToolTipText("Login");
		textLogin.setMargin(new Insets(0, 0, 0, 0));
		textLogin.setMinimumSize(new Dimension(100, 20));
		textLogin.setMaximumSize(new Dimension(200, 20));
		textLogin.setPreferredSize(new Dimension(150, 20));
		frmThesisatorExtra.getContentPane().add(textLogin);
		textLogin.setColumns(10);
		
		JLabel lblPassword = new JLabel("Password:");
		lblPassword.setBorder(new EmptyBorder(20, 10, 10, 10));
		lblPassword.setToolTipText("");
		lblPassword.setAlignmentX(Component.CENTER_ALIGNMENT);
		frmThesisatorExtra.getContentPane().add(lblPassword);
		
		textPassword = new JPasswordField();
		textPassword.setToolTipText("Password");
		textPassword.setMinimumSize(new Dimension(100, 20));
		textPassword.setMaximumSize(new Dimension(200, 20));
		textPassword.setPreferredSize(new Dimension(150, 20));
		frmThesisatorExtra.getContentPane().add(textPassword);
		textPassword.setColumns(10);
		
		JSeparator separatorH = new JSeparator();
		separatorH.setMinimumSize(new Dimension(0, 20));
		separatorH.setMaximumSize(new Dimension(32767, 20));
		separatorH.setPreferredSize(new Dimension(0, 10));
		separatorH.setOrientation(SwingConstants.VERTICAL);
		frmThesisatorExtra.getContentPane().add(separatorH);
		
		JButton btnLogin = new JButton("Login");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String login = textLogin.getText();
				String password = textPassword.getText();
				try {
					User user = server.login(login, password);
					switch(user.getType()){
					case STUDENT:
						StudentGUI.main(new String[]{user.getUsername(), user.getPassword(), Integer.toString(user.getId()), user.getType().toString(), user.getName()});
						frmThesisatorExtra.dispatchEvent(new WindowEvent(frmThesisatorExtra, WindowEvent.WINDOW_CLOSING));
						break;
					case REVIEWER:
						ReviewerGUI.main(new String[]{user.getUsername(), user.getPassword(), Integer.toString(user.getId()), user.getType().toString(), user.getName()});
						frmThesisatorExtra.dispatchEvent(new WindowEvent(frmThesisatorExtra, WindowEvent.WINDOW_CLOSING));
						break;
					case DEAN_REPRESENTATIVE:
						DeanRepresentativeGUI.main(new String[]{user.getUsername(), user.getPassword(), Integer.toString(user.getId()), user.getType().toString(), user.getName()});
						frmThesisatorExtra.dispatchEvent(new WindowEvent(frmThesisatorExtra, WindowEvent.WINDOW_CLOSING));
						break;
					case HEAD_OF_THE_DEPARTMENT:
						HeadOfTheDepartmentGUI.main(new String[]{user.getUsername(), user.getPassword(), Integer.toString(user.getId()), user.getType().toString(), user.getName()});
						frmThesisatorExtra.dispatchEvent(new WindowEvent(frmThesisatorExtra, WindowEvent.WINDOW_CLOSING));
						break;
					default:
						JOptionPane.showMessageDialog(null, "User does not exixts");
						break;
					}
				} catch (RemoteException e) {
					JOptionPane.showMessageDialog(null, "Connection error!");
				}
			}
		});
		btnLogin.setAlignmentY(Component.BOTTOM_ALIGNMENT);
		btnLogin.setAlignmentX(Component.CENTER_ALIGNMENT);
		frmThesisatorExtra.getContentPane().add(btnLogin);
		
		JSeparator separatorL = new JSeparator();
		separatorL.setOrientation(SwingConstants.VERTICAL);
		frmThesisatorExtra.getContentPane().add(separatorL);
	}
	
	private Server server;
}
