package br.edu.ifce.ppd.connection;
import java.rmi.Remote;

/**
 * Created by alcivanio on 07/05/17.
 */
public interface GamePatternComunication extends Remote {

    void updateTable(int[][] positions);
}
