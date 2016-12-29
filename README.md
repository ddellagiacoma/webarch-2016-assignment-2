# RMI Demo

## 1. INTRODUCTION

This assignment is focused on Java RMI (Remote Method Invocation) which allows an object running in one
Java virtual machine (JVM) to invoke methods on an object running in another JVM.

More specifically, this exercise consists in developing two separate programs, a client and a server. The
server shows a remote method **addTimestamp** with a parameter of type Document. The method adds the
string “Viewed on “+Timestamp in the Document. On the other hand, the client creates a new Document
and asks the server to add the string “Viewed on “+Timestamp. Finally, the client prints the content of the
Document.

Document is a custom class that has the method **addString** to append a string at the end of the Document
and a method **toString** to print out the entire Document.

## 2. IMPLEMENTATION

The main method of the server create an instance of **RMIServer** and export it to the RMI runtime.

```java
RMIServerIntf engine = new RMIServer();
RMIServerIntf stub = (RMIServerIntf) UnicastRemoteObject.exportObject(engine, 0);
```

Later, the server creates a RMI registry with the port specified as argument, or with the default port 1099.
Furthermore, it adds the name “Compute” to the RMI registry running on the server.

```java
int port = 1099;
if (args.length > 0) {
  port = Integer.parseInt(args[0]);
}
Registry registry = LocateRegistry.createRegistry(port);

String name = "Compute";
registry.rebind(name, stub);
```

Moreover, the server provides implementations for the method **addTimestamp** specified in the
**RMIServerIntf** interface.

```java
@Override
public void addTimestamp(Document document) throws RemoteException {
  String timeStamp = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy").format(new Date());
  document.addString("Viewed on " +timeStamp + "\n");
}
```

The client locates the registry named “Compute”, creates a remote object of type Document and calls the
remote method **addTimestamp** passing the Document.

```java
Document stub = (Document) UnicastRemoteObject.exportObject(document, 0);

Registry registry = LocateRegistry.getRegistry(host, port);
RMIServerIntf remoteCal = (RMIServerIntf) registry.lookup("Compute");
remoteCal.addTimestamp(stub);
```

The server calls the method **addString** of the Document passing the string “Viewed on “+Timestamp as
parameter. The client executes the method **addString**, appending the string passed as parameter.

```java
private static String timeStamps = "";

@Override
public void addString(String string) throws RemoteException {
  timeStamps += string;
}
```

Finally the client prints all the strings present in its document and unexports the Document in order to
terminate the session.

## 3. DEPLOYMENT

The server can be launched through the terminal compiling the jar file in the following way:

```sh
java -Djava.rmi.server.hostname=<RMIServer-IP> –jar RMIServer.java <port>
```

The **\<port>** attribute is optional, if not specified, the server creates a RMI registry with the default port
1099.

On the other hand, the client can be started in this way:

```sh
java -Djava.rmi.server.hostname=<RMIClient-IP> –jar RMIClient.java <host> <port>
```

In this case both the attributes **\<host>** and **\<port>** are optional. If none of them are specified, the client
tries to locate the registry for the localhost on the default port of 1099.

```java
 String host = "localhost";
int port = 1099;
if (args.length == 1) {
  host=args[0];
} else if (args.length == 2) {
  host=args[0];
  port = Integer.parseInt(args[1]);
}
```

This Java command line parameter is necessary to override the server address that is passed to the stub
object, setting the system property java.rmi.server.hostname:

```sh
-Djava.rmi.server.hostname=<IP>
```

## 4. COMMMENTS AND NOTES

This Oracle tutorial on Java RMI has been followed in order to implement the **RMIServer** and the
**RMIClient**:

**http://docs.oracle.com/javase/tutorial/rmi/overview.html**

Whereas this tutorial has been followed in order to avoid connection refused error messages on the
**RMIClient**:

**https://www.javacodegeeks.com/2013/11/two-things-to-remember-when-using-java-rmi.html**
