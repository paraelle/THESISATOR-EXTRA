package buisnessLayer;

import java.rmi.Remote;
import java.sql.SQLException;
import java.util.List;

public interface Server extends Remote {
	public User login(String login, String password) throws Exception;
	public int getStudentID(int userID) throws Exception;
//	public int getDepartmentNumber(int userID) throws Exception;

	// ---------------------------------- SEE LIST OF TOPICS
	// all approved topics
	public List<Topic> getApprovedTopics() throws Exception;

	// ---------------------------------- APPROVE TOPICS
	// for approveTopics, only given department topics
	// needs department number of current user - HOD
	public List<Topic> getNotApprovedTopics(int departmentNumber) throws Exception;
	public void approveTopic(String topicName) throws Exception;

	// ---------------------------------- RESERVE TOPIC
	// not reserved topics - only topics from current student department
	// needs department number of current user - student
	public List<Topic> getAvailableTopics(int departmentNumber) throws Exception;

	//thesisName is Topic.getTopic()
	public void reserveTopic(String thesisName, int userID) throws Exception;

	// ---------------------------------- UPLOAD THESIS
	//returns false if current user (student) didn't reserve any topic (doesn't have a thesis)
	public boolean uploadThesis(String content, int userID) throws Exception;

	// ---------------------------------- MAKE A REVIEW
	// for reviewers only
	public List<Thesis> getTopicsToReview(int userID) throws Exception;

	public void makeReview(int userID, String thesisName, String content, float mark) throws Exception;
	
	//---------------------------------- ASSIGN REVIEWERS --------------------------------
	//topic - supervisor - reviewer
	//tylko dla obecnego usera -> reviewera
	public List<Thesis> getThesesToAssignReviewers(int userID) throws Exception;
		
	//teachers to choose from to assign to a thesis as REVIEWER
	public List<Teacher> getTeacherList(Thesis thesis) throws Exception;
	
	public void assignReviewer(Thesis thesis, Teacher teacher) throws Exception;
	
	public void updateReviewer(Thesis thesis, Teacher teacher) throws Exception;
}
