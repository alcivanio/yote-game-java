package br.edu.ifce.ppd.connection;

import java.io.*;

/**
 * Created by alcivanio on 17/04/17.
 */
public class GameProtocol implements Serializable{

    public CommunicationType   communicationType;
    public GameMovePosition    gMovePosition;
    public ChatMessage         chatMessage;


    public GameProtocol(CommunicationType cType, GameMovePosition gPos, ChatMessage cMes) {
        communicationType   = cType;
        gMovePosition       = gPos;
        chatMessage         = cMes;
    }

    public byte[] getThisBytesArray() {
        try {
            ByteArrayOutputStream outputArray   = new ByteArrayOutputStream(25000);
            BufferedOutputStream bOutputStream  = new BufferedOutputStream(outputArray);
            ObjectOutputStream objOutput        = new ObjectOutputStream(bOutputStream);

            objOutput.flush();
            objOutput.writeObject(this);
            objOutput.flush();
            byte[] sendBuffer = outputArray.toByteArray();
            return sendBuffer;
        }

        //if everything goes wrong.
        catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    static GameProtocol fromBytes(byte[] buffer) {
        try {
            int bytesSize                       = buffer.length;
            ByteArrayInputStream byteInput      = new ByteArrayInputStream(buffer);
            BufferedInputStream bufferedInput   = new BufferedInputStream(byteInput);
            ObjectInputStream objInput          = new ObjectInputStream(bufferedInput);
            GameProtocol receivedInfo           = (GameProtocol) objInput.readObject();

            objInput.close();
            return receivedInfo;
        }
        //printing the error. Ok, I just hope it never happens!
        catch(Exception e) { e.printStackTrace(); }

        return null;
    }
}