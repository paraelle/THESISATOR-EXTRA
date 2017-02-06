package presentationLayer;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

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

import buisnessLayer.Thesis;
import buisnessLayer.Topic;
import buisnessLayer.User;

import javax.swing.ImageIcon;
import java.awt.Component;
import java.awt.CardLayout;
import javax.swing.JLabel;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JTextArea;

public class ReviewerGUI {

	private JFrame frmThesisatorextra;
	private JTable table;
	private JTable tableTheses;
	private JTextArea textFieldReview;
	private User user;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ReviewerGUI window = new ReviewerGUI(args);
					window.frmThesisatorextra.setVisible(true);
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
	public ReviewerGUI(String[] args) throws Exception {
		user = new User(args[0],args[1],Integer.parseInt(args[2]),args[3],args[4],Integer.parseInt(args[5]));
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 * @throws Exception 
	 */
	private void initialize() throws Exception {
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
		
		List<Topic> listTopics = user.getServer().getAvailableTopics(user.getDepartment());
		ArrayList<String[]> list = new ArrayList<String[]>();
		for(Topic topic : listTopics){
			String[] row = {topic.getTopic(),topic.getSupervisor()};
			list.add(row);
		}
		
		table = new JTable();
		scrollPane.setViewportView(table);
		table.setModel(new DefaultTableModel(
				list.toArray(new String[list.size()][]),
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
		
		JPanel panelTheses = new JPanel();
		tabbedPane.addTab("Theses", null, panelTheses, null);
		panelTheses.setLayout(new CardLayout(0, 0));
		
		JPanel panelThesesMain = new JPanel();
		panelTheses.add(panelThesesMain, "Main");
		
		JScrollPane scrollPaneTheses = new JScrollPane();
		panelThesesMain.add(scrollPaneTheses);
		scrollPaneTheses.setPreferredSize(new Dimension(600, 400));
		
		List<Thesis> listThesis = user.getServer().getTopicsToReview(user.getUserID());
		ArrayList<String[]> lisTmodel = new ArrayList<String[]>();
		for(Thesis thesis : listThesis){
			String[] row = {thesis.getThesisName(),thesis.getStudentName(),"More"};
			list.add(row);
		}
		
		tableTheses = new JTable();
		scrollPane.setViewportView(tableTheses);
		tableTheses.setModel(new DefaultTableModel(
				lisTmodel.toArray(new String[lisTmodel.size()][]),
			new String[] {
				"Thesis topic", "Student name", "More"
			}
		) {
			boolean[] columnEditables = new boolean[] {
				false, false, true
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		tableTheses.getColumnModel().getColumn(0).setResizable(false);
		tableTheses.getColumnModel().getColumn(0).setPreferredWidth(500);
		tableTheses.getColumnModel().getColumn(1).setResizable(false);
		tableTheses.getColumnModel().getColumn(1).setPreferredWidth(200);
		tableTheses.getColumnModel().getColumn(2).setResizable(false);
		tableTheses.getColumnModel().getColumn(2).setPreferredWidth(100);
		
		JPanel panelThesesMore = new JPanel();
		panelTheses.add(panelThesesMore, "More");
		panelThesesMore.setLayout(new BoxLayout(panelThesesMore, BoxLayout.Y_AXIS));
		
		JLabel lblMoreThesisTopic = new JLabel("Thesis topic");
		lblMoreThesisTopic.setFont(new Font("Tahoma", Font.PLAIN, 40));
		panelThesesMore.add(lblMoreThesisTopic);
		
		JSeparator separator = new JSeparator();
		separator.setOrientation(SwingConstants.VERTICAL);
		panelThesesMore.add(separator);
		
		JLabel lblMoreStudentName = new JLabel("Student name");
		lblMoreStudentName.setFont(new Font("Tahoma", Font.PLAIN, 30));
		panelThesesMore.add(lblMoreStudentName);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setPreferredSize(new Dimension(0, 40));
		separator_1.setOrientation(SwingConstants.VERTICAL);
		panelThesesMore.add(separator_1);
		
		JPanel panelMoreButtons = new JPanel();
		panelMoreButtons.setAlignmentX(Component.LEFT_ALIGNMENT);
		panelThesesMore.add(panelMoreButtons);
		panelMoreButtons.setLayout(new BoxLayout(panelMoreButtons, BoxLayout.X_AXIS));
		
		JButton btnMoreOpen = new JButton("Open");
		panelMoreButtons.add(btnMoreOpen);
		
		JSeparator separator_4 = new JSeparator();
		separator_4.setPreferredSize(new Dimension(20, 0));
		separator_4.setMaximumSize(new Dimension(20, 0));
		separator_4.setOrientation(SwingConstants.VERTICAL);
		panelMoreButtons.add(separator_4);
		
		JButton btnMoreMakeReview = new JButton("Make a review");
		panelMoreButtons.add(btnMoreMakeReview);
		
		JSeparator separator_5 = new JSeparator();
		separator_5.setMaximumSize(new Dimension(20, 0));
		separator_5.setPreferredSize(new Dimension(20, 0));
		panelMoreButtons.add(separator_5);
		
		JButton btnMoreDownload = new JButton("Download");
		panelMoreButtons.add(btnMoreDownload);
		
		JSeparator separator_2 = new JSeparator();
		separator_2.setOrientation(SwingConstants.VERTICAL);
		panelThesesMore.add(separator_2);
		
		JButton btnMoreBack = new JButton("Back");
		btnMoreBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CardLayout cardLayout = (CardLayout) panelTheses.getLayout();
            	cardLayout.show(panelTheses, "Main");
			}
		});
		panelThesesMore.add(btnMoreBack);
		
		JSeparator separator_3 = new JSeparator();
		separator_3.setMinimumSize(new Dimension(20, 0));
		separator_3.setPreferredSize(new Dimension(20, 70));
		separator_3.setAlignmentX(Component.RIGHT_ALIGNMENT);
		separator_3.setOrientation(SwingConstants.VERTICAL);
		panelThesesMore.add(separator_3);
		
		JPanel panelThesesMakeReview = new JPanel();
		panelTheses.add(panelThesesMakeReview, "Review");
		panelThesesMakeReview.setLayout(new BoxLayout(panelThesesMakeReview, BoxLayout.Y_AXIS));
		
		JLabel lblThesesMakeReviewText = new JLabel("Make a review");
		lblThesesMakeReviewText.setFont(new Font("Tahoma", Font.PLAIN, 40));
		panelThesesMakeReview.add(lblThesesMakeReviewText);
		
		JLabel lblThesesMakeReviewTopic = new JLabel("Topic");
		lblThesesMakeReviewTopic.setFont(new Font("Tahoma", Font.PLAIN, 30));
		panelThesesMakeReview.add(lblThesesMakeReviewTopic);
		
		btnMoreMakeReview.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				lblThesesMakeReviewTopic.setText(lblMoreThesisTopic.getText());
				CardLayout cardLayout = (CardLayout) panelTheses.getLayout();
				cardLayout.show(panelTheses, "Review");
			}
		});
		
		JScrollPane scrollPaneMakeReview = new JScrollPane();
		panelThesesMakeReview.add(scrollPaneMakeReview);
		
		textFieldReview = new JTextArea();
		scrollPaneMakeReview.setViewportView(textFieldReview);
		textFieldReview.setColumns(10);
		
		JPanel panel = new JPanel();
		panel.setAlignmentX(Component.LEFT_ALIGNMENT);
		panelThesesMakeReview.add(panel);
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
		
		JButton btnThesesMakeReviewCancel = new JButton("Cancel");
		btnThesesMakeReviewCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CardLayout cardLayout = (CardLayout) panelTheses.getLayout();
            	cardLayout.show(panelTheses, "More");
			}
		});
		btnThesesMakeReviewCancel.setAlignmentX(Component.CENTER_ALIGNMENT);
		panel.add(btnThesesMakeReviewCancel);
		
		JSeparator separator_6 = new JSeparator();
		separator_6.setMaximumSize(new Dimension(10, 0));
		separator_6.setOrientation(SwingConstants.VERTICAL);
		panel.add(separator_6);
		
		JButton btnThesesMakeReviewFinish = new JButton("Finish");
		btnThesesMakeReviewFinish.setAlignmentX(Component.CENTER_ALIGNMENT);
		panel.add(btnThesesMakeReviewFinish);
		tableTheses.getColumn("More").setCellRenderer(new JButtonRenderer());
		tableTheses.getColumn("More").setCellEditor(new JButtonEditor(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	JRowButton button = (JRowButton)e.getSource();
            	lblMoreThesisTopic.setText((String) tableTheses.getModel().getValueAt(button.getRow(), 0));
            	lblMoreStudentName.setText((String) tableTheses.getModel().getValueAt(button.getRow(), 1));
            	CardLayout cardLayout = (CardLayout) panelTheses.getLayout();
            	cardLayout.show(panelTheses, "More");
            	//JOptionPane.showMessageDialog(null, new String("Topic: " + thesis + "\nName: " + name));
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
