package com.example.sever.models;

import lombok.Data;

import java.util.List;

@Data
public class ClientInfoInput {

    String name;
    int numberOfVariables;
    int numberOfObjectives;
    List<Integer> lowerLimit;
    List<Integer> upperLimit;
}
