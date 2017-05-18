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

    String SERVER_ONE_ID    = "//localhost/server_one";
    String SERVER_TWO_ID    = "//localhost/server_two";

    int clientId;
    GamePatternComunication remote;

    public RMIClient(int clientId) {
        this.clientId = clientId;
        tryStartConnection();

    }


    private void tryStartConnection() {
        startConnection();

    }

    private void startConnection() {
        try {

            String stringId = clientId == 1 ? SERVER_ONE_ID : SERVER_TWO_ID;
            remote = (GamePatternComunication) Naming.lookup(stringId);

        } catch(Exception e) { e.printStackTrace(); }
    }

}



