# Linketinder Project

Este repositório será utilizado para o desenvolvimento do Linketinder Project durante o Acelera ZG.

## 📌 Funcionalidades
    ===> Empresas
    1. Listar todas empresas
    2. Cadastrar nova empresa
    3. Atualizar dados de empresa
    4. Deletar empresa

    ===> Candidatos
    5. Listar todos candidatos
    6. Cadastrar novo candidato
    7. Atualizar dados de candidato
    8. Deletar candidato

    ===> Vagas
    9. Listar todas vagas
    10. Encontrar vagas por CNPJ
    11. Cadastrar nova vaga
    12. Atualizar dados de vaga
    13. Deletar vaga
    
    ===> Competencias
    14. Listar todas competências
    15. Cadastrar nova competência
    16. Cadastrar uma lista de competência
    17. Atualizar dados de competência
    18. Deletar competência



## 🛠️ Tecnologias utilizadas
- Backend:
    - Groovy

- Frontend:
    - Typescript
    - HTML
    - Css

- Banco de dados:
    - PostgreSQL



## 📂 Estrutura do projeto

- **Backend**: `Linkertinder/src/main/groovy/org/acelerazg/Main.groovy` → Classe principal para executar a aplicação.
- **Frontend**:  `Linkertinder/frontend`

## Modelo lógico de Banco de Dados
Para elaborar esse modelo foi utilizada a ferramenta: https://dbdiagram.io/home

![Modelo do banco de dados](Linketinder-Project.png)

- O Script SQL se encontra na pasta: `Linkertinder/src/main/groovy/resources`

## 🚀 Como executar

- Clone este repositório 

``` 
git clone  git@github.com:mclara831/Linketinder-Project.git
```

-  Abra na sua IDE de preferência

- Backend:
    - Navegue atá a classe principal indicada no caminho acima:

    ``` 
    cd Linkertinder
    ```
    - Execute o projeto

    ```
    java -jar build/libs/Linkertinder-all.jar

    ```

- Frontend: 
    - Navegue atá a classe principal indicada no caminho acima:

    ``` 
    cd Linkertinder/frontend
    ```
    - Instale as dependências:

    ``` 
    npm install
    ```
    - Execute a aplicação:
    ```
    npm run dev
    ```



## 👩‍💻 Créditos

- Maria Clara Barbosa Fernandes