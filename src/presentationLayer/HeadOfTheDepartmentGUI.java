package presentationLayer;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import java.awt.BorderLayout;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JSeparator;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.BoxLayout;
import javax.swing.SwingConstants;
import java.awt.Component;

public class HeadOfTheDepartmentGUI {

	private JFrame frmThesisatorextra;
	private JTable tableToApprove;
	private JTable table;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					HeadOfTheDepartmentGUI window = new HeadOfTheDepartmentGUI();
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
	public HeadOfTheDepartmentGUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmThesisatorextra = new JFrame();
		frmThesisatorextra.setResizable(false);
		frmThesisatorextra.setTitle("THESISATOR-EXTRA");
		frmThesisatorextra.setBounds(100, 100, 800, 500);
		frmThesisatorextra.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmThesisatorextra.getContentPane().setLayout(new BorderLayout(0, 0));
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		frmThesisatorextra.getContentPane().add(tabbedPane, BorderLayout.CENTER);
		
		JPanel panelHome = new JPanel();
		tabbedPane.addTab("Home", null, panelHome, null);
		
		JPanel panelThesisTopics = new JPanel();
		tabbedPane.addTab("Thesis topics", null, panelThesisTopics, null);
		panelThesisTopics.setLayout(new BoxLayout(panelThesisTopics, BoxLayout.Y_AXIS));
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBorder(new EmptyBorder(0, 0, 0, 0));
		scrollPane.setPreferredSize(new Dimension(750, 400));
		panelThesisTopics.add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		table.setModel(new DefaultTableModel(
			new Object[][] {
				{"adsdad asdasd aasd asdasd asd asd", "g sdfg sdfg d"},
				{"sa dasd asda d dasda sfgdfgdfsg sdfg sdfgs dfgdfg sdg", "gdfs gdsfgs"},
			},
			new String[] {
				"Thesis topic", "Teacher name"
			}
		) {
			boolean[] columnEditables = new boolean[] {
				false, false
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		table.getColumnModel().getColumn(0).setResizable(false);
		table.getColumnModel().getColumn(0).setPreferredWidth(500);
		table.getColumnModel().getColumn(1).setResizable(false);
		table.getColumnModel().getColumn(1).setPreferredWidth(200);
		
		JPanel panelDefenseSchedule = new JPanel();
		tabbedPane.addTab("Defense schedule", null, panelDefenseSchedule, null);
		
		JPanel panelTopicsToApprove = new JPanel();
		tabbedPane.addTab("Topics to approve", null, panelTopicsToApprove, null);
		
		JScrollPane scrollPaneToApprove = new JScrollPane();
		scrollPaneToApprove.setBorder(new EmptyBorder(0, 0, 0, 0));
		scrollPaneToApprove.setPreferredSize(new Dimension(750, 350));
		panelTopicsToApprove.add(scrollPaneToApprove);
		
		tableToApprove = new JTable();
		scrollPaneToApprove.setViewportView(tableToApprove);
		tableToApprove.setModel(new DefaultTableModel(
				new Object[][] {
					{"adsdad asdasd aasd asdasd asd asd", "g sdfg sdfg d", Boolean.TRUE},
					{"sa dasd asda d dasda sfgdfgdfsg sdfg sdfgs dfgdfg sdg", "gdfs gdsfgs", Boolean.TRUE},
				},
				new String[] {
						"Thesis topic", "Teacher name", "Approve"
				}
				){
			boolean[] columnEditables = new boolean[] {
					false, false, true
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
			@Override
			public Class getColumnClass(int column) {
				return getValueAt(0, column).getClass();
			}
		});
		tableToApprove.getColumnModel().getColumn(0).setResizable(false);
		tableToApprove.getColumnModel().getColumn(0).setPreferredWidth(500);
		tableToApprove.getColumnModel().getColumn(1).setResizable(false);
		tableToApprove.getColumnModel().getColumn(1).setPreferredWidth(200);
		tableToApprove.getColumnModel().getColumn(2).setResizable(false);
		tableToApprove.getColumnModel().getColumn(2).setPreferredWidth(50);
		
		JPanel panel = new JPanel();
		panelTopicsToApprove.add(panel);
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
		
		JButton btnApprove = new JButton("Approve");
		btnApprove.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				for(int i = 0;i < tableToApprove.getRowCount();i++){
					tableToApprove.getValueAt(i, 2);
					//approve topic
				}
			}
		});
		btnApprove.setAlignmentX(Component.CENTER_ALIGNMENT);
		panel.add(btnApprove);
		
		JButton btnDecline = new JButton("Decline");
		btnDecline.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				for(int i = 0;i < tableToApprove.getRowCount();i++){
					tableToApprove.getValueAt(i, 2);
					//decline topic
				}
			}
		});
		
		JSeparator separator = new JSeparator();
		separator.setMaximumSize(new Dimension(20, 0));
		separator.setPreferredSize(new Dimension(50, 0));
		separator.setOrientation(SwingConstants.VERTICAL);
		panel.add(separator);
		panel.add(btnDecline);
		
		JPanel panelMail = new JPanel();
		tabbedPane.addTab("Mail", null, panelMail, null);
		
		JMenuBar menuBar = new JMenuBar();
		frmThesisatorextra.setJMenuBar(menuBar);
		
		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);
		
		JMenuItem mntmLogOut = new JMenuItem("Log out");
		mnFile.add(mntmLogOut);
		
		JMenuItem mntmExit = new JMenuItem("Exit");
		mntmExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});
		mnFile.add(mntmExit);
		
		JMenu mnEdit = new JMenu("Edit");
		menuBar.add(mnEdit);
		
		JMenu mnActions = new JMenu("Actions");
		menuBar.add(mnActions);
		
		JMenu mnTools = new JMenu("Tools");
		menuBar.add(mnTools);
		
		JMenu mnHelp = new JMenu("Help");
		menuBar.add(mnHelp);
		
		JSeparator mnSeparator = new JSeparator();
		menuBar.add(mnSeparator);
		
		JMenu mnHello = new JMenu("Hello, UserName");
		mnHello.setIcon(new ImageIcon("Icon.png"));
		mnHello.setBorderPainted(true);
		mnHello.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		mnHello.setBackground(Color.WHITE);
		mnHello.setForeground(Color.BLACK);
		menuBar.add(mnHello);
		
		JMenuItem mntmLogOut_Right = new JMenuItem("Log out");
		mnHello.add(mntmLogOut_Right);
	}

}
