export class Problem {

    lowerLimit: number;
    upperLimit: number;

    constructor(name: string,
        lowerLimit: number,
        upperLimit: number) { 
            this.lowerLimit = lowerLimit;
            this.upperLimit = upperLimit;
    }
}