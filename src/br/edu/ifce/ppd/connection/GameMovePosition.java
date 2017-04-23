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

    public int getDistanceX(GameMovePosition p) {
        return Math.abs(p.posX - posX);
    }

    public int getDistanceY(GameMovePosition p) {
        return Math.abs(p.posY - posY);
    }

    public boolean isSimpleMove(GameMovePosition p) {
        int disX = getDistanceX(p);
        int disY = getDistanceY(p);

        return (disX==0 && disY==1) || (disX==1 && disY==0);
    }

    public boolean isDoubleMove(GameMovePosition p) {
        int disX = getDistanceX(p);
        int disY = getDistanceY(p);

        return (disX==0 && disY==2) || (disX==2 && disY==0);

        /*GameMovePosition nUpper = new GameMovePosition(posX, posY-1);
        GameMovePosition nRight = new GameMovePosition(posX+1, posY);
        GameMovePosition nDown  = new GameMovePosition(posX, posY+1);
        GameMovePosition nLeft  = new GameMovePosition(posX-1, posY);

        return p.isEqual(nUpper) || p.isEqual(nRight) || p.isEqual(nDown) || p.isEqual(nLeft);*/
    }

    public GameMovePosition getMiddlePointForDoubleMove(GameMovePosition p) {
        if (!isDoubleMove(p)) {return null;}

        boolean xEqual  = p.posX == posX;
        boolean yEqual  = p.posY == posY;
        boolean xLess   = p.posX <  posX;
        boolean yLess   = p.posY <  posY;

        int nPosX = xEqual ? posX : (xLess ? posX - 1 : posX + 1);
        int nPosY = yEqual ? posY : (yLess ? posY - 1 : posY + 1);

        return new GameMovePosition(nPosX, nPosY);
    }


    public boolean isAllowedDoubleMove(GameMovePosition p, int[][] arrayRef) {
        GameMovePosition refPoint = getMiddlePointForDoubleMove(p);
        if(refPoint != null) {
            return arrayRef[refPoint.posX][refPoint.posY] == 2;
        }

        return false;
    }


    public boolean isEqual(GameMovePosition p) {
        return p.posX == posX && p.posY == posY;
    }



}
