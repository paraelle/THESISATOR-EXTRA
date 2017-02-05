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
import java.awt.CardLayout;
import javax.swing.JButton;
import javax.swing.BoxLayout;
import java.awt.Component;

public class DeanRepresentativeGUI {

	private JFrame frmThesisatorextra;
	private JTable table;
	private JTable tableAssignReviewersTheses;
	private JTable tableAssignReviewersTeachers;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DeanRepresentativeGUI window = new DeanRepresentativeGUI();
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
	public DeanRepresentativeGUI() {
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
		
		JPanel panelAssignReviewers = new JPanel();
		tabbedPane.addTab("Assign reviewers", null, panelAssignReviewers, null);
		panelAssignReviewers.setLayout(new CardLayout(0, 0));
		
		JPanel panelAssignReviewersTheses = new JPanel();
		panelAssignReviewers.add(panelAssignReviewersTheses, "Theses");
		
		JScrollPane scrollPaneAssignReviewersTheses = new JScrollPane();
		panelAssignReviewersTheses.add(scrollPaneAssignReviewersTheses);
		scrollPaneAssignReviewersTheses.setPreferredSize(new Dimension(700, 400));
		
		tableAssignReviewersTheses = new JTable();
		tableAssignReviewersTheses.setModel(new DefaultTableModel(
			new Object[][] {
				{null, null, null, "Add Reviewer"},
			},
			new String[] {
				"Topic", "Supervisor", "Reviewer", "Action"
			}
		) {
			boolean[] columnEditables = new boolean[] {
				false, false, false, true
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		tableAssignReviewersTheses.getColumnModel().getColumn(0).setResizable(false);
		tableAssignReviewersTheses.getColumnModel().getColumn(0).setPreferredWidth(300);
		tableAssignReviewersTheses.getColumnModel().getColumn(1).setResizable(false);
		tableAssignReviewersTheses.getColumnModel().getColumn(1).setPreferredWidth(100);
		tableAssignReviewersTheses.getColumnModel().getColumn(2).setResizable(false);
		tableAssignReviewersTheses.getColumnModel().getColumn(2).setPreferredWidth(100);
		tableAssignReviewersTheses.getColumnModel().getColumn(3).setResizable(false);
		tableAssignReviewersTheses.getColumnModel().getColumn(3).setPreferredWidth(120);
		tableAssignReviewersTheses.getColumnModel().getColumn(3).setMaxWidth(120);
		scrollPaneAssignReviewersTheses.setViewportView(tableAssignReviewersTheses);
		tableAssignReviewersTheses.getColumn("Action").setCellRenderer(new JButtonRenderer());
		tableAssignReviewersTheses.getColumn("Action").setCellEditor(new JButtonEditor(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	CardLayout cardLayout = (CardLayout) panelAssignReviewers.getLayout();
            	cardLayout.show(panelAssignReviewers, "Teachers");
            	//JRowButton button = (JRowButton)e.getSource();
            	//String thesis = (String) tableAssignReviewersTheses.getModel().getValueAt(button.getRow(), 0);
            	//String name = (String) tableAssignReviewersTheses.getModel().getValueAt(button.getRow(), 1);
            	//JOptionPane.showMessageDialog(null, new String("Topic: " + thesis + "\nName: " + name));
            }
        }));
		
		
		
		JPanel panelAssignReviewersTeachers = new JPanel();
		panelAssignReviewers.add(panelAssignReviewersTeachers, "Teachers");
		panelAssignReviewersTeachers.setLayout(new BoxLayout(panelAssignReviewersTeachers, BoxLayout.Y_AXIS));
		
		JScrollPane scrollPaneAssignReviewersTeachers = new JScrollPane();
		scrollPaneAssignReviewersTeachers.setMaximumSize(new Dimension(700, 400));
		scrollPaneAssignReviewersTeachers.setPreferredSize(new Dimension(700, 400));
		panelAssignReviewersTeachers.add(scrollPaneAssignReviewersTeachers);
		
		tableAssignReviewersTeachers = new JTable();
		tableAssignReviewersTeachers.setModel(new DefaultTableModel(
			new Object[][] {
				{null, null, null, null, "Assign"},
			},
			new String[] {
				"Name", "Degree", "Specialization", "Assigned thesis", "Assign"
			}
		) {
			boolean[] columnEditables = new boolean[] {
				false, false, false, false, false
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		tableAssignReviewersTeachers.getColumnModel().getColumn(0).setResizable(false);
		tableAssignReviewersTeachers.getColumnModel().getColumn(1).setResizable(false);
		tableAssignReviewersTeachers.getColumnModel().getColumn(2).setResizable(false);
		tableAssignReviewersTeachers.getColumnModel().getColumn(3).setResizable(false);
		tableAssignReviewersTeachers.getColumnModel().getColumn(4).setResizable(false);
		scrollPaneAssignReviewersTeachers.setViewportView(tableAssignReviewersTeachers);
		
		JButton btnBack = new JButton("Back");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CardLayout cardLayout = (CardLayout) panelAssignReviewers.getLayout();
            	cardLayout.show(panelAssignReviewers, "Theses");
			}
		});
		btnBack.setAlignmentX(Component.CENTER_ALIGNMENT);
		panelAssignReviewersTeachers.add(btnBack);
		tableAssignReviewersTeachers.getColumn("Assign").setCellRenderer(new JButtonRenderer());
		tableAssignReviewersTeachers.getColumn("Assign").setCellEditor(new JButtonEditor(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	JRowButton button = (JRowButton)e.getSource();
            	String thesis = (String) tableAssignReviewersTeachers.getModel().getValueAt(button.getRow(), 0);
            	String name = (String) tableAssignReviewersTeachers.getModel().getValueAt(button.getRow(), 1);
            	JOptionPane.showMessageDialog(null, new String("Topic: " + thesis + "\nName: " + name));
            }
        }));
		
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
