package br.edu.ifce.ppd.connection;

import java.io.Serializable;

/**
 * Created by alcivanio on 22/04/17.
 */
public class GameTableState implements Serializable {
    public int[][] table;
    public GameUserState opponent;
    public GameUserState myself;

    public GameTableState(int[][] table) {
        this.table = table;
    }
}
