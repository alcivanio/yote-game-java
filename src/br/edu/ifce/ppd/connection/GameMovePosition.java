package br.edu.ifce.ppd.connection;

import java.io.Serializable;

/**
 * Created by alcivanio on 17/04/17.
 */
public class GameMovePosition implements Serializable{
    public int posX;
    public int posY;

    public GameMovePosition(int posX, int posY) {
        this.posX = posX;
        this.posY = posY;
    }
}
