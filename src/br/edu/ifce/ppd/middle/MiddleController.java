package br.edu.ifce.ppd.middle;

import br.edu.ifce.ppd.connection.*;
import br.edu.ifce.ppd.gui.GameArea;
import br.edu.ifce.ppd.gui.GameChat;
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
    MouseStateMiddle    mouseState;
    GameMovePosition    lastClickedPos;
    boolean             isMyTurn = false;


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
        this.isMyTurn       = connectionType == ConnectionType.SERVER;
    }

    public void setGeneralConfigurations() {
        addSendMessageListener();
        setClickEventsOnTable();
        setClickCircleTurn();
        addCommandsListener();
        uiUpdate();
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
        GameProtocol prot       = new GameProtocol(CommunicationType.CHAT, null, mess, null);

        gameConnection.sendPackage(prot);
        didReceiveMessage(prot);

        gameChat.sendField.setText(gameChat.SEND_FIELD_PLACEHOLDER);
    }



    public void receivedProtocol(GameProtocol protocol) {
        switch (protocol.communicationType) {
            case CHAT:
                didReceiveMessage(protocol);
                break;
            case MOVE:
                didReceiveMove(protocol);
                break;
            case COMMAND:
                didReceiveCommand(protocol);
                break;
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
        isMyTurn = !isMyTurn;
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                gameArea.gameCanvas.gamePositions = gameArea.gameCanvas.opposeArray(protocol.gameTableState.table);
                gameArea.gameCanvas.repaint();
                uiUpdate();
            }
        });
    }

    private void didReceiveCommand(GameProtocol prot) {
        switch (prot.gameCommand.commandCode) {
            case LOSE_GAME:
                showMessage("lose game");
                break;
            case ASK_RESTART:
                askedToRestartGame();
                break;
            case DENY_RESTART:
                restartDenied();
                break;
            case ACCEPT_RESTART:
                restartAccepted();
                break;
        }
    }


    /*
        An area to commands receiveds.
    */

    private void askedToRestartGame() {
        System.out.println("CHAMEI");

        final String PLATFORM_MESSAGE   = "Oponente pediu pra reiniciar o jogo. Você aceita?";
        final String PLATFORM_TITLE     = "Pedido para reiniciar";
        final String[] OPTIONS          = new String[] {"Reiniciar", "Não"};
        int acceptRestart = -1;

        while (acceptRestart == -1) {
            int response = JOptionPane.showOptionDialog(null,
                    PLATFORM_MESSAGE, PLATFORM_TITLE,
                    JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null,
                    OPTIONS, OPTIONS[0]);

            acceptRestart = response;
        }
        GameCommand gameCommand = new GameCommand(acceptRestart == 0 ? GameCommandType.ACCEPT_RESTART : GameCommandType.DENY_RESTART);
        GameProtocol prot = new GameProtocol(CommunicationType.COMMAND,null,null, gameCommand);
        gameConnection.sendPackage(prot);

        if(gameCommand.commandCode == GameCommandType.ACCEPT_RESTART) {restartGame();}
    }

    private void restartAccepted() {
        isMyTurn = true;
        showMessage("O oponente aceitou seu pedido de reiniciar jogo");
        restartGame();
    }

    private void restartDenied() {
        isMyTurn = true;
        showMessage("O oponente não aceitou seu pedido de reiniciar jogo");
    }

    private void didLoseGame() {
        showMessage("VOCÊ PERDEU O JOGO :/ \nMELHORE!!!!");
        restartGame();
    }

    private void didWinGame() {
        showMessage("VOCÊ GANHOU O JOGO :/ \nTOP D+!!!!");
        restartGame();
    }

    private void restartGame() {
        gameArea.gameCanvas.startCleanArray();//restart the array positions.
        gameArea.myState.restart();
        gameArea.gameCanvas.repaint();
        isMyTurn = connectionType == ConnectionType.SERVER;
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
        if(!checkCanChangeAndAlert()) { return; }
        mouseState = MouseStateMiddle.CHOOSED_TO_ADD;
    }

    public void onMouseClickOnCanvas(MouseEvent e) {
        if(!checkCanChangeAndAlert()) { return; }

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
                gameArea.addPieceInTable(pos.posX, pos.posY);
                //gameArea.gameCanvas.gamePositions[pos.posX][pos.posY] = 1;
                //gameArea.myState.piecesInBucket--;

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

                GameMovePosition removedPos = lastClickedPos.getMiddlePointForDoubleMove(np);
                gameArea.getOpponentPiece(removedPos.posX, removedPos.posY);
                return true;
            }
            else {
                showMessage("Você escolheu uma posição inválida :(");
                return false;
            }

        }
    }

    public void sendTableStateToOtherUser() {
        isMyTurn = !isMyTurn;
        GameTableState tableState   = new GameTableState(gameArea.gameCanvas.gamePositions);
        tableState.myself   = gameArea.myState;
        tableState.opponent = gameArea.opponentState;

        GameProtocol prot   = new GameProtocol(CommunicationType.MOVE, tableState, null, null);
        gameConnection.sendPackage(prot);

        uiUpdate();
    }

    public void checkIfItsGettingOtherUserPiece(GameMovePosition newPos) {

    }




    public void showMessage(String message){
        JOptionPane.showMessageDialog(null, message, "Alerta",JOptionPane.INFORMATION_MESSAGE);
        System.out.println(message);
    }

    private boolean checkCanChangeAndAlert() {
        if(!isMyTurn) { showMessage("Não é a sua vez!"); }
        return isMyTurn;
    }






    /*
        Updates for the screen - some methods to show user infos
    */

    public void uiUpdate() {
        uiuIsMyTurn();
        uiuUsedPieces();
        uiuCaptured();
    }

    private void uiuIsMyTurn() {
        gameHeader.turnLabel.setText(isMyTurn ? "sua vez" : "vez do amigo");
    }
    private void uiuUsedPieces() {
        int useds = 12 - gameArea.myState.piecesInBucket;
        gameHeader.usedLabel.setText("" + useds);
    }
    private void uiuCaptured() {
        gameHeader.capturedsLabel.setText("" + gameArea.myState.tookFromOpponent);
    }




    /*
        Commands listener...
    */
    private void addCommandsListener() {
        gameHeader.restartLabel.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                sendRestartRequest();
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



    private void sendRestartRequest() {
        isMyTurn                = false;
        GameCommand gCommand    = new GameCommand(GameCommandType.ASK_RESTART);
        GameProtocol prot       = new GameProtocol(CommunicationType.COMMAND, null, null, gCommand);

        gameConnection.sendPackage(prot);
    }






}
