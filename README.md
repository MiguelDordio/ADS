# ADS
Projeto de encriptação homomórfica numa situação de Consultora - Cliente

O projeto pretende simular o ato do pedido feito pelo Cliente, de desenvolvimento de uma solução a uma consultora, bem como a posterior avaliação da solução gerada. 
Tudo isto usando criptografia homomórfica para assegurar que a disponibilização da solução gerada não compromete nenhuma das partes.
O FrontEnd da consultora foi realizado em Angular, já o BackEnd, tanto do cliente como da consultora em Java, recorrendo à framework Spring.

# Organização do projeto

O projeto está dividido em duas secções principais:

* Componente representativa do Clinte
  * Backend - Java
* Componente representativa da Consultora
  * Frontend - Angular
  * Backend - Java (com JMetal e Spring Framework)

# Execução do projeto em modo de desenvolvimento
  * ## Consultora

    ### Frontend

    1. Instalar o Node.js (desenvolvimento foi feito com a versão `12.16.1`).
    2. Colocar-se na diretoria do frontend da consultora.
    2. Correr o comando `npm install`.
    3. Correr o comando `npm install -g @angular/cli`.
    4. Inicializar o site localmente com o comando `ng serve`. Irá ficar disponivel no `localhost:4200`.

    ### Backend

    A componente de backend da consultora foi desenvolvida com java 8.
    O projeto `Server` pode ser importado para um IDE (usado para desenvolvimento o `Intellij`) como um projeto Maven.
    Instalar todas as dependências com o comando `mvn clean install`.
    Dentro do IDE ao correr o servidor com o butão `run` irá inicial o servidor apresentando uma mensagem sobre a framework Spring.
    O servidor ficará disponivel em `localhost:8080` e irá aguardar pedidos `POST` pelo frontend.

  * ## Cliente

    ### Backend
    A componente de backend do cliente foi desenvolvida com java 8.
    O projeto `Cliente - Backend` pode ser importado para um IDE (usado para desenvolvimento o `Intellij`) como um projeto Java
    Assim que o servidor de backend da consultora apresentar indicação na consola que está "ligado", a parte do backend do cliente pode ser inicializada
    Assim que a inicialização for feita, este irá comunicar com o backend da consultora afim de transmitir as soluções e avaliações.

  * ## Fluxo de execução:

    1. Inicializar o servidor da consultora.
    2. Inicializar o site da consultora.
    3. Preencher os dados do problema na secção `Problema`.
    4. Clicar em `Gerar Solução`.
    5. O servidor da consultora irá gerar as chaves de encriptação públicas e privadas para iniciar a comunicação com o backend do cliente de forma segura e encriptada
    6. Após na consola for indicado que o servidor se encontra `ligado`, inicilizar o backend do cliente carregando em `Run` no IDE.
    7. O backend da consultora irá gerar uma solução e comunica-la ao backend do cliente por sockets para que a mesma seja avaliada.
    8. Após o processo estar concluído será apresentado o resultado final da avaliação no site (frontend) da consultora.
    9. Caso o cliente aceite a proposta feita, é então apresentada a solução gerada pela consultora.
    


# Frameworks usadas

* Spring Boot
* JMetal
* Angular

### Este trabalho foi realizado no âmbito da cadeira de Arquitetura e Desenho de Software do mestrado em Engenharia Informática do ISCTE-IUL, tendo sido realizado por:
* Alexandre Valério
* Bruno Costa
* Micaela Fonseca
* Miguel Oliveira
