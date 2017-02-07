package dataLayer;

import java.io.FileInputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import buisnessLayer.Server;
import buisnessLayer.UserType;
import buisnessLayer.User;
import buisnessLayer.Topic;
import buisnessLayer.Thesis;
import buisnessLayer.Teacher;

public class ServerImpl implements Server {

	private Connection con;
	/**
	 *  Sql Server's TCP/IP should be enabled first for this
	 *  'SQL' user should be created and GRANTED access to DB
	 * @throws Exception
	 */
	public ServerImpl() throws Exception {

		// get DB properties
		Properties props = new Properties();
		props.load(new FileInputStream("database.properties"));

		String server = props.getProperty("server");
		String user = props.getProperty("user");
		String password = props.getProperty("password");
		String Url = props.getProperty("Url");
		String port = props.getProperty("port");
		String database = props.getProperty("database");

		String jdbcUrl = Url + "://" + server + ":" + port + ";user=" + user + ";password=" + password
				+ ";databaseName=" + database + "";
		// connect to DB
		con = DriverManager.getConnection(jdbcUrl);
		System.out.println("# - Connection Obtained");
	}

	/**
	 *  returns null if current user (student) does NOT have topic reserved
	 *  @param userID
	 *  current user primary key
	 */
	public Topic getCurrentStudentTopic(int userID) throws Exception {
		int studentID = getStudentID(userID);
		PreparedStatement pstmt2 = null;
		ResultSet rs = null;
		try {
			String SQL = "SELECT TopicName, [Employee].Name AS 'Supervisor', Number FROM [Topic] "
					+ "JOIN [Employee] ON [Topic].SupervisorID = [Employee].EmployeeID "
					+ "JOIN [Department] ON [Employee].DepartmentNumber = [Department].Number WHERE StudentID = ?";
			pstmt2 = con.prepareStatement(SQL);
			pstmt2.setInt(1, userID);
			rs = pstmt2.executeQuery();
			if (rs.next())
				return new Topic(rs.getString("TopicName"), rs.getString("Supervisor"), rs.getInt("Number"));
			else
				return null;
		} finally {
			pstmt2.close();
		}

	}

	public int getStudentID(int userID) throws SQLException {
		PreparedStatement pstmt2 = null;
		ResultSet rs = null;
		try {
			String SQL = "SELECT StudentID FROM [Student] WHERE UserID = ?";
			pstmt2 = con.prepareStatement(SQL);
			pstmt2.setInt(1, userID);
			rs = pstmt2.executeQuery();
			if (rs.next())
				return rs.getInt("StudentID");
			else
				return 0; // normal students id start from 1
		} finally {
			pstmt2.close();
		}
	}

	public int getDepartmentNumber(int userID) throws Exception {
		PreparedStatement pstmt2 = null;
		ResultSet rs = null;
		try {
			String SQL = "SELECT DepartmentNumber FROM [Employee] WHERE UserID = ?";
			pstmt2 = con.prepareStatement(SQL);
			pstmt2.setInt(1, userID);
			rs = pstmt2.executeQuery();
			if (rs.next())
				return rs.getInt("DepartmentNumber");
			else
				return 0;
		} finally {
			pstmt2.close();
		}
	}

	public String getThesisName(int userID) throws Exception {
		PreparedStatement pstmt2 = null;
		ResultSet rs = null;
		try {
			String SQL = "SELECT ThesisName FROM [User] JOIN [Student] ON [Student].UserID = [User].UserID "
					+ "JOIN [Thesis] ON [Thesis].StudentID = [Student].StudentID WHERE [User].UserID = ?";
			pstmt2 = con.prepareStatement(SQL);
			pstmt2.setInt(1, userID);
			rs = pstmt2.executeQuery();
			if (rs.next())
				return rs.getString("ThesisName");
			else
				return null; // normal students id start from 1
		} finally {
			pstmt2.close();
		}
	}

	/**
	 *   LOGIN
	 *   @return the logged in user object
	 *   
	 */

