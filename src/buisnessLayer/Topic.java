package buisnessLayer;

public class Topic {
	private int topicID;
	private String topicName;
	private String supervisorName;	
	private int departmentNumber;

	public Topic(String topicName, String supervisorName, int topicId, int departmentNumber) throws Exception {
		super();
		this.topicID = topicId;
		this.supervisorName = supervisorName;
		this.topicName = topicName;
		this.departmentNumber = departmentNumber;
	}

	public int getID() {
		return this.topicID;
	}

	public String getTopic() {
		return this.topicName;
	}
	
	public String getSupervisor() {
		return this.supervisorName;
	}

	@Override
	public String toString() {
		return String
				.format("Topic [id=%s, topicName=%s, supervisorName=%s]",
						topicID, topicName, supervisorName);
	}
}
