package br.edu.ifce.ppd.connection;
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Created by alcivanio on 07/05/17.
 */
public interface GamePatternComunication extends Remote {

    void addMessage(ChatMessage cMessage) throws RemoteException;
    void executeCommand(GameCommandType command) throws RemoteException;
    void updateTable(GameTableState tableState) throws RemoteException;
}
