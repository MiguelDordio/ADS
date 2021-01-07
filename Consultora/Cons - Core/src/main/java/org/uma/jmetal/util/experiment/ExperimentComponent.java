package org.uma.jmetal.util.experiment;

import org.uma.jmetal.solution.util.AlgorithmResult;

import java.io.IOException;
import java.util.List;

/**
 * An experiment is composed of instances of this interface.
 *
 * @author Antonio J. Nebro <antonio@lcc.uma.es>
 */
public interface ExperimentComponent {
  void run() throws IOException;
  AlgorithmResult runWithResults() throws IOException;
}
