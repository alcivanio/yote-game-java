package br.edu.ifce.ppd.gui;

import br.edu.ifce.ppd.connection.*;
import br.edu.ifce.ppd.connection.rmi.RMICenter;
import br.edu.ifce.ppd.connection.rmi.RMIRegister;
import br.edu.ifce.ppd.middle.MiddleController;
import br.edu.ifce.ppd.models.User;
import br.edu.ifce.ppd.util.MyColors;

import javax.swing.*;
import java.awt.*;
import java.net.InetAddress;

/**
 * Created by alcivanio on 12/04/17.
 */
public class GameGUI {

    ConnectionType userType;

    //component variables.
    JPanel      mainPanel;
    GameHeader  gameHeader;
    GameArea    gameArea;
    GameChat    gameChat;

    MiddleController middleController;
    GameGUI self = this;
    User user;

    //screen size
    static int screenSizeWidth     = 684;
    static int screenSizeHeight    = 542;




    public static void main(String[] args) {
        testesRMI();
        JFrame frame = new JFrame("App");
        frame.setContentPane(new GameGUI().mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(screenSizeWidth, screenSizeHeight);
        frame.setResizable(false);
        frame.setVisible(true);

        testesSocket();
        testesRMI();

    }

    /*
    * The system starts here. We do any change before to show to the user the interface using this method.
    */
    public GameGUI() {

        viewDidAppear();
    }

    public void viewDidAppear() {
        routineUserType();
        setGraphicalElements();
        setUserElements();
        setConnectionElements();
    }

    private void setGraphicalElements() {
        setMainPanel();
        setHeaderElements();
        setGameArea();
        setGameChatArea();
    }

    private void setConnectionElements() {
        middleController = new MiddleController(user, userType, gameHeader, gameArea, gameChat);
        middleController.setGeneralConfigurations();
    }

    private void setUserElements(){
        user = new User();
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

    static void testesSocket() {
        /*GameConnection server = new GameConnection(ConnectionType.SERVER);

        GameConnection client = new GameConnection(ConnectionType.CLIENT);

        ChatMessage mess        = new ChatMessage();
        mess.message            = "ESSA É A MENSAGEM DO CHAT...";
        GameProtocol prot       = new GameProtocol(CommunicationType.CHAT, null, mess);

        client.sendPackage(prot);
        //server.sendPackage(prot);*/
    }

    static void testesRMI() {
        System.out.println("ABRIU");
        RMICenter center1 = new RMICenter(0);
        RMICenter center2 = new RMICenter(1);

        center1.teste("Cliente 0");
        center2.teste("Cliente 1");

        int a = 1;
    }


    private void routineUserType() {

        final String PLATFORM_MESSAGE   = "Que tipo de plataforma você será nesse jogo?";
        final String PLATFORM_TITLE     = "Selecione tipo de plataforma";
        final String[] OPTIONS          = new String[] {"Servidor", "Cliente"};
        ConnectionType connectionType = null;

        while (connectionType == null) {
            int response = JOptionPane.showOptionDialog(null,
                    PLATFORM_MESSAGE,
                    PLATFORM_TITLE,
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    OPTIONS,
                    OPTIONS[0]);
            if(response != -1) {
                connectionType = response == 0 ? ConnectionType.SERVER : ConnectionType.CLIENT;
            }
        }

        userType = connectionType;
        if (connectionType == ConnectionType.CLIENT) {
            routineUserIPAddress();
        }
    }

    private void routineUserIPAddress() {

        String stringIPAddress = null;

        while(stringIPAddress == null) {
            stringIPAddress = JOptionPane.showInputDialog("Insira o IP do servidor.\nDeixe o campo em branco para definir como localhost.\nExemplo: 127.0.0.1");
            stringIPAddress = stringIPAddress == "" ? "localhost" : stringIPAddress;
            try {
                InetAddress serverName = InetAddress.getByName(stringIPAddress);
                int a = 0;
            }
            catch(Exception e){
                stringIPAddress = null;
                e.printStackTrace();
            }
        }
    }




}

