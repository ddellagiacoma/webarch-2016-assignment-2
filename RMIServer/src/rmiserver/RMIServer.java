package rmiserver;

import remote.RMIServerIntf;
import remote.Document;
import java.rmi.*;
import java.rmi.registry.*;
import java.rmi.server.*;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Daniele
 */
public class RMIServer implements RMIServerIntf {

    //defining the Constructor for the Remote Object
    public RMIServer() throws RemoteException {
        super();
    }

    //providing Implementations for Remote Method
    @Override
    public void addTimestamp(Document document) throws RemoteException {
        String timeStamp = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy").format(new Date());
        document.addString("Viewed on " +timeStamp + "\n");
    }

    public static void main(String args[]) {
        
        try {
            //making the Remote Object Available to Clients
            RMIServerIntf engine = new RMIServer();
            RMIServerIntf stub = (RMIServerIntf) UnicastRemoteObject.exportObject(engine, 0);

            int port = 1099;
            if (args.length > 0) {
                port = Integer.parseInt(args[0]);
            }
            Registry registry = LocateRegistry.createRegistry(port);

            String name = "Compute";
            registry.rebind(name, stub);
            System.out.println("Ready for RMI's");

        } catch (Exception e) {
            System.err.println("RMI exception: ");
            e.printStackTrace();
        }
    }
}