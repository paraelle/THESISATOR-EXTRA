package presentationLayer;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.BorderLayout;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JTabbedPane;
import javax.swing.JPanel;
import javax.swing.JMenuItem;

public class StudentGUI {

	private JFrame frmThesisatorextra;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					StudentGUI window = new StudentGUI();
					window.frmThesisatorextra.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public StudentGUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmThesisatorextra = new JFrame();
		frmThesisatorextra.setTitle("THESISATOR-EXTRA");
		frmThesisatorextra.setBounds(100, 100, 800, 500);
		frmThesisatorextra.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmThesisatorextra.getContentPane().setLayout(new BorderLayout(0, 0));
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		frmThesisatorextra.getContentPane().add(tabbedPane, BorderLayout.CENTER);
		
		JPanel panelHome = new JPanel();
		tabbedPane.addTab("Home", null, panelHome, null);
		
		JPanel panelThesisTopic = new JPanel();
		tabbedPane.addTab("Thesis topic", null, panelThesisTopic, null);
		
		JPanel panelDefenseSchedule = new JPanel();
		tabbedPane.addTab("Defense schedule", null, panelDefenseSchedule, null);
		
		JPanel panelThesis = new JPanel();
		tabbedPane.addTab("Thesis", null, panelThesis, null);
		
		JPanel panelMail = new JPanel();
		tabbedPane.addTab("Mail", null, panelMail, null);
		
		JMenuBar menuBar = new JMenuBar();
		frmThesisatorextra.setJMenuBar(menuBar);
		
		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);
		
		JMenuItem mntmLogOut = new JMenuItem("Log out");
		mnFile.add(mntmLogOut);
		
		JMenuItem mntmExit = new JMenuItem("Exit");
		mnFile.add(mntmExit);
		
		JMenu mnEdit = new JMenu("Edit");
		menuBar.add(mnEdit);
		
		JMenu mnActions = new JMenu("Actions");
		menuBar.add(mnActions);
		
		JMenu mnTools = new JMenu("Tools");
		menuBar.add(mnTools);
		
		JMenu mnHelp = new JMenu("Help");
		menuBar.add(mnHelp);
	}

}
