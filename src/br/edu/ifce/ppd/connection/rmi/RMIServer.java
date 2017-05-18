package br.edu.ifce.ppd.connection.rmi;

import br.edu.ifce.ppd.connection.GamePatternComunication;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * Created by alcivanio on 07/05/17.
 */
public class RMIServer extends UnicastRemoteObject implements GamePatternComunication {

    String SERVER_ONE_ID    = "//localhost/server_one";
    String SERVER_TWO_ID    = "//localhost/server_two";

    int serverId = 0;



    protected RMIServer(int serverId) throws RemoteException {
        super();
        this.serverId = serverId;
        registerServer();
        System.out.println("server successfully created.");
    }

    @Override
    public void updateTable(int[][] positions) {
        System.out.print("test - message sent.");
    }

    @Override
    public void teste(String message) {
        System.out.println("Servidor " + serverId + " recebeu mensagem de " + message);
    }


    private void registerServer() {
        String stringId = serverId == 0 ? SERVER_ONE_ID : SERVER_TWO_ID;
        //and starts it.
        try {
            Naming.rebind(stringId, this);
            System.out.println("Server with id " + stringId + " was registered.");

        } catch(Exception e) { e.printStackTrace(); }

    }
}
