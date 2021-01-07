import { Component, OnInit } from '@angular/core';
import { ApiService } from '../api.service';
import { ClientInfo } from '../models/ClientInfo';
import { Problem } from '../models/Problem';

@Component({
  selector: 'app-homepage',
  templateUrl: './homepage.component.html',
  styleUrls: ['./homepage.component.scss']
})
export class HomepageComponent implements OnInit {

  aboutUs: boolean = false;
  problema: boolean = false;
  solucao: boolean = false;

  semSolucao: boolean = true;
  solucaoAceite: boolean = false;

  isEditMode: boolean = true;

  clientInfo: ClientInfo;
  solutionsOutput: any;

  value: string = "GERAR SOLUCAO";

  name: any;
  numberOfVariables: any;
  numberOfObjectives: any;
  lowerLimit: any;
  upperLimit: any;
  temObjetivos: boolean = false;

  lowerLimits: number[] = new Array();
  upperLimits: number[] = new Array();

  //variaveis: any[];
  variaveis = [];

  problem: Problem;

  constructor(private javaApi: ApiService) { }

  ngOnInit(): void {
  }

  // mostrar
  screenChangedEvent(screen: number) {
    switch(screen) {
        case 0:
          this.aboutUs = true;
          this.solucao = false;
          this.problema = false;
          break;
        case 1:
          this.aboutUs = false;
          this.solucao = true;
          this.problema = false;
          break;
        case 2:
          this.aboutUs = false;
          this.solucao = false;
          this.problema = true;
          this.temObjetivos = false;
          this.lowerLimit = "";
          this.upperLimit = "";
          this.isEditMode = true;
          this.variaveis = new Array();
          break;
        case 3:
          this.aboutUs = false;
          this.solucao = false;
          this.problema = false;
          break;
    }
    console.log("screen:", screen);
  }

  gerarSolucao() {
    this.isEditMode = false;
    this.lowerLimits = new Array();
    this.upperLimits = new Array();
    this.clientInfo = null;

    this.variaveis.forEach(prob => {
      this.lowerLimits.push(Number.parseInt(prob.lowerLimit));
      this.upperLimits.push(Number.parseInt(prob.upperLimit));
    });


    this.clientInfo = new ClientInfo("Teste", this.lowerLimits.length, /*Number.parseInt(this.numberOfObjectives)*/2, this.lowerLimits, this.upperLimits);
    this.javaApi.getSolution(this.clientInfo).subscribe(resp => {
      console.log("Resposta server:", resp);
      this.solutionsOutput = resp;
      this.screenChangedEvent(1);
      this.semSolucao = false;
    });
  }

  removerProblema() {
    var table = document.getElementById("tableProblemas") as HTMLTableElement;
    for (var i = 1, row; row = table.rows[i]; i++) {
        var checkBox = row.cells[0].firstChild;
        var idProblema = row.cells[1].firstChild.nodeValue;
        if (checkBox.checked) {
          var x = i;
          this.variaveis.splice(this.variaveis.findIndex(i => i.id === i),1);
            table.deleteRow(i);
            i--;
        }
    }
}

  gravarProblema() {

    if(this.numberOfObjectives){
      this.temObjetivos = true;
    }

    var tabela = document.getElementById("tableProblemas");
    var linha = document.createElement('tr');

    var celula1 = document.createElement("td");
    var celula2 = document.createElement("td");
    var celula3 = document.createElement("td");
    var celula4 = document.createElement("td");

    var checkBox = document.createElement('input');
    checkBox.setAttribute('type', 'checkbox');
    checkBox.setAttribute('name', 'selectJ');

    var id = document.createTextNode("" + this.variaveis.length);

    if ((<HTMLInputElement>document.getElementById("lowerLimit")).value !== "") {
        var lowerLimit = document.createTextNode((<HTMLInputElement>document.getElementById("lowerLimit")).value); //guarda na variavel o input
    } else {
        alert("Insira o valor minimo da variavel!");
    }

    if ((<HTMLInputElement>document.getElementById("upperLimit")).value !== "") {
        var upperLimit = document.createTextNode((<HTMLInputElement>document.getElementById("upperLimit")).value);
    } else {
        alert("Insira o valor maximo da variavel!");
    }


    celula1.appendChild(checkBox);
    celula2.appendChild(id);
    celula3.appendChild(lowerLimit);
    celula4.appendChild(upperLimit);

    linha.appendChild(celula1);
    linha.appendChild(celula2);
    linha.appendChild(celula3);
    linha.appendChild(celula4);

    tabela.appendChild(linha);

    this.problem = new Problem("", this.lowerLimit, this.upperLimit);

    this.variaveis.push(this.problem); //adicionar no array variaveis para incrementar os ids e saber o numero de variaveis inseridas
    this.lowerLimit = "";
    this.upperLimit = "";
  }

  aceitarSolucao() {
    this.solucaoAceite = true;
  }

  criarNovoProblema() {
    this.semSolucao = true;
    this.solucaoAceite = false;
    this.screenChangedEvent(2);
  }
}
