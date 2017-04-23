package br.edu.ifce.ppd.middle;

import br.edu.ifce.ppd.connection.*;
import br.edu.ifce.ppd.gui.GameArea;
import br.edu.ifce.ppd.gui.GameChat;
import br.edu.ifce.ppd.gui.GameGUI;
import br.edu.ifce.ppd.gui.GameHeader;
import br.edu.ifce.ppd.models.GameChatElement;
import br.edu.ifce.ppd.models.User;

import javax.swing.*;
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

    //aux
    MouseStateMiddle mouseState;
    GameMovePosition lastClickedPos;


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
        this.mouseState     = MouseStateMiddle.FREE;
        this.lastClickedPos = new GameMovePosition(0,0);
        this.gameConnection = new GameConnection(connectionType, this);
    }

    public void setGeneralConfigurations() {
        addSendMessageListener();
        setClickEventsOnTable();
        setClickCircleTurn();
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
                didReceiveMove(protocol);
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

    private void didReceiveMove(final GameProtocol protocol) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                gameArea.gameCanvas.gamePositions = protocol.gameTableState.table;
                gameArea.gameCanvas.repaint();
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

    public void setClickCircleTurn() {
        gameHeader.turnColor.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                setChoosedToAddNewPiece();
            }
            @Override
            public void mousePressed(MouseEvent e) {}
            @Override
            public void mouseReleased(MouseEvent e) {}
            @Override
            public void mouseEntered(MouseEvent e) {}
            @Override
            public void mouseExited(MouseEvent e) {}
        });
    }

    public void setChoosedToAddNewPiece() {
        mouseState = MouseStateMiddle.CHOOSED_TO_ADD;
    }

    public void onMouseClickOnCanvas(MouseEvent e) {
        GameMovePosition pos = gameArea.gameCanvas.getPositionBasedOnClick(e);
        boolean jumpChoosedToMove = false;

        //escrevendo o caso em que o usuário quer mudar de lugar
        if(mouseState == MouseStateMiddle.FREE) {

            //this is the successfull case, when the user selects a piece to change place.
            if(gameArea.gameCanvas.gamePositions[pos.posX][pos.posY] == 1) {
                mouseState = MouseStateMiddle.CHOOSED_TO_MOVE;
                jumpChoosedToMove = true;
                //gameArea.gameCanvas.gamePositions[pos.posX][pos.posY] = 3;
            }

            if(gameArea.gameCanvas.gamePositions[pos.posX][pos.posY] == 2) {
                //trying to do things with the opponent piece.
                showMessage("Você deve escolher uma peça sua. Escolha as azuis :)");
                return;
            }

            if(gameArea.gameCanvas.gamePositions[pos.posX][pos.posY] == 0) {
                //its needed to choose an action type, as add a piece, for example.
                showMessage("Você deve escolher um tipo de ação. \nSe deseja adicionar uma peça" +
                        ", por favor, clique no botão azul no canto superir esquerdo.");
                return;
            }
        }

        //writing the case when the user is ending the action of change piece location
        if (mouseState == MouseStateMiddle.CHOOSED_TO_MOVE && !jumpChoosedToMove) {
//showMessage(gameArea.gameCanvas.gamePositions[pos.posX][pos.posY] + "");
            if(gameArea.gameCanvas.gamePositions[pos.posX][pos.posY] == 0) {
                if (!checkForValidMovement(pos)) { return; }//if its not a valid movement...

                gameArea.gameCanvas.gamePositions[pos.posX][pos.posY] = 1;
                gameArea.gameCanvas.gamePositions[lastClickedPos.posX][lastClickedPos.posY] = 0;
                sendTableStateToOtherUser();
                mouseState = MouseStateMiddle.FREE;
            }

            else {
                showMessage("Escolha um local vazio para mover a peça");
                return;
            }
        }


        if (mouseState == MouseStateMiddle.CHOOSED_TO_ADD) {
            if(gameArea.gameCanvas.gamePositions[pos.posX][pos.posY] == 0) {
                gameArea.gameCanvas.gamePositions[pos.posX][pos.posY] = 1;
                sendTableStateToOtherUser();
                mouseState = MouseStateMiddle.FREE;
            }
            else {
                showMessage("Escolha um local vazio para adicionar a peça");
            }
        }

        //gameArea.gameCanvas.gamePositions[pos.posX][pos.posY] = 1;
        gameArea.gameCanvas.repaint();

        //updating the last clicked space
        lastClickedPos.posX = pos.posX;
        lastClickedPos.posY = pos.posY;
    }

    public String checkForErrosInChoosePlace(GameMovePosition pos) {
        int [][] position = gameArea.gameCanvas.gamePositions;
        String errorMessage = null;

        if (position[pos.posX][pos.posY] != 0) {
            errorMessage = "Já existe uma peça ocupando este lugar";
        }

        return errorMessage;
    }

    public boolean checkForValidMovement(GameMovePosition np) {
        if(lastClickedPos.isSimpleMove(np)) {return true;}//we checked previously

        //if this guy isnt a simple move, we have to call a method to save a removement of other user piece.
        else {
            if (lastClickedPos.isDoubleMove(np) && lastClickedPos.isAllowedDoubleMove(np, gameArea.gameCanvas.gamePositions)) {
                showMessage("Deu tudo certo...");
                return true;
            }
            else {
                showMessage("Você escolheu uma posição inválida :(");
                return false;
            }

        }
    }

    public void sendTableStateToOtherUser() {
        GameTableState tableState   = new GameTableState(gameArea.gameCanvas.gamePositions);
        GameProtocol prot           = new GameProtocol(CommunicationType.MOVE, tableState, null);

        gameConnection.sendPackage(prot);
    }

    public void checkIfItsGettingOtherUserPiece(GameMovePosition newPos) {

    }




    public void showMessage(String message){
        JOptionPane.showMessageDialog(null, message, "Alerta",JOptionPane.INFORMATION_MESSAGE);
        System.out.println(message);
    }








}
