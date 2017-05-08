package br.edu.ifce.ppd.connection.rmi;

import br.edu.ifce.ppd.connection.GamePatternComunication;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * Created by alcivanio on 07/05/17.
 */
public class RMIServer extends UnicastRemoteObject implements GamePatternComunication {

    protected RMIServer() throws RemoteException {
        super();
        System.out.print("server successfully created.");
    }

    @Override
    public void updateTable(int[][] positions) {
        System.out.print("test - message sent.");
    }
}
