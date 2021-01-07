package com.example.sever.utils;

import com.example.sever.models.ClientInfo;
import org.uma.jmetal.algorithm.Algorithm;
import org.uma.jmetal.algorithm.multiobjective.nsgaii.NSGAIIBuilder;
import org.uma.jmetal.operator.CrossoverOperator;
import org.uma.jmetal.operator.MutationOperator;
import org.uma.jmetal.operator.impl.crossover.IntegerSBXCrossover;
import org.uma.jmetal.operator.impl.mutation.IntegerPolynomialMutation;
import org.uma.jmetal.solution.IntegerSolution;
import org.uma.jmetal.solution.util.AlgorithmResult;
import org.uma.jmetal.util.experiment.Experiment;
import org.uma.jmetal.util.experiment.ExperimentBuilder;
import org.uma.jmetal.util.experiment.component.ExecuteAlgorithms;
import org.uma.jmetal.util.experiment.util.ExperimentAlgorithm;
import org.uma.jmetal.util.experiment.util.ExperimentProblem;

import java.util.ArrayList;
import java.util.List;

public class SolutionGenerator {

    String experimentBaseDirectory = "ADS-Solutions-And-Results";
    private final int INDEPENDENT_RUNS = 1;
    List<ExperimentProblem<IntegerSolution>> problemList;
    List<ExperimentAlgorithm<IntegerSolution, List<IntegerSolution>>> algorithmList;


    public SolutionGenerator(ClientInfo clientInput) {
        this.problemList = new ArrayList<>();
        this.problemList.add(new ExperimentProblem<>(clientInput));
        this.algorithmList = configureAlgorithmList(problemList);
    }

    private List<ExperimentAlgorithm<IntegerSolution, List<IntegerSolution>>> configureAlgorithmList(
            List<ExperimentProblem<IntegerSolution>> problemList) {
        List<ExperimentAlgorithm<IntegerSolution, List<IntegerSolution>>> algorithms = new ArrayList<>();
        double crossoverProbability = 0.9 ;
        double crossoverDistributionIndex = 20.0 ;
        CrossoverOperator<IntegerSolution> crossover = new IntegerSBXCrossover(crossoverProbability, crossoverDistributionIndex) ;


        for (int run = 0; run < INDEPENDENT_RUNS; run++) {
            for (ExperimentProblem<IntegerSolution> integerSolutionExperimentProblem : problemList) {
                double mutationProbability = 1.0 / integerSolutionExperimentProblem.getProblem().getNumberOfVariables();
                double mutationDistributionIndex = 20.0;
                MutationOperator<IntegerSolution> mutation = new IntegerPolynomialMutation(mutationProbability, mutationDistributionIndex);
                Algorithm<List<IntegerSolution>> algorithm = new NSGAIIBuilder<IntegerSolution>(
                        integerSolutionExperimentProblem.getProblem(),
                        crossover,
                        mutation)
                        .setMaxEvaluations(1000)
                        .setPopulationSize(100)
                        .build();
                algorithms.add(new ExperimentAlgorithm<>(algorithm, "ADS", integerSolutionExperimentProblem, run));
            }
        }
        return algorithms;
    }

    public AlgorithmResult runExperiment() {
        Experiment<IntegerSolution, List<IntegerSolution>> experiment =
                new ExperimentBuilder<IntegerSolution, List<IntegerSolution>>("ADS")
                        .setAlgorithmList(algorithmList)
                        .setProblemList(problemList)
                        .setExperimentBaseDirectory(experimentBaseDirectory)
                        .setOutputParetoFrontFileName("FUN")
                        .setOutputParetoSetFileName("VAR")
                        .setReferenceFrontDirectory("/pareto_fronts")
                        .setIndependentRuns(INDEPENDENT_RUNS)
                        .setNumberOfCores(4)
                        .build();
        return new ExecuteAlgorithms<>(experiment).runWithResults();
    }
}
