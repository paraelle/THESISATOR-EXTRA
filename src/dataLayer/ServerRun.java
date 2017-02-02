package dataLayer;

import javax.naming.Context;
import javax.naming.InitialContext;

public class ServerRun {

	public static void main(String[] args) {
		try
		{
			System.out.println("Starting server");
			ServerImpl server = new ServerImpl();
			System.out.println("Binding name");
			Context namingContext = new InitialContext();
			namingContext.rebind("rmi:THESISATOR-SERVER", server);
			System.out.println("Waiting for clients...");
		}catch (Exception e)
		{
			e.printStackTrace();
		}
	}

}
