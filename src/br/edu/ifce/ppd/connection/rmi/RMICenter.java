package br.edu.ifce.ppd.connection.rmi;

import br.edu.ifce.ppd.connection.ChatMessage;
import br.edu.ifce.ppd.connection.GameCommandType;
import br.edu.ifce.ppd.connection.GamePatternComunication;
import br.edu.ifce.ppd.connection.GameTableState;
import br.edu.ifce.ppd.middle.MiddleController;

import java.rmi.RemoteException;

/**
 * Created by alcivanio on 17/05/17.
 */
public class RMICenter implements GamePatternComunication{

    int                 centerID;
    RMIClient           client;
    RMIServer           server;
    MiddleController    middleController;


    public RMICenter(int id, MiddleController middleController) {
        centerID                = id;
        this.middleController   = middleController;
        startServer();
    }


    /*
    Server methods <3
    */

    private void startServer() {
        try {
            server = new RMIServer(centerID, this);
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
    public void addMessage(ChatMessage cMessage) {
        try {
            checkForClientInitialization();
            client.remote.addMessage(cMessage);
        }catch(Exception e){ e.printStackTrace(); }
    }

    @Override
    public void executeCommand(GameCommandType command) {
        try {
            checkForClientInitialization();
            client.remote.executeCommand(command);
        }catch(Exception e){ e.printStackTrace(); }
    }

    @Override
    public void updateTable(GameTableState tableState) {
        try {
            checkForClientInitialization();
            client.remote.updateTable(tableState);
        }catch(Exception e){ e.printStackTrace(); }
    }


}
