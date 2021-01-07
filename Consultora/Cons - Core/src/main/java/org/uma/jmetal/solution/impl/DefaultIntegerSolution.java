package org.uma.jmetal.solution.impl;

import org.uma.jmetal.problem.IntegerProblem;
import org.uma.jmetal.solution.IntegerSolution;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Defines an implementation of an integer solution
 *
 * @author Antonio J. Nebro <antonio@lcc.uma.es>
 */
@SuppressWarnings("serial")
public class DefaultIntegerSolution
    extends AbstractGenericSolution<Integer, IntegerProblem>
    implements IntegerSolution {
	
	/*
	 * private BigInteger[] encryptedEvaluations; private List<BigInteger>
	 * encryptedVariables;
	 */

  /** Constructor */
  public DefaultIntegerSolution(IntegerProblem problem) {
    super(problem) ;

    initializeIntegerVariables();
    initializeObjectiveValues();
	/*
	 * encryptedEvaluations = new BigInteger[problem.getNumberOfObjectives()] ;
	 * encryptedVariables = new ArrayList<>(problem.getNumberOfVariables()) ;
	 * 
	 * for (int i = 0; i < problem.getNumberOfVariables(); i++) {
	 * encryptedVariables.add(i, null) ; }
	 */
    
  }

  /** Copy constructor */
  public DefaultIntegerSolution(DefaultIntegerSolution solution) {
    super(solution.problem) ;

    for (int i = 0; i < problem.getNumberOfVariables(); i++) {
      setVariableValue(i, solution.getVariableValue(i));
    }

    for (int i = 0; i < problem.getNumberOfObjectives(); i++) {
      setObjective(i, solution.getObjective(i)) ;
    }

    attributes = new HashMap<Object, Object>(solution.attributes) ;
  }

  @Override
  public Integer getUpperBound(int index) {
    return problem.getUpperBound(index);
  }

  @Override
  public Integer getLowerBound(int index) {
    return problem.getLowerBound(index) ;
  }

  @Override
  public DefaultIntegerSolution copy() {
    return new DefaultIntegerSolution(this);
  }

  @Override
  public String getVariableValueString(int index) {
    return getVariableValue(index).toString() ;
  }
  
  private void initializeIntegerVariables() {
    for (int i = 0 ; i < problem.getNumberOfVariables(); i++) {
      Integer value = randomGenerator.nextInt(getLowerBound(i), getUpperBound(i));
      setVariableValue(i, value) ;
    }
  }
	
	/*
	 * @Override public BigInteger getEncryptedVariableValue(int index) { return
	 * encryptedVariables.get(index); }
	 * 
	 * @Override public void setEncryptedVariableValue(int index, BigInteger value)
	 * { encryptedVariables.set(index, value); }
	 * 
	 * @Override public String getEncryptedVariableValueString(int index) { return
	 * encryptedVariables.get(index).toString(); }
	 * 
	 * @Override public void setEncryptedEvaluation(int index, BigInteger value) {
	 * encryptedEvaluations[index] = value; }
	 * 
	 * @Override public BigInteger getEncryptedEvaluation(int index) { return
	 * encryptedEvaluations[index]; }
	 * 
	 * @Override public BigInteger[] getEncryptedEvaluations() { return
	 * encryptedEvaluations; }
	 */
}
