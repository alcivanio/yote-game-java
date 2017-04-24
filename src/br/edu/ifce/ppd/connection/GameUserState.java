package br.edu.ifce.ppd.connection;

import java.io.Serializable;

/**
 * Created by alcivanio on 23/04/17.
 */
public class GameUserState implements Serializable {
    public int piecesInBucket;
    public int tookFromOpponent;


    public GameUserState(int inBucket, int fromOpponent) {
        piecesInBucket      = inBucket;
        tookFromOpponent    = fromOpponent;
    }

    public GameUserState() {
        piecesInBucket      = 12;
        tookFromOpponent    = 0;
    }

    public void restart() {
        piecesInBucket      = 12;
        tookFromOpponent    = 0;
    }

}
