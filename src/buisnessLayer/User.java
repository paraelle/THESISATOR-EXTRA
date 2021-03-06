package buisnessLayer;

import dataLayer.ServerImpl;

//import java.rmi.RMISecurityManager;
//import javax.naming.Context;
//import javax.naming.InitialContext;

public class User {
	protected Server server;
	private int id;
	private String username;
	private int userID;
	private String password;
	private UserType type;
	private String name;
	private int departmentNumber;

	public User(String username, String password, int id, String type, String name, int departmentNumber) throws Exception {
		super();
		this.username = username;
		this.password = password;
		this.userID = id;
		this.id = id;
		this.type = UserType.fromString(type);
		this.name = name;
		this.departmentNumber = departmentNumber;
//		System.setProperty("java.security.policy", "client.policy");
//		System.setSecurityManager(new RMISecurityManager());
//		Context namingContext = new InitialContext();
		server = new ServerImpl();//(Server)namingContext.lookup("rmi://localhost/THESISATOR-SERVER");
	}

	public int getId() {
		return this.id;
	}
	
	public Server getServer() {
		return this.server;
	}
	
	public int getDepartment() {
		return this.departmentNumber;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return this.username;
	}
	
	public String getPassword() {
		return this.password;
	}
	
	public String getName() {
		return this.name;
	}

	public int getUserID() {
		return this.userID;
	}
	
	public UserType getType() {
		return this.type;
	}

	@Override
	public String toString() {
		return String
				.format("User [id=%s, name=%s, userID=%s, username=%s, password=%s, user type=%s]",
						id, name, userID, username, password, type.toString());
	}
	
	
		
}
