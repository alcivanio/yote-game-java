package br.edu.ifce.ppd.connection.rmi;

import java.rmi.Naming;

/**
 * Created by alcivanio on 07/05/17.
 */
public class RMIRegister {
    public static void main(String args[]) {

        try {
            RMIServer server = new RMIServer();
            Naming.rebind("Server", server);
            System.out.println("Registered server.");
        } catch(Exception e) { e.printStackTrace(); }


    }

}
