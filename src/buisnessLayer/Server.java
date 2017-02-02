package buisnessLayer;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Server extends Remote {
	public UserType login(String login, String password) throws RemoteException;
}
