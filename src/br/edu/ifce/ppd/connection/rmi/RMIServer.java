package br.edu.ifce.ppd.connection.rmi;

import br.edu.ifce.ppd.connection.ChatMessage;
import br.edu.ifce.ppd.connection.GameCommandType;
import br.edu.ifce.ppd.connection.GamePatternComunication;
import br.edu.ifce.ppd.connection.GameTableState;

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
    RMICenter rmiCenter;


    protected RMIServer(int serverId, RMICenter rmiCenter) throws RemoteException {
        super();
        this.serverId   = serverId;
        this.rmiCenter  = rmiCenter;
        registerServer();
        System.out.println("server successfully created.");
    }


    @Override
    public void addMessage(ChatMessage cMessage) {
        rmiCenter.middleController.updateMessageOnScreen(cMessage);
        //System.out.println(cMessage.message);
    }


    @Override
    public void executeCommand(GameCommandType command) throws RemoteException {
        rmiCenter.middleController.executeCommand(command);
    }

    @Override
    public void updateTable(GameTableState tableState) throws RemoteException {
        rmiCenter.middleController.updateTableState(tableState);
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
