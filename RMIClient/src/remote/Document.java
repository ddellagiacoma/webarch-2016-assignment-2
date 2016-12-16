package remote;

import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author Daniele
 */
public interface Document extends Remote,Serializable{
    public void addString(String string) throws RemoteException;
    public void toString(String timeStamps) throws RemoteException;
}