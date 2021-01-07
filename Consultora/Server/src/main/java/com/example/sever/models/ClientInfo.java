package com.example.sever.models;

import org.uma.jmetal.problem.impl.AbstractIntegerProblem;
import org.uma.jmetal.solution.IntegerSolution;

import java.io.Serializable;

public class ClientInfo extends AbstractIntegerProblem implements Serializable {

    private static final long serialVersionUID = 1L;

    public ClientInfo(ClientInfoInput clientInfoInput) {
        super();
        setName(clientInfoInput.name);
        setNumberOfVariables(clientInfoInput.numberOfVariables);
        setNumberOfObjectives(clientInfoInput.numberOfObjectives);
        setLowerLimit(clientInfoInput.lowerLimit);
        setUpperLimit(clientInfoInput.upperLimit);
    }

    @Override
    public void evaluate(IntegerSolution integerSolution) {

    }
}
