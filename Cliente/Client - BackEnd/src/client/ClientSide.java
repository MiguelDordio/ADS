package client;

import infoexchange.ExchangedSolution;
import security.DGK.DGKOperations;
import security.DGK.DGKPublicKey;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ClientSide {

    private static ObjectOutputStream outToServer;

    public static void main(String[] args) {

        try {
            Socket socket = new Socket("localhost", 1342);

            // Create the input & output streams to the server
            outToServer = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream inFromServer = new ObjectInputStream(socket.getInputStream());
            while(true){
                ExchangedSolution s = (ExchangedSolution) inFromServer.readObject();

                if(s != null)
                    evaluate(s);
                System.out.println("Solution received and evaluated");
            }

        }catch(Exception e) {
            System.err.println("Socket Error: " + e.getMessage());
            System.err.println("Localized: " + e.getLocalizedMessage());
            System.err.println("Stack Trace: " + Arrays.toString(e.getStackTrace()));
            System.err.println("To String: " + e.toString());
        }
    }

    private static void evaluate(ExchangedSolution solutionInformation) {
        DGKPublicKey pk = solutionInformation.getPk();
        List<BigInteger> encryptedVariables = solutionInformation.getEncryptedVariables();

        BigInteger[] fxEn = new BigInteger[2];
        for (int i = 0; i < 2; i++) {
            fxEn[i] = DGKOperations.encrypt(pk, new BigInteger("0"));
        }

        //Evaluate
        for (BigInteger encryptedVariable : encryptedVariables) {
            //encrypted part
            BigInteger aux = DGKOperations.add(pk, encryptedVariable, encryptedVariable);
            fxEn[0] = DGKOperations.add(pk, aux, fxEn[0]);


        }

        for (BigInteger aux : encryptedVariables) {
            //fxEn[1] = DGKOperations.add(pk, solution.getEncryptedVariableValue(var), solution.getEncryptedVariableValue(var));
            fxEn[1] = DGKOperations.multiply(pk, aux, 10);
        }


        encryptedVariables.clear();
        //Save the results
        encryptedVariables = new ArrayList<>(Arrays.asList(fxEn));
        solutionInformation.setEncryptedVariables(encryptedVariables);

        try {
            outToServer.writeObject(solutionInformation);
            System.out.println("Evaluation sent to the Server");
            //outToServer.flush();
        } catch (IOException e) {
            System.out.println("Error sending the solutionback to the server");
            e.printStackTrace();
        }
    }
}
