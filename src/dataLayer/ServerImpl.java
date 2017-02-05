package dataLayer;

import java.io.FileInputStream;
import java.rmi.RemoteException;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.sql.ResultSetMetaData;

import buisnessLayer.Server;
import buisnessLayer.UserType;
import buisnessLayer.User;

public class ServerImpl implements Server {
	private Connection con;
//	ResultSetMetaData rsmd;
	// • Sql Server's TCP/IP should be enabled first for this
	// • A 'SQL' user should be created and GRANTED access to DB

	public ServerImpl() throws Exception {

		// get DB properties
		Properties props = new Properties();
		props.load(new FileInputStream("properties.txt"));

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
	public User login(String login, String password) throws RemoteException {
		User currentUser = null;
		try {
			String SQL = "SELECT Type, UserID FROM [User] WHERE Username = ? AND Password = ?";
			PreparedStatement pstmt = con.prepareStatement(SQL);
			pstmt.setString(1, login);
			pstmt.setString(2, password);
			ResultSet rs = pstmt.executeQuery();
			System.out.println("# - Query Executed");

			if (rs.next()) {

				if (UserType.fromString(rs.getString("Type")) == UserType.STUDENT) {
					String SQL1 = "SELECT [User].UserID, Username, Password, Type, EmployeeID, Name, Degree, DepartmentNumber FROM [User] JOIN [Student] ON [User].[UserID] = [Student].[UserID] WHERE [User].UserID = ?";
					PreparedStatement pstmt1 = con.prepareStatement(SQL1);
					pstmt1.setInt(1, rs.getInt("UserID"));
					ResultSet rs1 = pstmt1.executeQuery();
					if (rs1.next())
						currentUser = new User(rs1.getString("Username"), rs1.getString("Password"),
								rs1.getInt("UserID"), rs1.getString("Type"), rs1.getString("Name"));
					return currentUser;
				} else {
					String SQL1 = "SELECT [User].UserID, Username, Password, Type, EmployeeID, Name, Degree, DepartmentNumber FROM [User] JOIN [Employee] ON [User].[UserID] = [Employee].[UserID] WHERE [User].UserID = ?";
					PreparedStatement pstmt1 = con.prepareStatement(SQL1);
					System.out.println(rs.getInt("UserID"));
					pstmt1.setInt(1, rs.getInt("UserID"));
					ResultSet rs1 = pstmt1.executeQuery();
					if (rs1.next())
						currentUser = new User(rs1.getString("Username"), rs1.getString("Password"),
								rs1.getInt("UserID"), rs1.getString("Type"), rs1.getString("Name"));
					return currentUser;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return currentUser;
	}

//	public List<Topic> getAllTopicList() throws RemoteException {
//		Map<String, List<String>> topicTeacherList = new HashMap<String, List<String>>();
//
//		try {
//			List<String> allTopicList = new ArrayList<>();
//			List<String> teacherList = new ArrayList<>();
//			List<String> topicList = new ArrayList<>();
//			String SQL = "SELECT * FROM [Topic] JOIN [Employee] ON [Topic].[SupervisorID] = [Employee].[EmployeeID] ORDER BY EmployeeID";
//			Statement s = con.createStatement();
//			ResultSet r = s.executeQuery(SQL);
//
//			while (r.next()) {
//				allTopicList.add(r.getString("TopicName"));
//				teacherList.add(r.getString("Name"));
//			}
//
//			for (int i = 0; i < teacherList.size(); i++) {
//
//				String currentTeacherName = teacherList.get(i);
//				topicList.add(allTopicList.get(i));
//
//				if (!currentTeacherName.equals(teacherList.get(i + 1))) {
//					topicTeacherList.put(currentTeacherName, topicList);
//					topicList.clear();
//
//				}
//			}
//
//			r.close();
//			s.close();
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		return topicTeacherList;
//	}
//
//	// not reserved
//	public Map<String, List<String>> getAvailableTopicList() throws RemoteException {
//		Map<String, List<String>> topicTeacherList = new HashMap<String, List<String>>();
//		try {
//			List<String> allTopicList = new ArrayList<>();
//			List<String> teacherList = new ArrayList<>();
//			List<String> topicList = new ArrayList<>();
//			String SQL = "SELECT * FROM [Topic] WHERE StudentID IS NULL";
//			Statement s = con.createStatement();
//			ResultSet r = s.executeQuery(SQL);
//
//			while (r.next()) {
//				allTopicList.add(r.getString("TopicName"));
//				teacherList.add(r.getString("Name"));
//			}
//
//			for (int i = 0; i < teacherList.size(); i++) {
//
//				String currentTeacherName = teacherList.get(i);
//				topicList.add(allTopicList.get(i));
//
//				if (!currentTeacherName.equals(teacherList.get(i + 1))) {
//					topicTeacherList.put(currentTeacherName, topicList);
//					topicList.clear();
//
//				}
//			}
//
//			r.close();
//			s.close();
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		return topicTeacherList;
//	}

	// test
	public static void main(String[] args) throws Exception {
		ServerImpl server = new ServerImpl();

		System.out.println(server.login("HOD8", "HOD8"));

		// Map<String, List<String> > al = server.getAllTopicList();
		// for ( String topic : al) {
		// System.out.println(al);
		// }

		// System.out.println();

		// al = server.getAvailableTopicList();
		// for ( String topic : al) {
		// System.out.println(topic);
		// }
	}
}
