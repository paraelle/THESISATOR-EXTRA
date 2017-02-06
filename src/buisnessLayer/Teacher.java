package buisnessLayer;

public class Teacher {
	private int teacherID;
	private String name;
	private String degree;
	
	public Teacher(String name, String degree, int id) {
		super();
		this.name = name;
		this.degree = degree;
		this.teacherID = id;
	}
	
	public String getName() {
		return this.name;
	}
	
	public String getDegree() {
		return this.degree;
	}
	
	public int getTeacherID() {
		return this.teacherID;
	}
	
	@Override
	public String toString() {
		return String
				.format("Teacher [ Name=%s, degree=%s]",
						 name, degree);
	}
}
