package org.uma.jmetal.solution.util;

import java.util.List;

public class AlgorithmResult {

    List<List<Integer>> solutions;
    List<List<Integer>> evaluations;

    public AlgorithmResult() {
    }

    public AlgorithmResult(List<List<Integer>> solutions, List<List<Integer>> evaluations) {
        this.solutions = solutions;
        this.evaluations = evaluations;
    }

    public List<List<Integer>> getSolutions() {
        return solutions;
    }

    public void setSolutions(List<List<Integer>> solutions) {
        this.solutions = solutions;
    }

    public List<List<Integer>> getEvaluations() {
        return evaluations;
    }

    public void setEvaluations(List<List<Integer>> evaluations) {
        this.evaluations = evaluations;
    }
}
