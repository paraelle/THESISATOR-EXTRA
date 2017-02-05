package buisnessLayer;

import java.rmi.RMISecurityManager;
import javax.naming.Context;
import javax.naming.InitialContext;

public class User {
	protected Server server;
	private int id;
	private String username;
	private int userID;
	private String password;
	private UserType type;
	private String name;

	public User(String username, String password, int id, String type, String name) throws Exception {
		super();
		this.username = username;
		this.password = password;
		this.userID = id;
		this.type = UserType.fromString(type);
		this.name = name;
		System.setProperty("java.security.policy", "client.policy");
		System.setSecurityManager(new RMISecurityManager());
		Context namingContext = new InitialContext();
		server = (Server)namingContext.lookup("rmi://localhost/Server");

	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return this.username;
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
