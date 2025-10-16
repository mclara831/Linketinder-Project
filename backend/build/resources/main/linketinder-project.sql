CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE enderecos
(
    id     varchar PRIMARY KEY DEFAULT uuid_generate_v4(),
    pais   varchar NOT NULL,
    estado varchar NOT NULL,
    cep    varchar NOT NULL
);

select * from enderecos;

CREATE TABLE candidatos
(
    id              varchar PRIMARY KEY DEFAULT uuid_generate_v4(),
    nome            varchar        NOT NULL,
    sobrenome       varchar,
    email           varchar UNIQUE NOT NULL,
    linkedin        varchar,
    cpf             varchar UNIQUE NOT NULL,
    data_nascimento date           NOT NULL,
    endereco_id     varchar        NOT NULL,
    descricao       text           NOT NULL,
    senha           varchar        NOT NULL,
    FOREIGN KEY (endereco_id) REFERENCES enderecos (id)
);

CREATE TABLE empresas
(
    id          varchar PRIMARY KEY DEFAULT uuid_generate_v4(),
    nome        varchar        NOT NULL,
    email       varchar UNIQUE NOT NULL,
    linkedin    varchar,
    cnpj        varchar UNIQUE NOT NULL,
    endereco_id varchar        NOT NULL,
    descricao   text           NOT NULL,
    senha       varchar        NOT NULL,
    FOREIGN KEY (endereco_id) REFERENCES enderecos (id)
);

CREATE TABLE competencias
(
    id   varchar PRIMARY KEY DEFAULT uuid_generate_v4(),
    nome varchar NOT NULL
);

CREATE TABLE vagas
(
    id          varchar PRIMARY KEY DEFAULT uuid_generate_v4(),
    nome        varchar NOT NULL,
    descricao   text    NOT NULL,
    criada_em   date    NOT NULL,
    endereco_id varchar NOT NULL,
    empresa_id  varchar NOT NULL,
    FOREIGN KEY (endereco_id) REFERENCES enderecos (id),
    FOREIGN KEY (empresa_id) REFERENCES empresas (id)
);

CREATE TABLE candidatos_competencias
(
    id             varchar PRIMARY KEY DEFAULT uuid_generate_v4(),
    candidato_id   varchar,
    competencia_id varchar,
    FOREIGN KEY (candidato_id) REFERENCES candidatos (id),
    FOREIGN KEY (competencia_id) REFERENCES competencias (id)
);

CREATE TABLE empresas_competencias
(
    id             varchar PRIMARY KEY DEFAULT uuid_generate_v4(),
    empresa_id     varchar,
    competencia_id varchar,
    FOREIGN KEY (empresa_id) REFERENCES empresas (id),
    FOREIGN KEY (competencia_id) REFERENCES competencias (id)
);

CREATE TABLE vagas_competencias
(
    id             varchar PRIMARY KEY DEFAULT uuid_generate_v4(),
    vaga_id        varchar,
    competencia_id varchar,
    FOREIGN KEY (vaga_id) REFERENCES vagas (id),
    FOREIGN KEY (competencia_id) REFERENCES competencias (id)
);

INSERT INTO enderecos (pais, estado, cep)
VALUES ('Brasil', 'Minas Gerais', '00.000-00');
INSERT INTO enderecos (pais, estado, cep)
VALUES ('Brasil', 'São Paulo', '11.111-11');
INSERT INTO enderecos (pais, estado, cep)
VALUES ('Brasil', 'Rio de Janeiro', '22.222-22');
INSERT INTO enderecos (pais, estado, cep)
VALUES ('Brasil', 'Minas Gerais', '33.333-33');
INSERT INTO enderecos (pais, estado, cep)
VALUES ('Brasil', 'Goias', '44.444-44');

select *
from enderecos;

insert into candidatos (nome, sobrenome, email, linkedin, cpf, data_nascimento, endereco_id, descricao, senha)
values ('Maria', 'Silva', 'maria@gmail.com', 'linkedin.com/in/maria', '000.000.000-00', '2004-01-01',
        '2c4bba02-b52a-46b0-8e91-9388cba389c6',
        'Sou graduanda da UFOP pelo curso de Sistemas de Informação, no 4° período', '12345678');

insert into candidatos (nome, sobrenome, email, linkedin, cpf, data_nascimento, endereco_id, descricao, senha)
values ('Lucas', 'Pereira', 'lucas@gmail.com', 'linkedin.com/in/lucaspereira', '111.111.111-02', '2002-01-01',
        '8e01149d-fd86-437a-b5fc-ab092eb4387b',
        'Graduando em Engenharia de Computação, com foco em desenvolvimento web e aplicativos móveis.', '12345678');

insert into candidatos (nome, sobrenome, email, linkedin, cpf, data_nascimento, endereco_id, descricao, senha)
values ('Fernanda', 'Souza', 'fernanda@gmail.com', 'linkedin.com/in/fernandasouza', '222.222.222-03', '2000-01-01',
        '9cfe3978-5cd8-4ddf-92f5-cda653f5642d',
        'Analista de dados com experiência em estatística e machine learning.', '12345678');

