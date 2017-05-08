package br.edu.ifce.ppd.connection.rmi;

import br.edu.ifce.ppd.connection.GamePatternComunication;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

/**
 * Created by alcivanio on 07/05/17.
 */
public class RMIClient {
    String serverName;
    GamePatternComunication tunel;

    public RMIClient() {
        tryStartConnection();

    }


    private void tryStartConnection() {
        startConnection();
        tunel.updateTable(null);

    }

    private void startConnection() {
        try {
            tunel = (GamePatternComunication) Naming.lookup(serverName);

        } catch(Exception e) { e.printStackTrace(); }
    }

}



