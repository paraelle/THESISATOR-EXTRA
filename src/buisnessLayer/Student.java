package buisnessLayer;

public class Student {
	private int studentID;
	private String name;
	private int userID;
	private String topicName;
	private String topicID;
	private String faculty;
	private int department;

	
	public Student(int id, String name, int userID) {
		super();
		this.studentID = id;
		this.name = name;
		this.userID = userID;
	}

	public int getId() {
		return studentID;
	}

	public void setId(int id) {
		this.studentID = id;
	}

	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}

	public int getUserID() {
		return this.userID;
	}

	@Override
	public String toString() {
		return String
				.format("Student [id=%s, name=%s, userID=%s]",
						studentID, name, userID);
	}
	
	
		
}
