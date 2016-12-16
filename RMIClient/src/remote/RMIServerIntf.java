package remote;

import java.io.Serializable;
import java.rmi.*;

/**
 *
 * @author Daniele
 */

public interface RMIServerIntf extends Remote, Serializable {
    public void addTimestamp(Document document) throws RemoteException;
}