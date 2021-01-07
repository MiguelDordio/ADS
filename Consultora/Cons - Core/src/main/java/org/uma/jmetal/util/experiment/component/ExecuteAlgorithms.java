package org.uma.jmetal.util.experiment.component;

import org.uma.jmetal.solution.Solution;
import org.uma.jmetal.solution.util.AlgorithmResult;
import org.uma.jmetal.util.JMetalException;
import org.uma.jmetal.util.JMetalLogger;
import org.uma.jmetal.util.ServerSide;
import org.uma.jmetal.util.experiment.Experiment;
import org.uma.jmetal.util.experiment.ExperimentComponent;
import security.DGK.DGKKeyPairGenerator;
import security.DGK.DGKPrivateKey;
import security.DGK.DGKPublicKey;

import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.KeyPair;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

/**
 * This class executes the algorithms the have been configured with a instance of class
 * {@link Experiment}. Java 8 parallel streams are used to run the algorithms in parallel.
 * <p>
 * The result of the execution is a pair of files FUNrunId.tsv and VARrunID.tsv per experiment,
 * which are stored in the directory
 * {@link Experiment #getExperimentBaseDirectory()}/algorithmName/problemName.
 *
 * @author Antonio J. Nebro <antonio@lcc.uma.es>
 */
public class ExecuteAlgorithms<S extends Solution<?>, Result> implements ExperimentComponent {
  private Experiment<S, Result> experiment;

    public static DGKPublicKey pk;
    public static DGKPrivateKey sk;

    public static ObjectOutputStream outToClient;
    public static ObjectInputStream inFromClient;

  /**
   * Constructor
   */
  public ExecuteAlgorithms(Experiment<S, Result> configuration) {
    this.experiment = configuration;
  }

  @Override
  public void run() {
    JMetalLogger.logger.info("ExecuteAlgorithms: Preparing output directory");
    prepareOutputDirectory();

    System.setProperty("java.util.concurrent.ForkJoinPool.common.parallelism",
            "" + this.experiment.getNumberOfCores());

    experiment.getAlgorithmList()
            .parallelStream()
            .forEach(algorithm -> {
            	algorithm.runAlgorithm(experiment);
            });
  }


  private void prepareOutputDirectory() {
    if (experimentDirectoryDoesNotExist()) {
      createExperimentDirectory();
    }
  }

  private boolean experimentDirectoryDoesNotExist() {
    boolean result;
    File experimentDirectory;

    experimentDirectory = new File(experiment.getExperimentBaseDirectory());
    if (experimentDirectory.exists() && experimentDirectory.isDirectory()) {
      result = false;
    } else {
      result = true;
    }

    return result;
  }

  private void createExperimentDirectory() {
    File experimentDirectory;
    experimentDirectory = new File(experiment.getExperimentBaseDirectory());

    if (experimentDirectory.exists()) {
      experimentDirectory.delete();
    }

    boolean result;
    result = new File(experiment.getExperimentBaseDirectory()).mkdirs();
    if (!result) {
      throw new JMetalException("Error creating experiment directory: " +
              experiment.getExperimentBaseDirectory());
    }
  }

	@Override
	public AlgorithmResult runWithResults() {
	    //JMetalLogger.logger.info("ExecuteAlgorithms: Preparing output directory");
	    prepareOutputDirectory();

	    System.setProperty("java.util.concurrent.ForkJoinPool.common.parallelism",
	            "" + this.experiment.getNumberOfCores());

	    return runWithSocket();
	}

    public AlgorithmResult runWithSocket(){
        final AlgorithmResult[] results = {null};

        // Generate the public key for encryption
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

            experiment.getAlgorithmList()
                    .parallelStream()
                    .forEach(algorithm -> results[0] = algorithm.runAlgorithm(experiment));

            // solution has been generated, turn off the communication
            System.out.println("----- Servidor Consultora Desligado -----");
            serverSocket.close();


        }catch (Exception e) {
            System.err.println("Server Error: " + e.getMessage());
            System.err.println("Localized: " + e.getLocalizedMessage());
            System.err.println("Stack Trace: " + e.getStackTrace());
            System.err.println("To String: " + e.toString());
        }

        return results[0];
    }
}
