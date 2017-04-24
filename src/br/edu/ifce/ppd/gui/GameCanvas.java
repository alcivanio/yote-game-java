package br.edu.ifce.ppd.gui;

import br.edu.ifce.ppd.connection.GameMovePosition;
import br.edu.ifce.ppd.util.MyColors;

import java.awt.*;
import java.awt.event.MouseEvent;

public class GameCanvas extends Canvas {
    final int horizontalNumber = 5;
    final int verticalNumber = 6;

    public int[][] gamePositions = new int[horizontalNumber][verticalNumber];

    int strokeSize  = 6;
    int rectWidth   = 59;
    int rectHeight  = 67;

    public GameCanvas() {
        startCleanArray();
    }

    @Override
    public void paint(Graphics g) {

        Graphics2D g2 = (Graphics2D)g;
        g2.setStroke(new BasicStroke(strokeSize));
        g2.setColor(MyColors.ddd);

        for(int i=0; i<verticalNumber; i++) {
            for(int j=0; j<horizontalNumber; j++) {
                g2.drawRect(j*rectWidth+(strokeSize/2), i*rectHeight+(strokeSize/2), rectWidth, rectHeight);

                //check if we're going to draw a circle here and its color.

                int cGamePosition = gamePositions[j][i];
                if(cGamePosition != 0) {

                    g2.setColor(getColorForUserType(cGamePosition));
                    g2.fillOval(j*rectWidth+(strokeSize)+7, i*rectHeight+(strokeSize)+11, rectWidth-20, rectWidth-20);
                    g2.setColor(MyColors.ddd);
                }



            }
        }
    }

    private Color getColorForUserType(int userType) {
        return userType == 1 ? MyColors.blue : MyColors.ddd;
    }

    public void startCleanArray() {
        for(int i=0; i<verticalNumber; i++){
            for(int j=0; j<horizontalNumber; j++) {
                gamePositions[j][i] = 0;
            }
        }
    }

    public GameMovePosition getPositionBasedOnClick(MouseEvent e){
        int posX = e.getX()/rectWidth;
        int posY = e.getY()/rectHeight;


        GameMovePosition movePos = new GameMovePosition(posX,posY);
        return movePos;

    }

    public int[][] opposeArray(int[][] gameScene) {
        int[][]finalArray = gameScene;
        for(int i=0; i<verticalNumber; i++){
            for(int j=0; j<horizontalNumber; j++) {
                if (finalArray[j][i] == 1) {
                    finalArray[j][i] = 2;
                }
                else if (finalArray[j][i] == 2) {
                    finalArray[j][i] = 1;
                }
            }
        }

        return finalArray;
    }


}
