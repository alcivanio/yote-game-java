package br.edu.ifce.ppd.models;

import java.io.Serializable;
import java.util.Random;

/**
 * Created by alcivanio on 21/04/17.
 */
public class User implements Serializable{
    public String name;
    public String identifier;

    public User() {
        generateIdentifier();
    }

    private void generateIdentifier(){
        Random rand = new Random();
        identifier  = rand.nextInt(100) + rand.nextInt(300) + "";//it can fail!
    }


}


