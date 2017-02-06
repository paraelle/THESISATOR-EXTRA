package buisnessLayer;

public class Topic {
	private String topicName;
	private String supervisorName;	
	private int departmentNumber;

	public Topic(String topicName, String supervisorName, int departmentNumber) throws Exception {
		super();
		this.supervisorName = supervisorName;
		this.topicName = topicName;
		this.departmentNumber = departmentNumber;
	}
	
	public int getDepartmentnumber() {
		return this.departmentNumber;
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
				.format("Topic [ topicName=%s, supervisorName=%s]",
						 topicName, supervisorName);
	}
}
