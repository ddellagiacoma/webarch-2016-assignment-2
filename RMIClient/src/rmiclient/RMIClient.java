package rmiclient;

import java.rmi.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import remote.Document;
import remote.RMIServerIntf;

/**
 *
 * @author Daniele
 */
public class RMIClient implements Document {

    private static String timeStamps = "";

    @Override
    public void addString(String string) throws RemoteException {
        timeStamps += string;
    }

    @Override
    public void toString(String timeStamps) throws RemoteException {
        System.out.println(timeStamps);
    }

    public static void main(String args[]) {

        Document document = new RMIClient();
        try {
            
            String host = "localhost";
            int port = 1099;
            if (args.length == 1) {
                host=args[0];
            } else if (args.length == 2) {
                host=args[0];
                port = Integer.parseInt(args[1]);
            }
            
            Document stub = (Document) UnicastRemoteObject.exportObject(document, 0);

            Registry registry = LocateRegistry.getRegistry(host, port);
            RMIServerIntf remoteCal = (RMIServerIntf) registry.lookup("Compute");
            remoteCal.addTimestamp(stub);

            ((RMIClient) document).toString(timeStamps);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                UnicastRemoteObject.unexportObject(document, false);
            } catch (NoSuchObjectException e) {
                e.printStackTrace();
            }
        }
    }
}