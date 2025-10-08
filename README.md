# Linketinder Project

Este repositório será utilizado para o desenvolvimento do Linketinder Project durante o Acelera ZG.

## 🧠 Descrição do Projeto

O Linketinder é uma plataforma de recrutamento que une as funcionalidades do LinkedIn à mecânica de correspondência do Tinder.
A aplicação permite que empresas e candidatos interajam de forma anônima, revelando suas informações completas apenas quando ocorre um match, incentivando uma seleção baseada em competências e interesses compatíveis.

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

- **Backend**: `backend/src/main/groovy/org/acelerazg/Main.groovy` → Classe principal para executar a aplicação.
- **Frontend**:  `frontend` → contém os arquivos para execução do frontend da aplicação.

## Modelo lógico de Banco de Dados
Para elaborar esse modelo foi utilizada a ferramenta: https://dbdiagram.io/home

![Modelo do banco de dados](Linketinder-Project.png)

- O Script SQL se encontra na pasta: `backend/src/main/groovy/resources`

## 🚀 Como executar

- Clone este repositório 

``` 
git clone  git@github.com:mclara831/Linketinder-Project.git
```

-  Abra na sua IDE de preferência

### Backend:

1. Para conectar a aplicação ao banco de dados, foi utilizado um container PostgreSQL executado no Docker, responsável por armazenar e povoar as tabelas do sistema.

    ```docker
    
    docker run --name linketinderdb \
    -e POSTGRES_USER=postgres \
    -e POSTGRES_PASSWORD=postgres \
    -e POSTGRES_DB=linketinderdb \
    -p 5435:5432 \
    -d postgres:16.3

    ```
2.  Navegue atá a classe principal indicada no caminho acima:

    ``` 
    cd backend
    ```
3. Execute o projeto

    ```
    java -jar build/libs/backend-all.jar

    ```

### Frontend: 
1. Navegue atá a classe principal indicada no caminho acima:

    ``` 
    cd frontend
    ```
2. Instale as dependências:

    ``` 
    npm install
    ```
3. Execute a aplicação:
    ```
    npm run dev
    ```



## 👩‍💻 Créditos

- Maria Clara Barbosa Fernandes