package buisnessLayer;

public class Thesis {
	private String thesisName;
	private String studentName;
	private String reviewerName;
	private String content;
	
	public Thesis(String thesisName, String studentName, String reviewerName, String content) {
		super();
		this.thesisName = thesisName;
		this.studentName = studentName;
		this.reviewerName = reviewerName;
		this.content = content;
	}
	
	public Thesis(String thesisName, String studentName) {
		this.thesisName = thesisName;
		this.studentName = studentName;
	}
	
	public Thesis(String thesisName, String studentName, String reviewerName) {
		this.thesisName = thesisName;
		this.studentName = studentName;
		this.reviewerName = reviewerName;
	}
	
	public String getThesisName() {
		return this.thesisName;	
	}
	
	public String getReviewerName() {
		return this.reviewerName;		
	}
	
	public String getContent() {
		return this.content;	
	}
	
	public String getStudentName() {
		return this.getStudentName();	
	}
	
}
