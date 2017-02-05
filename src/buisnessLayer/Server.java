package buisnessLayer;

import java.rmi.Remote;
import java.sql.SQLException;
import java.util.List;

public interface Server extends Remote {
	public User login(String login, String password) throws Exception;
	public int getStudentID(int userID) throws SQLException;

	// ---------------------------------- SEE LIST OF TOPICS
	// all approved topics
	public List<Topic> getApprovedTopics() throws Exception;

	// ---------------------------------- APPROVE TOPICS
	// for approveTopics, only given department topics
	// needs department number of current user - HOD
	public List<Topic> getNotApprovedTopics(int departmentNumber) throws Exception;
	public void approveTopic(int topicID) throws Exception;

	// ---------------------------------- RESERVE TOPIC
	// not reserved topics - only topics from current student department
	// needs department number of current user - student
	public List<Topic> getAvailableTopics(int departmentNumber) throws Exception;

	// current user -> userID
	public void reserveTopic(String thesisName, String content, int userID, int topicID) throws Exception;

	// ---------------------------------- UPLOAD THESIS
	public void uploadThesis(String thesisName, String content, int userID, int topicID) throws Exception;

	// ---------------------------------- MAKE A REVIEW
	// for reviewers only
	public List<Thesis> getTopicsToReview(int userID) throws Exception;

	public void makeReview(int userID, String thesisName, String content, float mark) throws Exception;
}
