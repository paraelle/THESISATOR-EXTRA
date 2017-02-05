package buisnessLayer;

public class User {
	private int id;
	private String username;
	private int userID;
	private String password;
	private UserType type;
	private String name;

	public User(String username, String password, int id, String type, String name) {
		super();
		this.username = username;
		this.password = password;
		this.userID = id;
		this.type = UserType.fromString(type);
		this.name = name;
	}

	public int getId() {
		return this.id;
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
