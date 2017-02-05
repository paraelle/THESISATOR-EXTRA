package buisnessLayer;

public class Topic {
	private int id;
	private String topicName;
	private String supervisorName;

	public Topic(String topicName, String supervisorName, int id) {
		super();
		this.id = id;
		this.topicName = topicName;
		this.supervisorName = supervisorName;
	}

	public int getId() {
		return this.id;
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
				.format("Topic [id=%s, topic=%s, supervisor=%s]",
						id, topicName, supervisorName);
	}
}
