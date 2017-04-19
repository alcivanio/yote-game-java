package br.edu.ifce.ppd.connection;

import java.io.*;
import java.net.*;

/**
 * Created by alcivanio on 17/04/17.
 */
public class GameConnection implements Runnable {

    int serverPort = 3393;
    InetAddress serverName;
    DatagramSocket socket;

    //these are some server tools. We'll need just one instance of these things, so its cheapear to create it outside a method.
    byte[] serverBuffer;
    DatagramPacket serverPacket;



    public void startClient() {
        initClientElements();
        initClientSocket();
    }


    public void startServer() {

        /*try {
            ServerSocket sSocket = new ServerSocket(serverPort);
            while (true) {
                Socket socket                   = sSocket.accept();
                InputStreamReader inputStream   = new InputStreamReader(socket.getInputStream());
                BufferedReader bufferClient     = new BufferedReader(inputStream);
            }
        }

        catch(Exception e){}*/

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
    }

    private void initServerElements() {
        serverBuffer = new byte[5000];
        serverPacket = new DatagramPacket(serverBuffer, serverBuffer.length);
    }




    /*
    STEP 2:
    Easy step, just to initialize the socket. its separated of the other part just because :)
    */
    private void initClientSocket() {
        try {
            socket = new DatagramSocket();
        } catch(Exception e){}
    }

    private void initServerSocket() {
        try {
            socket = new DatagramSocket(serverPort);
        } catch(Exception e) {}
    }





    /*
    STEP 3:
    Here we're going to send OR receive a content from the server.
    */
    private void receivePackets() {
        try {
            while(true) {
                System.out.println("Fecha recebimento");
                socket.receive(serverPacket);

                System.out.println("Fecha recebimento");
                int bytesSize = serverPacket.getLength();
                ByteArrayInputStream byteInput = new ByteArrayInputStream(serverBuffer);
                BufferedInputStream bufferedInput = new BufferedInputStream(byteInput);
                ObjectInputStream objInput = new ObjectInputStream(bufferedInput);
                GameProtocol prot = (GameProtocol) objInput.readObject();
                objInput.close();
            }
        }catch(Exception e){}

    }

    public void pr(){
        System.out.print("ENVIOU");
    }


    public void sendPackage(GameProtocol prot) {

        //getting the object bytes
        byte[] protBytes = prot.getThisBytesArray();

        //and then we just create the socket and send it!
        DatagramPacket packet = new DatagramPacket(protBytes, protBytes.length, serverName, serverPort);

        /* We'll try to send the packet to the server, and if it fails we show a message.
        But don't worry, everything will be ok. */
        try {
            socket.send(packet);
        }catch(Exception e){
            System.out.println("Erro ao enviar pacote ao servidor.\n");
            e.printStackTrace();
        }

    }






    @Override
    public void run() {
        receivePackets();
    }
}



















