package br.edu.ifce.ppd.gui;

import br.edu.ifce.ppd.util.MyColors;

import javax.swing.*;
import java.awt.*;

/**
 * Created by alcivanio on 12/04/17.
 */
public class GameGUI {

    //component variables.
    JPanel      mainPanel;
    GameHeader  gameHeader;
    GameArea    gameArea;
    GameChat    gameChat;

    GameGUI self = this;

    //screen size
    static int screenSizeWidth     = 684;
    static int screenSizeHeight    = 542;




    public static void main(String[] args) {

        JFrame frame = new JFrame("App");
        frame.setContentPane(new GameGUI().mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(screenSizeWidth, screenSizeHeight);
        frame.setResizable(false);
        frame.setVisible(true);

    }

    /*
    * The system starts here. We do any change before to show to the user the interface using this method.
    */
    public GameGUI() {

        viewDidAppear();
    }

    public void viewDidAppear() {
        setGraphicalElements();
    }

    private void setGraphicalElements() {
        setMainPanel();
        setHeaderElements();
        setGameArea();
        setGameChatArea();
    }

    //this aux method will help us to confiture stuff as layout and its elements (of the jpanel).
    private void setMainPanel() {

        mainPanel = new JPanel();
        mainPanel.setLayout(null);//it will let us to be free to put elements in any position!
        mainPanel.setBounds(0, 0, screenSizeWidth, screenSizeHeight);
        mainPanel.setBackground(Color.white);
    }


    private void setHeaderElements() {
        gameHeader = new GameHeader();
        mainPanel.add(gameHeader.headerPanel);
    }

    private void setGameArea() {
        gameArea = new GameArea();
        mainPanel.add(gameArea.gamePanel);
    }

    private void setGameChatArea() {
        gameChat = new GameChat();
        mainPanel.add(gameChat.chatPanel);
    }



}

