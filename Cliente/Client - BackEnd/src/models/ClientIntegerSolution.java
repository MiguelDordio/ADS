package models;

import java.math.BigInteger;
import java.util.List;

public class ClientIntegerSolution {

    private double[] objectives;
    private List<Integer> variables;
    private BigInteger[] encryptedEvaluations;
    private List<BigInteger> encryptedVariables;


    // Objectives
    public double[] getObjectives() {
        return objectives;
    }

    public void setObjectives(double[] objectives) {
        this.objectives = objectives;
    }

    public int getNumberOfObjectives() {
        return this.objectives.length;
    }


    // Variables
    public List<Integer> getVariables() {
        return this.variables;
    }

    public void setVariables(List<Integer> variables) {
        this.variables = variables;
    }

    public int getNumberOfVariables() {
        return this.variables.size();
    }


    // Encrypted Evaluations
    public BigInteger[] getEncryptedEvaluations() {
        return encryptedEvaluations;
    }

    public void setEncryptedEvaluations(BigInteger[] encryptedEvaluations) {
        this.encryptedEvaluations = encryptedEvaluations;
    }

    public void setEncryptedEvaluation(int index, BigInteger value) {
        this.encryptedVariables.set(index, value);
    }

    // Encrypted Variables
    public List<BigInteger> getEncryptedVariables() {
        return encryptedVariables;
    }

    public void setEncryptedVariables(List<BigInteger> encryptedVariables) {
        this.encryptedVariables = encryptedVariables;
    }

    public BigInteger getEncryptedVariableValue(int index) {
        return this.encryptedVariables.get(index);
    }
}
