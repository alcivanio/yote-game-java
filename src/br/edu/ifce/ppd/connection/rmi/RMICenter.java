package br.edu.ifce.ppd.connection.rmi;

import br.edu.ifce.ppd.connection.GamePatternComunication;

/**
 * Created by alcivanio on 17/05/17.
 */
public class RMICenter implements GamePatternComunication{

    int         centerID;
    RMIClient   client;
    RMIServer   server;

    public RMICenter(int id) {
        centerID = id;
        startServer();
    }


    /*
    Server methods <3
    */

    private void startServer() {
        try {
            server = new RMIServer(centerID);
        }catch (Exception e) { e.printStackTrace(); }
    }



    /*
    As an organized boy, I'll put here just the client methods.
    */
    private void checkForClientInitialization() {
        if(client == null) {

            client = new RMIClient(centerID);
        }

    }


    @Override
    public void updateTable(int[][] positions) {
        checkForClientInitialization();
    }

    @Override
    public void teste(String message) {
        try {
            checkForClientInitialization();
            client.remote.teste(message);
        }catch(Exception e){ e.printStackTrace(); }
    }
}
