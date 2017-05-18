package br.edu.ifce.ppd.connection;
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Created by alcivanio on 07/05/17.
 */
public interface GamePatternComunication extends Remote {

    void updateTable(int[][] positions) throws  RemoteException;;
    void teste(String message) throws RemoteException;;
}
