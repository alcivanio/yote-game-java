package br.edu.ifce.ppd.connection;

import br.edu.ifce.ppd.middle.MiddleController;

import java.io.*;
import java.net.*;

/**
 * Created by alcivanio on 17/04/17.
 */
public class GameConnection implements Runnable {
    private static final int SERVER_PORT = 3393;

    MiddleController middleController;

    ConnectionType  type;
    InetAddress     serverName;
    Socket          socket;
    ServerSocket    serverSocket;
    byte[]          bufferInfos;


    public GameConnection(ConnectionType type, MiddleController middleController) {
        this.type = type;
        switch (type) {
            case CLIENT:
                startClient();
            case SERVER:
                startServer();
        }
        this.middleController = middleController;
    }

    public void startClient() {
        initClientElements();
        initClientSocket();
        Thread serverThread = new Thread(this);
        serverThread.start();
    }


    public void startServer() {

        initServerElements();
        initServerSocket();
        Thread serverThread = new Thread(this);
        serverThread.start();
    }




    /*
    STEP 1:
    In the step 1 we're going to start some variables in the code, as some buffers, ip address and port id.
    */

    private void initClientElements() {
        //initializing the inet address.
        try {
            serverName = InetAddress.getByName("localhost");
        }
        catch(Exception e) {e.printStackTrace();}
        bufferInfos = new byte[5000];
    }

    private void initServerElements() {
        bufferInfos = new byte[5000];
    }




    /*
    STEP 2:
    Easy step, just to initialize the socket. its separated of the other part just because :)
    */
    private void initClientSocket() {
        try {
            socket = new Socket(serverName, SERVER_PORT);
        } catch(Exception e){}
    }

    private void initServerSocket() {
        try {
            serverSocket    = new ServerSocket(SERVER_PORT);
            socket          = serverSocket.accept();
        } catch(Exception e) {}
    }





    /*
    STEP 3:
    Here we're going to send OR receive a content from the server.
    */
    private void serverReceivePackets() {
        try {
            while(true) {
                System.out.println("SERVIDOR ESPERANDO");

                DataInputStream inputStream = new DataInputStream(socket.getInputStream());
                inputStream.read(bufferInfos);
                GameProtocol receivedInfo   = GameProtocol.fromBytes(bufferInfos);
                middleController.receivedProtocol(receivedInfo);
            }
        }
        catch(Exception e){e.printStackTrace();}
    }

    private void serverSendMessage() {

    }

    private void clientReceivePackets() {
        try {
            while (true) {
                System.out.println("CLIENTE ESPERANDO");
                DataInputStream input = new DataInputStream(socket.getInputStream());
                input.read(bufferInfos);
                GameProtocol receivedInfo = GameProtocol.fromBytes(bufferInfos);
                middleController.receivedProtocol(receivedInfo);

            }
        }
        catch (Exception e){e.printStackTrace();}
    }


    public void sendPackage(GameProtocol prot) {
        byte[] protBytes = prot.getThisBytesArray();

        try {
            DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());
            outputStream.write(protBytes);
        }catch(Exception e){
            System.out.println("Erro ao enviar pacote ao servidor.\n");
            e.printStackTrace();
        }
    }






    @Override
    public void run() {

        switch(type) {
            case SERVER:
                serverReceivePackets();
            case CLIENT:
                clientReceivePackets();
        }
    }
}


