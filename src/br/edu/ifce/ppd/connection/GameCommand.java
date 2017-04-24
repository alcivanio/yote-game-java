package br.edu.ifce.ppd.connection;

import java.io.Serializable;

/**
 * Created by alcivanio on 23/04/17.
 */
public class GameCommand implements Serializable {

    public GameCommandType commandCode;

    public GameCommand(GameCommandType type) {
        commandCode = type;
    }
}
