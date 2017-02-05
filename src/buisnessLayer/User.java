package buisnessLayer;

import java.rmi.RMISecurityManager;
import javax.naming.Context;
import javax.naming.InitialContext;

public class User {
	
	public User() throws Exception{
		System.setProperty("java.security.policy", "client.policy");
		System.setSecurityManager(new RMISecurityManager());
		Context namingContext = new InitialContext();
		server = (Server)namingContext.lookup("rmi://localhost/Server");
	}
	public String getName() {
		return name;
	}	
	
	private String name;
	protected Server server;
}