insert into candidatos (nome, sobrenome, email, linkedin, cpf, data_nascimento, endereco_id, descricao, senha)
values ('Thiago', 'Lima', 'thiago@gmail.com', 'linkedin.com/in/thiagolima', '333.333.333-04', '1997-01-01',
        'adf0f964-0498-434c-a876-5f4050988294',
        'Desenvolvedor full stack apaixonado por tecnologias open-source e cloud computing.', '12345678');

insert into candidatos (nome, sobrenome, email, linkedin, cpf, data_nascimento, endereco_id, descricao, senha)
values ('Beatriz', 'Santos', 'beatriz@gmail.com', 'linkedin.com/in/beatrizsantos', '444.444.444-05', '2004-01-01',
        '0aeda78c-478c-4d5c-aa39-87e04d99be73',
        'Estudante de Sistemas de Informação, interessada em desenvolvimento mobile e UX/UI.', '12345678');

select *
from candidatos;

insert into empresas (nome, email, linkedin, cnpj, endereco_id, descricao, senha)
values ('ZG Soluções', 'zgsolucoes@gmail.com', 'linkedin.com/in/ZG',
        '00000000/00000', '2c4bba02-b52a-46b0-8e91-9388cba389c6',
        'Somos uma empresa jovem, comprometida com a inovação e disposta a transformar o sistema financeiro da saúde no Brasil.',
        '12345678');

insert into empresas (nome, email, linkedin, cnpj, endereco_id, descricao, senha)
values ('TechNova', 'contato@technova.com', 'linkedin.com/company/technova',
        '11111111/11111', '8e01149d-fd86-437a-b5fc-ab092eb4387b',
        'Empresa especializada em soluções de software corporativo e consultoria tecnológica.', '12345678');

insert into empresas (nome, email, linkedin, cnpj, endereco_id, descricao, senha)
values ('InovaData', 'info@inovadata.com', 'linkedin.com/company/inovadata',
        '22222222/22222', '9cfe3978-5cd8-4ddf-92f5-cda653f5642d',
        'Focada em análise de dados e inteligência artificial, ajudamos empresas a tomarem decisões baseadas em dados.',
        '12345678');

insert into empresas (nome, email, linkedin, cnpj, endereco_id, descricao, senha)
values ('CyberSeg', 'contato@cyberseg.com', 'linkedin.com/company/cyberseg',
        '33333333/33333', 'adf0f964-0498-434c-a876-5f4050988294',
        'Especializada em segurança da informação e auditoria de sistemas corporativos.', '12345678');

insert into empresas (nome, email, linkedin, cnpj, endereco_id, descricao, senha)
values ('WebSolutions', 'suporte@websolutions.com', 'linkedin.com/company/websolutions',
        '44444444/44444', '0aeda78c-478c-4d5c-aa39-87e04d99be73',
        'Desenvolvimento de websites e aplicações web modernas, com foco em experiência do usuário e performance.',
        '12345678');

select * from empresas;

insert into competencias (nome) values ('Java');
insert into competencias (nome) values ('Javascript');
insert into competencias (nome) values ('Docker');
insert into competencias (nome) values ('Angular');
insert into competencias (nome) values ('Groovy');
insert into competencias (nome) values ('Grails');
insert into competencias (nome) values ('HTML');
insert into competencias (nome) values ('CSS');

select * from competencias;

insert into vagas (nome, descricao, criada_em, endereco_id, empresa_id)
values ('Desenvolvedor frontend',
        'Irá ficar responsável pelo desenvolvimento do frontend do nosso produto princicpal', CURRENT_DATE,
        '2c4bba02-b52a-46b0-8e91-9388cba389c6', '53bbcc28-feb7-46ef-abfd-10c6472bd126');

insert into vagas (nome, descricao, criada_em, endereco_id, empresa_id)
values ('Desenvolvedor Backend',
        'Será responsável pela implementação de APIs, integração com banco de dados e manutenção da arquitetura de serviços.',
        CURRENT_DATE,
        '8e01149d-fd86-437a-b5fc-ab092eb4387b',
        '53bbcc28-feb7-46ef-abfd-10c6472bd126');

insert into vagas (nome, descricao, criada_em, endereco_id, empresa_id)
values ('Analista de Dados',
        'Irá atuar na coleta, tratamento e análise de dados para geração de insights de negócio e apoio à tomada de decisão.',
        CURRENT_DATE,
        '9cfe3978-5cd8-4ddf-92f5-cda653f5642d',
        '53bbcc28-feb7-46ef-abfd-10c6472bd126');

insert into vagas (nome, descricao, criada_em, endereco_id, empresa_id)
values ('Engenheiro DevOps',
        'Responsável por pipelines de CI/CD, automação de infraestrutura, monitoramento e escalabilidade de sistemas.',
        CURRENT_DATE,
        'adf0f964-0498-434c-a876-5f4050988294',
        '53bbcc28-feb7-46ef-abfd-10c6472bd126');

