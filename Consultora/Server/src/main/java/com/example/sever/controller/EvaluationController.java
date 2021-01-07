package com.example.sever.controller;

import com.example.sever.models.ClientInfo;
import com.example.sever.models.ClientInfoInput;
import com.example.sever.utils.SolutionGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.uma.jmetal.solution.util.AlgorithmResult;

import java.util.List;


@Controller
@CrossOrigin
@RequiredArgsConstructor
public class EvaluationController {

    @ResponseBody
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes=MediaType.APPLICATION_JSON_VALUE)
    public AlgorithmResult getSolutionsEvaluations(@RequestBody ClientInfoInput clientInfoInput) {
        ClientInfo clientInfo = new ClientInfo(clientInfoInput);
        // after receiving the client data, construct a solution for him
        SolutionGenerator solution = new SolutionGenerator(clientInfo);
        return solution.runExperiment();
    }
}
