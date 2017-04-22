package br.edu.ifce.ppd.connection;

import br.edu.ifce.ppd.models.User;

import java.io.Serializable;

/**
 * Created by alcivanio on 17/04/17.
 */
public class ChatMessage implements Serializable{
    public String message;
    public User user;
}
