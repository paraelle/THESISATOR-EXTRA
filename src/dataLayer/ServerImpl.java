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

public class ServerImpl implements Server{

	private Connection con;
	// Sql Server's TCP/IP should be enabled first for this
	// 'SQL' user should be created and GRANTED access to DB

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

	// ---------------------------------- LOGIN
	// ---------------------------------------
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

	// ---------------------------------- SEE LIST OF TOPICS
	// ---------------------------------------
	// all approved topics
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

	// ---------------------------------- APPROVE TOPICS
	// ---------------------------------------
	// for approveTopics, only given department topics
	// needs department number of current user - HOD
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

	// ---------------------------------- RESERVE TOPIC
	// ---------------------------------------
	// not reserved topics - only topics from current student department
	// needs department number of current user - student
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

	// current user -> userID
	// thesisName is Topic.getTopic()
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

	// ---------------------------------- UPLOAD THESIS
	// ---------------------------------------
	// returns false if current user (student) didn't reserve any topic (doesn't
	// have a thesis)
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

	// ---------------------------------- MAKE A REVIEW
	// ---------------------------------------
	// for reviewers only
	public List<Thesis> getTopicsToReview(int userID) throws Exception {
		List<Thesis> list = new ArrayList<>();
		Thesis thesis = null;

		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			String SQL = " SELECT Employee.Name AS 'Reviewer', [Thesis].ThesisName, [Student].DepartmentID AS 'DepartmentNumber' FROM [Employee] JOIN  [Review] ON "
					+ "[Employee].EmployeeID = [Review].ReviewerID "
					+ "JOIN  [Thesis] ON [Thesis].ThesisName = [Review].ThesisName "
					+ "JOIN [Student] ON [Student].StudentID = [Thesis].StudentID WHERE [Employee].UserID = ?";
			pstmt = con.prepareStatement(SQL);
			pstmt.setInt(1, userID);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				thesis = new Thesis(rs.getString("ThesisName"), rs.getString("Reviewer"),
						rs.getInt("DepartmentNumber"));
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

	// ---------------------------------- ASSIGN REVIEWERS
	// --------------------------------
	// topic - supervisor - reviewer
	// tylko dla obecnego usera -> reviewera
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

	// teachers to choose from to assign to a thesis as REVIEWER
	public List<Teacher> getTeacherList(Thesis thesis) throws Exception {
		List<Teacher> list = new ArrayList<>();
		Teacher teacher = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			String SQL = "SELECT Name, Degree, EmployeeID FROM [Employee] WHERE [EmployeeID] <> ? AND [Employee].DepartmentNumber = ?";
			pstmt = con.prepareStatement(SQL);
			pstmt.setInt(1, thesis.getSupervisorID());
			pstmt.setInt(2, thesis.getDepartmentNumber());
			rs = pstmt.executeQuery();

			while (rs.next()) {
				teacher = new Teacher(rs.getString("Name"), rs.getString("Degree"), rs.getInt("EmployeeID"));
				list.add(teacher);
			}
		} finally {
			pstmt.close();
		}
		return list;
	}

	public void assignReviewer(Thesis thesis, Teacher teacher) throws Exception {
		PreparedStatement pstmt = null;
		try {
			String SQL = "INSERT INTO [Review] (ThesisName, ReviewerID) VALUES(?, ?)";
			pstmt = con.prepareStatement(SQL);
			pstmt.setString(1, thesis.getThesisName());
			pstmt.setInt(2, teacher.getTeacherID());
			pstmt.executeUpdate();
		} finally {
			pstmt.close();
		}
	}

	public void updateReviewer(Thesis thesis, Teacher teacher) throws Exception {
		PreparedStatement pstmt = null;
		try {
			String SQL = "UPDATE [Review] SET ReviewerID = ? WHERE ThesisName = ?";
			pstmt = con.prepareStatement(SQL);
			pstmt.setString(2, thesis.getThesisName());
			pstmt.setInt(1, teacher.getTeacherID());
			pstmt.executeUpdate();
		} finally {
			pstmt.close();
		}
	}

	// test
	public static void main(String[] args) throws Exception {
		ServerImpl server = new ServerImpl();

		System.out.println(server.login("DR8", "DR8"));
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

		// for(Thesis topic : server.getTopicsToReview(5))
		// System.out.println(topic);

		// server.makeReview(4, "Mobile Zoo Guide", "jest super", 5.5f);

		// Thesis teza = null;
		//
		// for(Thesis thesis : server.getThesesToAssignReviewers(4)){
		// System.out.println(thesis);
		// teza = thesis;
		//
		// }
		//
		// Teacher naucz = null;
		// for(Teacher teacher : server.getTeacherList(teza)) {
		// System.out.println(teacher);
		//
		// };

		// server.assignReviewer(teza, naucz);

		// server.updateReviewer(teza, naucz);

	}
}
