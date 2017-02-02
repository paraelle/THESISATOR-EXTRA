package dataLayer;

import java.rmi.RemoteException;

import buisnessLayer.Server;
import buisnessLayer.UserType;

public class ServerImpl implements Server {
	@Override
	public UserType login(String login, String password) throws RemoteException{
		return null;
	}
}
