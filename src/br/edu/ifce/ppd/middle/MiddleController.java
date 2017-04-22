package br.edu.ifce.ppd.middle;

import br.edu.ifce.ppd.connection.*;
import br.edu.ifce.ppd.gui.GameArea;
import br.edu.ifce.ppd.gui.GameChat;
import br.edu.ifce.ppd.gui.GameGUI;
import br.edu.ifce.ppd.gui.GameHeader;
import br.edu.ifce.ppd.models.GameChatElement;
import br.edu.ifce.ppd.models.User;

import java.awt.*;
import java.awt.event.*;

/**
 * Created by alcivanio on 19/04/17.
 */
public class MiddleController {

    User            user;
    ConnectionType  connectionType;
    GameHeader      gameHeader;
    GameArea        gameArea;
    GameChat        gameChat;
    GameConnection  gameConnection;



    public MiddleController(User            user,
                            ConnectionType  connectionType,
                            GameHeader      gameHeader,
                            GameArea        gameArea,
                            GameChat        gameChat) {

        this.user           = user;
        this.connectionType = connectionType;
        this.gameHeader     = gameHeader;
        this.gameArea       = gameArea;
        this.gameChat       = gameChat;
        //this.gameConnection = new GameConnection(connectionType, this);
    }

    public void setGeneralConfigurations() {
        addSendMessageListener();
        setClickEventsOnTable();
    }


    /*
        - Send message methods.
    * */
    private void addSendMessageListener() {
        gameChat.sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                trySendMessage();
            }
        });
    }

    private void trySendMessage() {
        String message          = gameChat.sendField.getText();
        ChatMessage mess        = new ChatMessage();
        mess.message            = message;
        mess.user               = user;
        GameProtocol prot       = new GameProtocol(CommunicationType.CHAT, null, mess);

        gameConnection.sendPackage(prot);
        didReceiveMessage(prot);

        gameChat.sendField.setText(gameChat.SEND_FIELD_PLACEHOLDER);
    }



    public void receivedProtocol(GameProtocol protocol) {
        switch (protocol.communicationType) {
            case CHAT:
                didReceiveMessage(protocol);
            case MOVE:
                System.out.println("MOVIMENTO");
        }
    }

    private void didReceiveMessage(GameProtocol protocol) {

        boolean isFromCurrentUser   = protocol.chatMessage.user.identifier == user.identifier;
        String userMessageId        = protocol.chatMessage.user.identifier;
        String messageText          = protocol.chatMessage.message;
        final GameChatElement chatElement = new GameChatElement(userMessageId, messageText);
        chatElement.fromCurrentUser = isFromCurrentUser;

        //MAIN THREAD
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                gameChat.addNewMessage(chatElement);
            }
        });
    }



    /*
        - change position method.
    */

    public void setClickEventsOnTable() {
        gameArea.gameCanvas.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                onMouseClickOnCanvas(e);
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
            public void mouseExited(MouseEvent e) {}
        });

    }

    public void onMouseClickOnCanvas(MouseEvent e) {
        GameMovePosition pos = gameArea.gameCanvas.getPositionBasedOnClick(e);

        //posso estar clicando lá pq quero mudar a minha peça de lugar.

        //posso estar clicando pois quero adicionar uma peça no lugar.



        gameArea.gameCanvas.gamePositions[pos.posX][pos.posY] = 1;
        gameArea.gameCanvas.repaint();
    }

    public String checkForErrosInChoosePlace(GameMovePosition pos) {
        int [][] position = gameArea.gameCanvas.gamePositions;
        String errorMessage = null;

        if (position[pos.posX][pos.posY] != 0) {
            errorMessage = "Já existe uma peça ocupando este lugar";
        }


        return errorMessage;

    }








}
