package org.uma.jmetal.util.evaluator.impl;

import infoexchange.ExchangedSolution;
import org.uma.jmetal.problem.ConstrainedProblem;
import org.uma.jmetal.problem.Problem;
import org.uma.jmetal.solution.impl.DefaultIntegerSolution;
import org.uma.jmetal.util.JMetalException;
import org.uma.jmetal.util.evaluator.SolutionListEvaluator;

import org.uma.jmetal.util.experiment.component.ExecuteAlgorithms;
import security.DGK.DGKOperations;

import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;


/**
 * @author Antonio J. Nebro
 */
@SuppressWarnings("serial")
public class SequentialSolutionListEvaluator<S> implements SolutionListEvaluator<S> {
@Override
  public List<S> evaluate(List<S> solutionList, Problem<S> problem) throws JMetalException {
      if (problem instanceof ConstrainedProblem) {
        solutionList.stream().forEach(s -> {
          problem.evaluate(s);
          ((ConstrainedProblem<S>) problem).evaluateConstraints(s);
        });
      } else {

    	  solutionList.stream().forEach(s -> {
    		  // encrypt variables before sending them to the client
    		  List<BigInteger> encryptedVariables = new ArrayList<BigInteger>();
    		  for (int i = 0; i < ((DefaultIntegerSolution) s).getNumberOfVariables() ; i++) {
    			  BigInteger val = new BigInteger(String.valueOf(((DefaultIntegerSolution) s).getVariableValue(i)));
    	          //((DefaultIntegerSolution) s).setVariableValue(i, null);
    	          encryptedVariables.add(DGKOperations.encrypt(ExecuteAlgorithms.pk, val));
    	          //((DefaultIntegerSolution) s).setEncryptedVariableValue(i, DGKOperations.encrypt(ExecuteAlgorithms.pk, val));
  			  }

          	//problem.evaluate(s);
          	try {
          		// Send the solution to the client
				ExchangedSolution solutionToSend = new ExchangedSolution(ExecuteAlgorithms.pk, encryptedVariables);
          		ExecuteAlgorithms.outToClient.writeObject(solutionToSend);
          		System.out.println("Sending the solution to the client");
          		//ExecuteAlgorithms.outToClient.flush();

				//ExecuteAlgorithms.outToClient.writeObject(jsonSolution);

				// Receive the solution from the client
          		ExchangedSolution newS = (ExchangedSolution) ExecuteAlgorithms.inFromClient.readObject();
          		for (int i = 0; i < newS.getEncryptedVariables().size() ; i++) {
	          		int decryptedNormalEvaluation = DGKOperations.decrypt(newS.getEncryptedVariables().get(i), ExecuteAlgorithms.sk).intValue();
	          		((DefaultIntegerSolution) s).setObjective(i, decryptedNormalEvaluation);
          		}


			} catch (IOException e) {
				System.out.println("Error sending the solution to the client");
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}




          });
      }

    return solutionList;
  }

  @Override public void shutdown() {
	  // This method is an intentionally-blank override.
  }
}
