package org.uma.jmetal.solution;

import java.io.Serializable;
import java.math.BigInteger;

/**
 * Interface representing a Solution
 *
 * @author Antonio J. Nebro <antonio@lcc.uma.es>
 * @param <T> Type (Double, Integer, etc.)
 */
public interface Solution<T> extends Serializable {
	
	// normal evaluations
    void setObjective(int index, double value) ;
    double getObjective(int index) ;
    double[] getObjectives() ;

    //normal variables
    T getVariableValue(int index) ;
    void setVariableValue(int index, T value) ;
    String getVariableValueString(int index) ;
 
    int getNumberOfVariables() ;
    int getNumberOfObjectives() ;

    Solution<T> copy() ;

    void setAttribute(Object id, Object value) ;
    Object getAttribute(Object id) ;
 
 
    // encrypted variables for evaluation
    BigInteger getEncryptedVariableValue(int index);
    void setEncryptedVariableValue(int index, BigInteger value);
    String getEncryptedVariableValueString(int index) ;
 
    // encrypted solution evaluations
    void setEncryptedEvaluation(int index, BigInteger value) ;
    BigInteger getEncryptedEvaluation(int index) ;
    BigInteger[] getEncryptedEvaluations() ;
}
