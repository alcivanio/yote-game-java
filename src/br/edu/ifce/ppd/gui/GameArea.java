package br.edu.ifce.ppd.gui;

import br.edu.ifce.ppd.util.MyColors;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;

/**
 * Created by alcivanio on 13/04/17.
 */
public class GameArea {

    JPanel gamePanel;



    public GameArea() {
        int posX    = 10;
        int posY    = 103;
        int width   = 301;
        int height  = 407;

        gamePanel = new JPanel();
        gamePanel.setBounds(posX, posY, width, height);
        gamePanel.setBackground(Color.white);
        gamePanel.setLayout(null);

        GameCanvas canvas = new GameCanvas();
        canvas.setBounds(0, 0, width, height);

        gamePanel.add(canvas);
    }


}

class GameCanvas extends Canvas {

    public GameCanvas() {
        System.out.print("INICIALIZADOR");
    }

    @Override
    public void paint(Graphics g) {

        int horizontalNumber = 5;
        int verticalNumber = 6;

        int strokeSize  = 6;
        int rectWidth   = 59;
        int rectHeight  = 67;

        Graphics2D g2 = (Graphics2D)g;
        g2.setStroke(new BasicStroke(strokeSize));
        g2.setColor(MyColors.ddd);

        for(int i=0; i<verticalNumber; i++) {
            for(int j=0; j<horizontalNumber; j++) {
                g2.drawRect(j*rectWidth+(strokeSize/2), i*rectHeight+(strokeSize/2), rectWidth, rectHeight);
            }
        }

    }
}