	@Override
	public User login(String login, String password) throws Exception {
		User currentUser = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			String SQL = "SELECT Type, UserID FROM [User] WHERE Username = ? AND Password = ?";
			pstmt = con.prepareStatement(SQL);
			pstmt.setString(1, login);
			pstmt.setString(2, password);
			rs = pstmt.executeQuery();
			System.out.println("# - Query Executed");

			if (rs.next()) {

				if (UserType.fromString(rs.getString("Type")) == UserType.STUDENT) {
					String SQL1 = "SELECT [User].UserID, Username, Password, Type, [Student].Name AS 'Student', DepartmentID "
							+ "FROM [User] JOIN [Student] ON [User].[UserID] = [Student].[UserID] WHERE [User].UserID = ?";
					PreparedStatement pstmt1 = con.prepareStatement(SQL1);
					pstmt1.setInt(1, rs.getInt("UserID"));
					ResultSet rs1 = pstmt1.executeQuery();
					if (rs1.next())
						currentUser = new User(rs1.getString("Username"), rs1.getString("Password"),
								rs1.getInt("UserID"), rs1.getString("Type"), rs1.getString("Student"),
								rs1.getInt("DepartmentID"));
					return currentUser;
				} else {
					String SQL1 = "SELECT [User].UserID, Username, Password, Type, EmployeeID, Name, Degree, DepartmentNumber "
							+ "FROM [User] JOIN [Employee] ON [User].[UserID] = [Employee].[UserID] WHERE [User].UserID = ?";
					PreparedStatement pstmt1 = con.prepareStatement(SQL1);
					System.out.println(rs.getInt("UserID"));
					pstmt1.setInt(1, rs.getInt("UserID"));
					ResultSet rs1 = pstmt1.executeQuery();
					if (rs1.next())
						currentUser = new User(rs1.getString("Username"), rs1.getString("Password"),
								rs1.getInt("UserID"), rs1.getString("Type"), rs1.getString("Name"),
								rs1.getInt("DepartmentNumber"));
					return currentUser;
				}
			}
		} finally {
			pstmt.close();
			rs.close();
		}
		return currentUser;
	}

	/**
	 *  SEE LIST OF approved TOPICS
	 */
	public List<Topic> getApprovedTopics() throws Exception {
		List<Topic> list = new ArrayList<>();
		Topic topic = null;

		Statement stmt = null;
		ResultSet rs = null;

		try {
			String SQL = "SELECT TopicName, [Employee].Name AS 'Supervisor', Number FROM [Topic] "
					+ "JOIN [Employee] ON [Topic].SupervisorID = [Employee].EmployeeID "
					+ "JOIN [Department] ON [Employee].DepartmentNumber = [Department].Number WHERE isApproved = 1";
			stmt = con.createStatement();
			rs = stmt.executeQuery(SQL);

			while (rs.next()) {
				topic = new Topic(rs.getString("TopicName"), rs.getString("Supervisor"), rs.getInt("Number"));
				list.add(topic);
			}
		} finally {
			stmt.close();
			rs.close();
		}
		return list;
	}


	/**
	 *  for approveTopics, only given department topics
	 *  @param departmentNumber
	 *  department number of current user - HOD
	 */
	public List<Topic> getNotApprovedTopics(int departmentNumber) throws Exception {
		List<Topic> list = new ArrayList<>();
		Topic topic = null;

		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			String SQL = "SELECT TopicName, [Employee].Name AS 'Supervisor', Number  FROM [Topic] "
					+ "JOIN [Employee] ON [Topic].SupervisorID = [Employee].EmployeeID "
					+ "JOIN [Department] ON [Employee].DepartmentNumber = [Department].Number WHERE isApproved = 0 AND Number = ?";
			pstmt = con.prepareStatement(SQL);
			pstmt.setInt(1, departmentNumber);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				topic = new Topic(rs.getString("TopicName"), rs.getString("Supervisor"), rs.getInt("Number"));
				list.add(topic);
			}
		} finally {
			pstmt.close();
			rs.close();
		}
		return list;
	}

	public void approveTopic(String topicName) throws Exception {
		PreparedStatement pstmt = null;
		try {
			String SQL = "UPDATE [Topic] SET isApproved = 1 WHERE TopicName = ?";
			pstmt = con.prepareStatement(SQL);
			pstmt.setString(1, topicName);
			pstmt.executeUpdate();
		} finally {
			pstmt.close();
		}
	}


	/**
	 *  not reserved topics - only topics from current student department
	 *  @param departmentNumber
	 *  department number of current user - student
	 */
	public List<Topic> getAvailableTopics(int departmentNumber) throws Exception {
		List<Topic> list = new ArrayList<>();
		Topic topic = null;

		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			String SQL = "SELECT TopicName, [Employee].Name AS 'Supervisor', Number FROM [Topic] "
					+ "JOIN [Employee] ON [Topic].SupervisorID = [Employee].EmployeeID "
					+ "JOIN [Department] ON [Employee].DepartmentNumber = [Department].Number WHERE isApproved = 1 AND StudentID IS NULL AND Number = ?";
			pstmt = con.prepareStatement(SQL);
			pstmt.setInt(1, departmentNumber);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				topic = new Topic(rs.getString("TopicName"), rs.getString("Supervisor"), rs.getInt("Number"));
				list.add(topic);
			}
		} finally {
			pstmt.close();
			rs.close();
		}
		return list;
	}

	/**
	 * Reserve a topic in database and creates empty Thesis
	 * 
	 * @param userID
	 *  ID of currently logged in user
	 */
	public void reserveTopic(String topicName, int userID) throws Exception {
		PreparedStatement pstmt = null;
		PreparedStatement pstmt1 = null;
		int studentID = 0;
		try {
			studentID = getStudentID(userID);
			String SQL1 = "UPDATE [Topic] SET StudentID = ? WHERE TopicName = ?";
			pstmt = con.prepareStatement(SQL1);
			pstmt.setInt(1, studentID);
			pstmt.setString(2, topicName);
			pstmt.executeUpdate();

			// create empty thesis
			String SQL2 = "INSERT INTO [dbo].[Thesis] (ThesisName, StudentID) VALUES (?, ?); ";
			pstmt1 = con.prepareStatement(SQL2);
			pstmt1.setString(1, topicName);
			pstmt1.setInt(2, userID);
			pstmt1.executeUpdate();
		} finally {
			pstmt.close();
			pstmt1.close();
		}
	}


	/**
	 * Upload thesis file
	 * 
	 *  @return boolean
	 *  false if current user (student) didn't reserve any topic (doesn't have a thesis)
	 */
	public boolean uploadThesis(String content, int userID) throws Exception {
		PreparedStatement pstmt = null;
		String thesisName = null;
		try {
			thesisName = getThesisName(userID);
			if (thesisName == null)
				return false;

			String SQL = "UPDATE [dbo].[Thesis] SET content = ? WHERE ThesisName = ?";
			pstmt = con.prepareStatement(SQL);
			pstmt.setString(1, content);
			pstmt.setString(2, thesisName);
			// pstmt.setInt(3, getStudentID(userID));
			pstmt.executeUpdate();
		} finally {
			// pstmt.close();
		}
		return true;
	}

	/**
	 *  MAKE A REVIEW
	 *  
	 *  for reviewers only
	 */
	public List<Thesis> getTopicsToReview(int userID) throws Exception {
		List<Thesis> list = new ArrayList<>();
		Thesis thesis = null;

		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			String SQL = " SELECT Employee.Name AS 'Reviewer', [Thesis].ThesisName, [Student].Name AS 'StudentName', DepartmentID "
					+ "FROM [Employee] JOIN  [Review] ON "
					+ "[Employee].EmployeeID = [Review].ReviewerID "
					+ "JOIN  [Thesis] ON [Thesis].ThesisName = [Review].ThesisName "
					+ "JOIN [Student] ON [Student].StudentID = [Thesis].StudentID WHERE EmployeeID = ?";
			pstmt = con.prepareStatement(SQL);
			pstmt.setInt(1, userID);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				thesis = new Thesis(rs.getString("ThesisName"), rs.getString("StudentName"), rs.getInt("DepartmentID"));
				list.add(thesis);
			}
		} finally {
			pstmt.close();
		}
		return list;
	}

	public void makeReview(int userID, String thesisName, String content, float mark) throws Exception {
		PreparedStatement pstmt = null;
		try {
			String SQL = "INSERT INTO [dbo].[Review] (ReviewerID, ThesisName, Content, Mark) VALUES (?, ?, ?, ?); ";
			pstmt = con.prepareStatement(SQL);
			pstmt.setString(2, thesisName);
			pstmt.setString(3, content);
			pstmt.setInt(1, userID);
			pstmt.setFloat(4, mark);
			pstmt.executeUpdate();
		} finally {
			pstmt.close();
		}
	}

	/**
	 * ASSIGN REVIEWERS
	 * 
	 * only for current (reviewer)
	 */
	public List<Thesis> getThesesToAssignReviewers(int userID) throws Exception {
		List<Thesis> list = new ArrayList<>();
		Thesis thesis = null;

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int departmentNumber = 0;
		try {
			departmentNumber = getDepartmentNumber(userID);
			if (departmentNumber != 0) {

				String SQL = "SELECT [Thesis].ThesisName AS 'ThesisName', ES.Name AS 'Supervisor', "
						+ "ES.EmployeeID AS 'SupervisorID', ER.Name AS 'Reviewer', "
						+ "[Student].DepartmentID AS 'DepartmentNumber', ER.EmployeeID AS 'ReviewerID' "
						+ "FROM [Employee] AS ES JOIN [Topic] ON [Topic].SupervisorID=ES.EmployeeID "
						+ "JOIN [Thesis] ON [Thesis].ThesisName = [Topic].TopicName "
						+ "JOIN [Student] ON [Student].StudentID = [Thesis].StudentID "
						+ "LEFT JOIN [Review] ON [Thesis].ThesisName = [Review].ThesisName "
						+ "LEFT JOIN [Employee] AS ER ON ER.EmployeeID = [Review].ReviewerID "
						+ "WHERE [Student].DepartmentID = ?";
				pstmt = con.prepareStatement(SQL);
				pstmt.setInt(1, departmentNumber);
				rs = pstmt.executeQuery();

				while (rs.next()) {
					thesis = new Thesis(rs.getString("ThesisName"), rs.getString("Reviewer"),
							rs.getString("Supervisor"), rs.getInt("DepartmentNumber"), rs.getInt("SupervisorID"),
							rs.getInt("ReviewerID"));
					list.add(thesis);
				}
			}
		} finally {
			pstmt.close();
		}
		return list;
	}

	/**
	 *  teachers to choose from to assign to a thesis as REVIEWER
	 */
	public List<Teacher> getTeacherList(String thesis) throws Exception {
		List<Teacher> list = new ArrayList<>();
		Teacher teacher = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		PreparedStatement pstmt1 = null;
		ResultSet rs1 = null;
		int departmentID = 0;
		int supervisorID = 0;
		int HOD = 0;
		int DR = 0;
		try {

			String SQL1 = "SELECT [Student].DepartmentID, SupervisorID, HeadOfDepartmentID, DeansRepresentativeID FROM "
					+ "[Topic] JOIN [Thesis] ON [Topic].TopicName = [Thesis].ThesisName "
					+ "JOIN [Student] ON [Student].StudentID = [Thesis].StudentID "
					+ "JOIN [Department] ON [Department].Number = [Student].DepartmentID " + "WHERE TopicName = ?";
			pstmt1 = con.prepareStatement(SQL1);
			pstmt1.setString(1, thesis);
			rs = pstmt1.executeQuery();
			if (rs.next()) {
				departmentID = rs.getInt("DepartmentID");
				supervisorID = rs.getInt("SupervisorID");
				HOD = rs.getInt("HeadOfDepartmentID");
				DR = rs.getInt("DeansRepresentativeID");
			}

			String SQL = "SELECT Name, Degree, EmployeeID FROM [Employee] AS ET WHERE ET.DepartmentNumber = ?  "
					+ "AND ET.EmployeeID <> ? " + "AND ET.EmployeeID <> ? " + "AND ET.EmployeeID <> ?";
			pstmt = con.prepareStatement(SQL);
			pstmt.setInt(1, departmentID);
			pstmt.setInt(2, HOD);
			pstmt.setInt(3, DR);
			pstmt.setInt(4, supervisorID);
			rs1 = pstmt.executeQuery();

			while (rs1.next()) {
				teacher = new Teacher(rs1.getString("Name"), rs1.getString("Degree"), rs1.getInt("EmployeeID"));
				list.add(teacher);
			}
		} finally {
			 pstmt.close();
			 pstmt1.close();
		}
		return list;
	}

	public void assignReviewer(String thesis, String teacher) throws Exception {
		PreparedStatement pstmt = null;
		try {
			String SQL = "INSERT INTO [Review] (ThesisName, ReviewerID) VALUES(?, ?)";
			pstmt = con.prepareStatement(SQL);
			pstmt.setString(1, thesis);
			pstmt.setInt(2, getEmployeeID(teacher));
			pstmt.executeUpdate();

		} finally {
			pstmt.close();
		}
	}

	public void updateReviewer(String thesis, String teacher) throws Exception {
		PreparedStatement pstmt = null;
		try {
			String SQL = "UPDATE [Review] SET ReviewerID = ? WHERE ThesisName = ?";
			pstmt = con.prepareStatement(SQL);
			pstmt.setString(2, thesis);
			pstmt.setInt(1, getEmployeeID(teacher));
			pstmt.executeUpdate();
		} finally {
			pstmt.close();
		}
	}

	/**
	 * Returns employee ID
	 * 
	 * 
	 *  
	 * @param name
	 * name of the employee
	 * @return int
	 * returns 0 if no such employee in DB
	 */
	public int getEmployeeID(String name) throws Exception {
		PreparedStatement pstmt2 = null;
		ResultSet rs = null;
		try {
			String SQL = "SELECT EmployeeID FROM [Employee] WHERE Name = ?";
			pstmt2 = con.prepareStatement(SQL);
			pstmt2.setString(1, name);
			rs = pstmt2.executeQuery();
			if (rs.next())
				return rs.getInt("EmployeeID");
			else
				return 0;
		} finally {
			pstmt2.close();
		}
	}

