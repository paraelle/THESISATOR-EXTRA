package buisnessLayer;

public class Thesis {
	private String thesisName;
	private String studentName;
	private String supervisorName;
	private int supervisorID;
	private String reviewerName;
	private int reviewerID;
	private int departmentNumber;
	
	public Thesis(String thesisName, String studentName, int departmentNumber) {
		this.thesisName = thesisName;
		this.studentName = studentName;
		this.departmentNumber = departmentNumber;
	}
	
	public Thesis(String thesisName, String studentName, String reviewerName, String supervisorName,int departmentNumber, int supervisorID, int reviewerID) {
		super();
		this.thesisName = thesisName;
		this.studentName = studentName;
		this.reviewerName = reviewerName;
		this.supervisorName = supervisorName;
		this.departmentNumber = departmentNumber;
		this.supervisorID = supervisorID;
		this.reviewerID = reviewerID;
	}
	
	public Thesis(String thesisName, String studentName, String supervisorName,int departmentNumber, int supervisorID, int reviewerID) {
		this.thesisName = thesisName;
		this.studentName = studentName;
		this.supervisorName = supervisorName;
		this.departmentNumber = departmentNumber;
		this.supervisorID = supervisorID;
		this.reviewerID = reviewerID;
	}
	
	public String getSupervisorName() {
		return this.thesisName;	
	}
	
	public String getThesisName() {
		return this.thesisName;	
	}
	
	public String getReviewerName() {
		return this.reviewerName;		
	}
	
	public int getSupervisorID() {
		return this.supervisorID;	
	}
	
	public int getDepartmentNumber() {
		return this.departmentNumber;	
	}
	
	public String getStudentName() {
		return this.getStudentName();	
	}
	
	public int getReviewerID() {
		return this.reviewerID;
	}
	
	@Override
	public String toString() {
		return String
				.format("Thesis [ name=%s, supervisorName=%s, reviewerName=%s, department=%s]",
						 thesisName, supervisorName, reviewerName, departmentNumber);
	}
}
