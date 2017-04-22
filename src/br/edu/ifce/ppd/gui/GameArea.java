package br.edu.ifce.ppd.gui;

import br.edu.ifce.ppd.connection.GameMovePosition;
import br.edu.ifce.ppd.middle.MiddleController;
import br.edu.ifce.ppd.util.MyColors;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Rectangle2D;

/**
 * Created by alcivanio on 13/04/17.
 */
public class GameArea {

    public GameCanvas       gameCanvas;
    public JPanel           gamePanel;
    public MiddleController middleController;



    public GameArea() {
        int posX    = 10;
        int posY    = 103;
        int width   = 301;
        int height  = 407;

        gamePanel = new JPanel();
        gamePanel.setBounds(posX, posY, width, height);
        gamePanel.setBackground(Color.white);
        gamePanel.setLayout(null);

        gameCanvas = new GameCanvas();
        gameCanvas.setBounds(0, 0, width, height);

        gamePanel.add(gameCanvas);
    }


    public void setClickEventsOnTable() {

        gameCanvas.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                middleController.onMouseClickOnCanvas(e);
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });
    }

}