//	public static void main(String[] args) throws Exception {
//		ServerImpl server = new ServerImpl();
//		
//		User user = null;
//		user = server.login("student", "student");
//		System.out.println(user.getUserID());
//		System.out.println(user.getId());
//		 System.out.println(server.login("student", "student"));
		//
		// for(Topic topic : server.getApprovedTopics())
		// System.out.println(topic);
		//
		// System.out.println();
		//
		// for(Topic topic : server.getAvailableTopics(8))
		// System.out.println(topic);

		// server.reserveTopic(server.getAvailableTopics(8).get(1).getTopic(),
		// 1);
		// System.out.println();
		// for(Topic topic : server.getAvailableTopics(8))
		// System.out.println(topic);
		// System.out.println();

		// for(Topic topic : server.getNotApprovedTopics(8))
		// System.out.println(topic);

		// server.approveTopic("Topic");

		// server.uploadThesis("jestem arbuzem", 1);
//
//		 for(Thesis topic : server.getTopicsToReview(7)) {
//		 System.out.println(topic.getThesisName());
//		 System.out.println(topic.getStudentName());
//		 }

		// server.makeReview(4, "Mobile Zoo Guide", "jest super", 5.5f);

		//
//		 for(Thesis thesis : server.getThesesToAssignReviewers(4)){
//		 System.out.println(thesis);
//
//		 }

//		for (Teacher teacher : server.getTeacherList("This topic is reserved")) {
//			System.out.println(teacher);
//		};
//
////	server.assignReviewer(teza, naucz);
//
//		 server.updateReviewer("This topic is reserved", "Karol Andek");

//	}
}