insert into vagas (nome, descricao, criada_em, endereco_id, empresa_id)
values ('UX/UI Designer',
        'Será responsável pela criação de protótipos, design de interfaces e melhorias na experiência do usuário em nossos produtos digitais.',
        CURRENT_DATE,
        '0aeda78c-478c-4d5c-aa39-87e04d99be73',
        '53bbcc28-feb7-46ef-abfd-10c6472bd126');

select * from vagas;
alter table vagas alter column criada_em set default CURRENT_DATE;

insert into candidatos_competencias (candidato_id, competencia_id)
values ('eb8ce398-37cb-405d-b26a-b526dc69ebf5', '61ecfd6d-276f-4e17-8f49-f863797d49cb');
insert into candidatos_competencias (candidato_id, competencia_id)
values ('008aa667-889f-4864-b87f-e1ab72de35a5', 'a739266d-4428-400f-96ab-8d2b958880ae');
insert into candidatos_competencias (candidato_id, competencia_id)
values ('7b018e6f-83be-4ceb-8fe9-2d62c4a429cf', '61ecfd6d-276f-4e17-8f49-f863797d49cb');
insert into candidatos_competencias (candidato_id, competencia_id)
values ('53160d48-3055-437e-abf0-25cc6ff1294c', '115dd943-5c91-422a-a26e-e6ba40ec5e66');
insert into candidatos_competencias (candidato_id, competencia_id)
values ('9ea6f0d3-9090-4b5e-926b-3c3cd92e279f', '115dd943-5c91-422a-a26e-e6ba40ec5e66');


insert into empresas_competencias (empresa_id, competencia_id)
values ('53bbcc28-feb7-46ef-abfd-10c6472bd126', '61ecfd6d-276f-4e17-8f49-f863797d49cb');
insert into empresas_competencias (empresa_id, competencia_id)
values ('e8e787be-08cc-4db5-8461-f8b78bd82fde', 'a739266d-4428-400f-96ab-8d2b958880ae');
insert into empresas_competencias (empresa_id, competencia_id)
values ('d733452b-b646-4ca7-838e-ba4ec9d2580e', '61ecfd6d-276f-4e17-8f49-f863797d49cb');
insert into empresas_competencias (empresa_id, competencia_id)
values ('d7d9645b-fd70-4c5b-8875-fb6fa387af6c', '115dd943-5c91-422a-a26e-e6ba40ec5e66');
insert into empresas_competencias (empresa_id, competencia_id)
values ('8d6fd80c-a484-4acf-9db6-8c678a545051', '115dd943-5c91-422a-a26e-e6ba40ec5e66');

insert into vagas_competencias (vaga_id, competencia_id)
values ('fca46222-11e8-4437-83c9-d2f0533c6dda', '61ecfd6d-276f-4e17-8f49-f863797d49cb');
insert into vagas_competencias (vaga_id, competencia_id)
values ('bf84ba21-c6c5-4716-a374-d1ce20e9baf7', 'a739266d-4428-400f-96ab-8d2b958880ae');
insert into vagas_competencias (vaga_id, competencia_id)
values ('8fd3429f-bf61-4337-b9e5-651d25a0691f', '61ecfd6d-276f-4e17-8f49-f863797d49cb');
insert into vagas_competencias (vaga_id, competencia_id)
values ('364796a3-06f9-4949-bb6d-b625c30ea8ed', '115dd943-5c91-422a-a26e-e6ba40ec5e66');
insert into vagas_competencias (vaga_id, competencia_id)
values ('a3023a3d-5038-4841-a8a2-e8cd622c6b8d', '115dd943-5c91-422a-a26e-e6ba40ec5e66');

-- LÓGICA DE MATCH
CREATE TABLE matches (
   id varchar PRIMARY KEY DEFAULT uuid_generate_v4(),
   candidato_id varchar NOT NULL,
   vaga_id varchar NOT NULL,
   empresa_gostou boolean DEFAULT 'false',
    criado_em date default CURRENT_DATE
);

insert into matches(candidato_id, vaga_id) VALUES ('eb8ce398-37cb-405d-b26a-b526dc69ebf5', 'fca46222-11e8-4437-83c9-d2f0533c6dda');
insert into matches(candidato_id, vaga_id, empresa_gostou) VALUES ('008aa667-889f-4864-b87f-e1ab72de35a5', 'bf84ba21-c6c5-4716-a374-d1ce20e9baf7', true);
insert into matches(candidato_id, vaga_id) VALUES ('7b018e6f-83be-4ceb-8fe9-2d62c4a429cf', '8fd3429f-bf61-4337-b9e5-651d25a0691f');
insert into matches(candidato_id, vaga_id, empresa_gostou) VALUES ('53160d48-3055-437e-abf0-25cc6ff1294c', '364796a3-06f9-4949-bb6d-b625c30ea8ed', true);
insert into matches(candidato_id, vaga_id) VALUES ('9ea6f0d3-9090-4b5e-926b-3c3cd92e279f', 'a3023a3d-5038-4841-a8a2-e8cd622c6b8d');

select * from matches;