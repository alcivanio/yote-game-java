package br.edu.ifce.ppd.connection;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

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
}