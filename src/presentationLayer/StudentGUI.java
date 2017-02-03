package presentationLayer;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.JFrame;
import java.awt.BorderLayout;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JTabbedPane;
import javax.swing.JPanel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JSeparator;
import java.awt.Color;
import javax.swing.border.MatteBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.ImageIcon;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;
import java.awt.Dimension;
import javax.swing.border.EmptyBorder;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JTextField;
import java.awt.Component;
import java.awt.Font;
import javax.swing.SwingConstants;




public class StudentGUI {

	private JFrame frmThesisatorextra;
	private JTable table;
	private JTextField txtFilepath;

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
		frmThesisatorextra.setResizable(false);
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
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBorder(new EmptyBorder(0, 0, 0, 0));
		scrollPane.setPreferredSize(new Dimension(750, 400));
		panelThesisTopic.add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		table.setModel(new DefaultTableModel(
			new Object[][] {
				{"adsdad asdasd aasd asdasd asd asd", "g sdfg sdfg d", "Reserve"},
				{"sa dasd asda d dasda sfgdfgdfsg sdfg sdfgs dfgdfg sdg", "gdfs gdsfgs", "Reserve"},
			},
			new String[] {
				"Thesis topic", "Teacher name", "Reserve"
			}
		));
		table.getColumnModel().getColumn(0).setResizable(false);
		table.getColumnModel().getColumn(0).setPreferredWidth(500);
		table.getColumnModel().getColumn(1).setResizable(false);
		table.getColumnModel().getColumn(1).setPreferredWidth(200);
		table.getColumnModel().getColumn(2).setResizable(false);
		table.getColumnModel().getColumn(2).setPreferredWidth(100);
		table.getColumn("Reserve").setCellRenderer(new JButtonRenderer());
		table.getColumn("Reserve").setCellEditor(new JButtonEditor(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	JRowButton button = (JRowButton)e.getSource();
            	String thesis = (String) table.getModel().getValueAt(button.getRow(), 0);
            	String name = (String) table.getModel().getValueAt(button.getRow(), 1);
            	JOptionPane.showMessageDialog(null, new String("Topic: " + thesis + "\nName: " + name));
            }
        }));
		
		JPanel panelDefenseSchedule = new JPanel();
		tabbedPane.addTab("Defense schedule", null, panelDefenseSchedule, null);
		
		JPanel panelThesis = new JPanel();
		tabbedPane.addTab("Thesis", null, panelThesis, null);
		panelThesis.setLayout(new BoxLayout(panelThesis, BoxLayout.Y_AXIS));
		
		JLabel lblThesisTopic = new JLabel("Topic of the thesis");
		lblThesisTopic.setFont(new Font("Tahoma", Font.PLAIN, 40));
		panelThesis.add(lblThesisTopic);
		
		JSeparator separator = new JSeparator();
		separator.setPreferredSize(new Dimension(0, 10));
		separator.setOrientation(SwingConstants.VERTICAL);
		panelThesis.add(separator);
		
		JButton btnCheckThesis = new JButton("Check Thesis");
		panelThesis.add(btnCheckThesis);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setPreferredSize(new Dimension(0, 10));
		separator_1.setOrientation(SwingConstants.VERTICAL);
		panelThesis.add(separator_1);
		
		JLabel lblTeacherName = new JLabel("Teacher name");
		lblTeacherName.setFont(new Font("Tahoma", Font.PLAIN, 20));
		panelThesis.add(lblTeacherName);
		
		JSeparator separator_2 = new JSeparator();
		separator_2.setPreferredSize(new Dimension(0, 10));
		separator_2.setOrientation(SwingConstants.VERTICAL);
		panelThesis.add(separator_2);
		
		JButton btnCheckReviews = new JButton("Check the reviews");
		panelThesis.add(btnCheckReviews);
		
		JSeparator separator_3 = new JSeparator();
		separator_3.setPreferredSize(new Dimension(0, 100));
		separator_3.setOrientation(SwingConstants.VERTICAL);
		panelThesis.add(separator_3);
		
		JPanel panel = new JPanel();
		panel.setPreferredSize(new Dimension(400, 20));
		panel.setAlignmentX(Component.LEFT_ALIGNMENT);
		panel.setMaximumSize(new Dimension(400, 20));
		panelThesis.add(panel);
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
		
		txtFilepath = new JTextField();
		txtFilepath.setColumns(1);
		txtFilepath.setText("Select file");
		panel.add(txtFilepath);
		
		JButton btnBrowse = new JButton("Browse");
		btnBrowse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser();
				FileNameExtensionFilter filter = new FileNameExtensionFilter(
					    "Pdf", "pdf");
				fileChooser.setFileFilter(filter);
				fileChooser.showOpenDialog(null);
				File pfile = fileChooser.getSelectedFile();
				try {
					txtFilepath.setText(pfile.getCanonicalPath());
				} catch (IOException ex) {
				}
			}
		});
		btnBrowse.setAlignmentX(Component.CENTER_ALIGNMENT);
		panel.add(btnBrowse);
		
		JSeparator separator_4 = new JSeparator();
		separator_4.setPreferredSize(new Dimension(0, 10));
		separator_4.setOrientation(SwingConstants.VERTICAL);
		panelThesis.add(separator_4);
		
		JButton btnUpload = new JButton("Upload thesis");
		panelThesis.add(btnUpload);
		
		JSeparator separator_5 = new JSeparator();
		separator_5.setMinimumSize(new Dimension(20, 100));
		separator_5.setAlignmentX(Component.RIGHT_ALIGNMENT);
		separator_5.setOrientation(SwingConstants.VERTICAL);
		separator_5.setPreferredSize(new Dimension(20, 100));
		panelThesis.add(separator_5);
		
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
