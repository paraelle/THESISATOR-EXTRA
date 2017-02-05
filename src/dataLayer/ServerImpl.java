package dataLayer;

import java.io.FileInputStream;
import java.io.Serializable;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import buisnessLayer.Server;
import buisnessLayer.UserType;
import buisnessLayer.User;
import buisnessLayer.Topic;

public class ServerImpl implements Server, Serializable {
	private Connection con;
	// � Sql Server's TCP/IP should be enabled first for this
	// � A 'SQL' user should be created and GRANTED access to DB

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

	@Override
	public User login(String login, String password) throws Exception {
		User currentUser = null;
		PreparedStatement pstmt = null;
		ResultSet  rs = null;
		try {
			String SQL = "SELECT Type, UserID FROM [User] WHERE Username = ? AND Password = ?";
			pstmt = con.prepareStatement(SQL);
			pstmt.setString(1, login);
			pstmt.setString(2, password);
			rs = pstmt.executeQuery();
			System.out.println("# - Query Executed");

			if (rs.next()) {

				if (UserType.fromString(rs.getString("Type")) == UserType.STUDENT) {
					String SQL1 = "SELECT [User].UserID, Username, Password, Type, EmployeeID, Name, Degree, DepartmentID " +
							"FROM [User] JOIN [Student] ON [User].[UserID] = [Student].[UserID] WHERE [User].UserID = ?";
					PreparedStatement pstmt1 = con.prepareStatement(SQL1);
					pstmt1.setInt(1, rs.getInt("UserID"));
					ResultSet rs1 = pstmt1.executeQuery();
					if (rs1.next())
						currentUser = new User(rs1.getString("Username"), rs1.getString("Password"),
								rs1.getInt("UserID"), rs1.getString("Type"), rs1.getString("Name"), rs1.getInt("DepartmentID"));
					return currentUser;
				} else {
					String SQL1 = "SELECT [User].UserID, Username, Password, Type, EmployeeID, Name, Degree, DepartmentNumber " +
							"FROM [User] JOIN [Employee] ON [User].[UserID] = [Employee].[UserID] WHERE [User].UserID = ?";
					PreparedStatement pstmt1 = con.prepareStatement(SQL1);
					System.out.println(rs.getInt("UserID"));
					pstmt1.setInt(1, rs.getInt("UserID"));
					ResultSet rs1 = pstmt1.executeQuery();
					if (rs1.next())
						currentUser = new User(rs1.getString("Username"), rs1.getString("Password"),
								rs1.getInt("UserID"), rs1.getString("Type"), rs1.getString("Name"), rs1.getInt("DepartmentNumber"));
					return currentUser;
				}
			}
		}finally {
			pstmt.close();
			rs.close();
		}
		return currentUser;
	}
	//all approved topics
	public List<Topic> getApprovedTopics() throws Exception {
		List<Topic> list = new ArrayList<>();
		Topic topic = null;
		
		Statement stmt = null;
		ResultSet rs = null;
		
		try {
			String SQL = "SELECT TopicID, TopicName, [Employee].Name AS 'Supervisor', Number FROM [Topic] " +
					"JOIN [Employee] ON [Topic].SupervisorID = [Employee].EmployeeID " +
					"JOIN [Department] ON [Employee].DepartmentNumber = [Department].Number WHERE isApproved = 1";
			stmt = con.createStatement();
			rs = stmt.executeQuery(SQL);
			
			while(rs.next()) {
				topic = new Topic(rs.getString("TopicName"), rs.getString("Supervisor"), rs.getInt("TopicID"), rs.getInt("Number"));
				list.add(topic);
			}
		} finally {
			stmt.close();
			rs.close();
		}
		return list;
	}
	//for approveTopics, only given department topics
	//needs department number of current user - HOD
	public List<Topic> getNotApprovedTopics(int departmentNumber) throws Exception {
		List<Topic> list = new ArrayList<>();
		Topic topic = null;
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			String SQL = "SELECT TopicID, TopicName, [Employee].Name AS 'Supervisor', Number  FROM [Topic] " +
					"JOIN [Employee] ON [Topic].SupervisorID = [Employee].EmployeeID " +
					"JOIN [Department] ON [Employee].DepartmentNumber = [Department].Number WHERE isApproved = 0 AND Number = ?";
			pstmt = con.prepareStatement(SQL);
			pstmt.setInt(1, departmentNumber);
			rs = pstmt.executeQuery(SQL);
			
			while(rs.next()) {
				topic = new Topic(rs.getString("TopicName"), rs.getString("Supervisor"), rs.getInt("TopicID"), rs.getInt("Number"));
				list.add(topic);
			}
		} finally {
			pstmt.close();
			rs.close();
		}
		return list;
	}
	
	public void approveTopic(int topicID) throws Exception {
		PreparedStatement pstmt = null;
		try{
			String SQL = "UPDATE [Topic] SET isApproved = 1 WHERE topicID = ?";
			pstmt = con.prepareStatement(SQL);
			pstmt.setInt(1, topicID);
			pstmt.executeQuery();
		} finally {
			pstmt.close();
		}
	}
	//not reserved topics - only topics from current student department
	//needs department number of current user - student
	public List<Topic> getAvailableTopics(int departmentNumber) throws Exception {
		List<Topic> list = new ArrayList<>();
		Topic topic = null;
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			String SQL = "SELECT TopicID, TopicName, [Employee].Name AS 'Supervisor', Number FROM [Topic] " +
					"JOIN [Employee] ON [Topic].SupervisorID = [Employee].EmployeeID " +
					"JOIN [Department] ON [Employee].DepartmentNumber = [Department].Number WHERE isApproved = 1 AND StudentID IS NULL AND Number = ?";
			pstmt = con.prepareStatement(SQL);
			pstmt.setInt(1, departmentNumber);
			rs = pstmt.executeQuery(SQL);
			
			while(rs.next()) {
				topic = new Topic(rs.getString("TopicName"), rs.getString("Supervisor"), rs.getInt("TopicID"), rs.getInt("Number"));
				list.add(topic);
			}
		} finally {
			pstmt.close();
			rs.close();
		}
		return list;
	}
	
	public void reserveTopic(int topicID, int userID) throws Exception {
		PreparedStatement pstmt = null;
		try{
			String SQL = "UPDATE [Topic] SET StudentID = ? WHERE topicID = ?";
			pstmt = con.prepareStatement(SQL);
			pstmt.setInt(1, userID);
			pstmt.setInt(2, topicID);
			pstmt.executeQuery();
		} finally {
			pstmt.close();
		}
	}
	
	public void uploadThesis(String thesisName, String content, int userID, int topicID) throws Exception {
		PreparedStatement pstmt = null;
		try{
			String SQL = "INSERT INTO [dbo].[Thesis] (ThesisName, Content, StudentID, TopicID, isPlagiarism) VALUES (?, ?, ?, ?, 0); ";
			pstmt = con.prepareStatement(SQL);
			pstmt.setString(1, thesisName);
			pstmt.setString(2, content);
			pstmt.setInt(3, userID);
			pstmt.setInt(4, topicID);
			pstmt.executeQuery();
		} finally {
			pstmt.close();
		}
	}
	
	public void makeReview(int userID, String thesisName, String content, float mark) throws Exception  {
		PreparedStatement pstmt = null;
		try{
			String SQL = "INSERT INTO [dbo].[Review] (ReviewerID, ThesisName, Content, Mark) VALUES (?, ?, ?, ?); ";
			pstmt = con.prepareStatement(SQL);
			pstmt.setString(2, thesisName);
			pstmt.setString(3, content);
			pstmt.setInt(1, userID);
			pstmt.setFloat(4, mark);
			pstmt.executeQuery();
		} finally {
			pstmt.close();
		}
	}
	
	
	
//	public List<Teacher> getTeacherList() {
//		List<Topic> list = new ArrayList<>();
//		Topic topic = null;
//		
//		Statement stmt = null;
//		ResultSet rs = null;
//		
//		try {
//			String SQL = "SELECT TopicID, TopicName, [Employee].Name AS 'Supervisor', Degree, [Department].Name AS 'Department' FROM [Topic] " +
//					"JOIN [Employee] ON [Topic].SupervisorID = [Employee].EmployeeID JOIN [Department] ON [Employee].DepartmentNumber = [Department].Number WHERE isApproved = 0";
//			stmt = con.createStatement();
//			rs = stmt.executeQuery(SQL);
//			
//			while(rs.next()) {
//				topic = new Topic(rs.getString("TopicName"), rs.getString("Supervisor"), rs.getInt("TopicID"));
//				list.add(topic);
//			}
//		} finally {
//			stmt.close();
//			rs.close();
//		}
//		return list;
//	}
//	}

	// test
	public static void main(String[] args) throws Exception {
		ServerImpl server = new ServerImpl();

		System.out.println(server.login("HOD8", "HOD8"));


	}
}
