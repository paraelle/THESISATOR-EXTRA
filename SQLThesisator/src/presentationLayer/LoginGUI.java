package presentationLayer;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Insets;
import javax.swing.border.EmptyBorder;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import javax.swing.JPasswordField;

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
	 */
	public LoginGUI() {
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
		frmThesisatorExtra.setBounds(100, 100, 300, 250);
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
		btnLogin.setAlignmentY(Component.BOTTOM_ALIGNMENT);
		btnLogin.setAlignmentX(Component.CENTER_ALIGNMENT);
		frmThesisatorExtra.getContentPane().add(btnLogin);
		
		JSeparator separatorL = new JSeparator();
		separatorL.setOrientation(SwingConstants.VERTICAL);
		frmThesisatorExtra.getContentPane().add(separatorL);
	}

}
