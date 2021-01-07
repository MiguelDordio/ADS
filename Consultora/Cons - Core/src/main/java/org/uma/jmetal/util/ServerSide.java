package org.uma.jmetal.util;

import security.DGK.DGKKeyPairGenerator;
import security.DGK.DGKPrivateKey;
import security.DGK.DGKPublicKey;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.KeyPair;
import java.security.SecureRandom;

public class ServerSide {

    public static DGKPublicKey pk;
    public static DGKPrivateKey sk;

    public static ObjectOutputStream outToClient;
    public static ObjectInputStream inFromClient;

    public ServerSide(){
        int keySize = 1024;
        SecureRandom sr = new SecureRandom();
        DGKKeyPairGenerator p = new DGKKeyPairGenerator(16, 56, keySize);
        p.initialize(keySize, sr);
        KeyPair pe = p.generateKeyPair();
        pk = (DGKPublicKey) pe.getPublic();
        sk = (DGKPrivateKey) pe.getPrivate();

        try {
            ServerSocket serverSocket = new ServerSocket(1342);
            System.out.println("----- Servidor Consultora Iniciado -----");
            Socket socket = serverSocket.accept();


            // Create input and output streams to client
            outToClient = new ObjectOutputStream(socket.getOutputStream());
            inFromClient = new ObjectInputStream(socket.getInputStream());

            // solution has been generated, turn off the communication
            System.out.println("----- Servidor Consultora Desligado -----");
            serverSocket.close();


        }catch (Exception e) {
            System.err.println("Server Error: " + e.getMessage());
            System.err.println("Localized: " + e.getLocalizedMessage());
            System.err.println("Stack Trace: " + e.getStackTrace());
            System.err.println("To String: " + e.toString());
        }
    }
}
