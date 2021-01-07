export class ClientInfo {

    name: string;
    numberOfVariables: number;
    numberOfObjectives: number;
    lowerLimit: number[];
    upperLimit: number[];

    constructor(name: string,
        numberOfVariables: number,
        numberOfObjectives: number,
        lowerLimit: number[],
        upperLimit: number[]) { 
            this.name = name;
            this.numberOfVariables = numberOfVariables;
            this.numberOfObjectives = numberOfObjectives;
            this.lowerLimit = lowerLimit;
            this.upperLimit = upperLimit;
    }
}