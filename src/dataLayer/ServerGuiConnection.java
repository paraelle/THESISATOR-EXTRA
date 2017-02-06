package dataLayer;

import java.io.Serializable;
import java.util.List;
import buisnessLayer.Server;
import buisnessLayer.Teacher;
import buisnessLayer.Thesis;
import buisnessLayer.Topic;
import buisnessLayer.User;

public class ServerGuiConnection implements Server, Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8320177486971839794L;
	private ServerImpl imp;
	
	public ServerGuiConnection() throws Exception {
		imp = new ServerImpl();
	}

	@Override
	public User login(String login, String password) throws Exception {
		return imp.login(login, password);
		
	}

//	@Override
//	public int getStudentID(int userID) throws Exception {
//		
//	}
//
//	@Override
//	public int getDepartmentNumber(int userID) throws Exception {
//		// TODO Auto-generated method stub
//		return 0;
//	}

	@Override
	public List<Topic> getApprovedTopics() throws Exception {
		return imp.getApprovedTopics();
	}

	@Override
	public List<Topic> getNotApprovedTopics(int departmentNumber) throws Exception {
		return imp.getNotApprovedTopics(departmentNumber);
	}

	@Override
	public void approveTopic(String topicName) throws Exception {
		imp.approveTopic(topicName);
		
	}

	@Override
	public List<Topic> getAvailableTopics(int departmentNumber) throws Exception {
		return imp.getAvailableTopics(departmentNumber);
	}

	@Override
	public void reserveTopic(String thesisName, int userID) throws Exception {
		imp.reserveTopic(thesisName, userID);
		
	}

	@Override
	public boolean uploadThesis(String content, int userID) throws Exception {
		return imp.uploadThesis(content, userID);
	}

	@Override
	public List<Thesis> getTopicsToReview(int userID) throws Exception {
		return imp.getTopicsToReview(userID);
	}

	@Override
	public void makeReview(int userID, String thesisName, String content, float mark) throws Exception {
		imp.makeReview(userID, thesisName, content, mark);
		
	}

	@Override
	public List<Thesis> getThesesToAssignReviewers(int userID) throws Exception {
		return imp.getThesesToAssignReviewers(userID);
	}

	@Override
	public List<Teacher> getTeacherList(Thesis thesis) throws Exception {
		return imp.getTeacherList(thesis);
	}

	@Override
	public void assignReviewer(Thesis thesis, Teacher teacher) throws Exception {
		imp.assignReviewer(thesis, teacher);
		
	}

	@Override
	public void updateReviewer(Thesis thesis, Teacher teacher) throws Exception {
		imp.updateReviewer(thesis, teacher);
		
	}

}
