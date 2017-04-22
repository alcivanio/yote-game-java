package br.edu.ifce.ppd.models;

import javax.swing.*;

/**
 * Created by alcivanio on 21/04/17.
 */
public class GameChatElement {

    public boolean fromCurrentUser;
    public String userID;
    public String  text;
    public JPanel elementPanel;
    public JTextArea textLabel;

    public GameChatElement(String userID, String text) {
        this.userID = userID;
        this.text   = text;
    }

    private void createElement() {

    }
}