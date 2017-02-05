package buisnessLayer;

public enum UserType {
	NONE, STUDENT, TEACHER, REVIEWER, SUPERVISOR, DEAN_REPRESENTATIVE, HEAD_OF_THE_DEPARTMENT;
	
	public static UserType fromString(String typeString) {
		switch (typeString.toLowerCase()) {
			case "student":
				return UserType.STUDENT;
			case "teacher":
				return UserType.TEACHER;
			case "reviewer":
				return UserType.REVIEWER;
			case "dr":
				return UserType.DEAN_REPRESENTATIVE;
			case "supervisor":
				return UserType.SUPERVISOR;
			case "hod":
				return UserType.HEAD_OF_THE_DEPARTMENT;
		}
		return NONE;
	
//		for(UserType type : UserType.values()) {
//			if ( type.toString().equalsIgnoreCase(typeString))
//				return type;
//		}
	}
}
