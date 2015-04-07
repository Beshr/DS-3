
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RMI_Interface extends Remote {
	
	public String RMI_Request(String request) throws RemoteException;
}
