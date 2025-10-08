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
        '67aaece0-ee39-4e8f-a2a5-5491cdf9860c',
        'Sou graduanda da UFOP pelo curso de Sistemas de Informação, no 4° período', '12345678');

insert into candidatos (nome, sobrenome, email, linkedin, cpf, data_nascimento, endereco_id, descricao, senha)
values ('Lucas', 'Pereira', 'lucas@gmail.com', 'linkedin.com/in/lucaspereira', '111.111.111-02', '2002-01-01',
        '0663b4ac-33c6-4055-8d4d-7e662580af98',
        'Graduando em Engenharia de Computação, com foco em desenvolvimento web e aplicativos móveis.', '12345678');

insert into candidatos (nome, sobrenome, email, linkedin, cpf, data_nascimento, endereco_id, descricao, senha)
values ('Fernanda', 'Souza', 'fernanda@gmail.com', 'linkedin.com/in/fernandasouza', '222.222.222-03', '2000-01-01',
        '94bccae0-c982-4f2d-8934-5ef3b48b5200',
        'Analista de dados com experiência em estatística e machine learning.', '12345678');

insert into candidatos (nome, sobrenome, email, linkedin, cpf, data_nascimento, endereco_id, descricao, senha)
values ('Thiago', 'Lima', 'thiago@gmail.com', 'linkedin.com/in/thiagolima', '333.333.333-04', '1997-01-01',
        '78a7497f-0a10-467e-a33c-3ff2dd01e5c5',
        'Desenvolvedor full stack apaixonado por tecnologias open-source e cloud computing.', '12345678');

insert into candidatos (nome, sobrenome, email, linkedin, cpf, data_nascimento, endereco_id, descricao, senha)
values ('Beatriz', 'Santos', 'beatriz@gmail.com', 'linkedin.com/in/beatrizsantos', '444.444.444-05', '2004-01-01',
        '82164c58-92e6-416b-a494-42b6e4e5a360',
        'Estudante de Sistemas de Informação, interessada em desenvolvimento mobile e UX/UI.', '12345678');

select *
from candidatos;

insert into empresas (nome, email, linkedin, cnpj, endereco_id, descricao, senha)
values ('ZG Soluções', 'zgsolucoes@gmail.com', 'linkedin.com/in/ZG',
        '00000000/00000', '67aaece0-ee39-4e8f-a2a5-5491cdf9860c',
        'Somos uma empresa jovem, comprometida com a inovação e disposta a transformar o sistema financeiro da saúde no Brasil.',
        '12345678');

insert into empresas (nome, email, linkedin, cnpj, endereco_id, descricao, senha)
values ('TechNova', 'contato@technova.com', 'linkedin.com/company/technova',
        '11111111/11111', '0663b4ac-33c6-4055-8d4d-7e662580af98',
        'Empresa especializada em soluções de software corporativo e consultoria tecnológica.', '12345678');

insert into empresas (nome, email, linkedin, cnpj, endereco_id, descricao, senha)
values ('InovaData', 'info@inovadata.com', 'linkedin.com/company/inovadata',
        '22222222/22222', '94bccae0-c982-4f2d-8934-5ef3b48b5200',
        'Focada em análise de dados e inteligência artificial, ajudamos empresas a tomarem decisões baseadas em dados.',
        '12345678');

insert into empresas (nome, email, linkedin, cnpj, endereco_id, descricao, senha)
values ('CyberSeg', 'contato@cyberseg.com', 'linkedin.com/company/cyberseg',
        '33333333/33333', '78a7497f-0a10-467e-a33c-3ff2dd01e5c5',
        'Especializada em segurança da informação e auditoria de sistemas corporativos.', '12345678');

insert into empresas (nome, email, linkedin, cnpj, endereco_id, descricao, senha)
values ('WebSolutions', 'suporte@websolutions.com', 'linkedin.com/company/websolutions',
        '44444444/44444', '82164c58-92e6-416b-a494-42b6e4e5a360',
        'Desenvolvimento de websites e aplicações web modernas, com foco em experiência do usuário e performance.',
        '12345678');

select *
from empresas;

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
        '67aaece0-ee39-4e8f-a2a5-5491cdf9860c', '4bbd2bd4-ef27-4fa5-b8aa-eb16c97ba5fb');

insert into vagas (nome, descricao, criada_em, endereco_id, empresa_id)
values ('Desenvolvedor Backend',
        'Será responsável pela implementação de APIs, integração com banco de dados e manutenção da arquitetura de serviços.',
        CURRENT_DATE,
        '67aaece0-ee39-4e8f-a2a5-5491cdf9860c',
        '4bbd2bd4-ef27-4fa5-b8aa-eb16c97ba5fb');

insert into vagas (nome, descricao, criada_em, endereco_id, empresa_id)
values ('Analista de Dados',
        'Irá atuar na coleta, tratamento e análise de dados para geração de insights de negócio e apoio à tomada de decisão.',
        CURRENT_DATE,
        '67aaece0-ee39-4e8f-a2a5-5491cdf9860c',
        '4bbd2bd4-ef27-4fa5-b8aa-eb16c97ba5fb');

insert into vagas (nome, descricao, criada_em, endereco_id, empresa_id)
values ('Engenheiro DevOps',
        'Responsável por pipelines de CI/CD, automação de infraestrutura, monitoramento e escalabilidade de sistemas.',
        CURRENT_DATE,
        '67aaece0-ee39-4e8f-a2a5-5491cdf9860c',
        '4bbd2bd4-ef27-4fa5-b8aa-eb16c97ba5fb');

insert into vagas (nome, descricao, criada_em, endereco_id, empresa_id)
values ('UX/UI Designer',
        'Será responsável pela criação de protótipos, design de interfaces e melhorias na experiência do usuário em nossos produtos digitais.',
        CURRENT_DATE,
        '67aaece0-ee39-4e8f-a2a5-5491cdf9860c',
        '4bbd2bd4-ef27-4fa5-b8aa-eb16c97ba5fb');

select * from vagas
alter table vagas alter column criada_em set default CURRENT_DATE;

insert into candidatos_competencias (candidato_id, competencia_id)
values ('09134f66-3ebe-4157-b9da-1edc9ec38486', 'fb623f86-8049-475c-98cd-e23884f19c2b');
insert into candidatos_competencias (candidato_id, competencia_id)
values ('207601fb-377e-4558-9e22-8b770ad2f649', '0f7b45b7-da73-4451-aaaa-890b1d80f02d');
insert into candidatos_competencias (candidato_id, competencia_id)
values ('c813b035-9a0b-43d3-a226-bf7f91956b03', 'fb623f86-8049-475c-98cd-e23884f19c2b');
insert into candidatos_competencias (candidato_id, competencia_id)
values ('2e0f1b9a-db67-435c-9e0e-f7b14e69643b', '890abf3b-8098-4352-92dd-121f1e8fcd95');
insert into candidatos_competencias (candidato_id, competencia_id)
values ('2e0f1b9a-db67-435c-9e0e-f7b14e69643b', '890abf3b-8098-4352-92dd-121f1e8fcd95');


insert into empresas_competencias (empresa_id, competencia_id)
values ('4bbd2bd4-ef27-4fa5-b8aa-eb16c97ba5fb', 'fb623f86-8049-475c-98cd-e23884f19c2b');
insert into empresas_competencias (empresa_id, competencia_id)
values ('d83c7e56-f0a6-4950-a74c-cbb55f6c8b44', '0f7b45b7-da73-4451-aaaa-890b1d80f02d');
insert into empresas_competencias (empresa_id, competencia_id)
values ('5fe3e2e3-4da8-4a0c-a9d8-a50213059013', 'fb623f86-8049-475c-98cd-e23884f19c2b');
insert into empresas_competencias (empresa_id, competencia_id)
values ('e14cb684-999e-40b3-be97-0963875b01a0', '890abf3b-8098-4352-92dd-121f1e8fcd95');
insert into empresas_competencias (empresa_id, competencia_id)
values ('b52e9105-c725-4f67-9923-931f4fb1ddef', '890abf3b-8098-4352-92dd-121f1e8fcd95');

insert into vagas_competencias (vaga_id, competencia_id)
values ('7bbd6208-dd13-4499-a366-ce9657784333', 'fb623f86-8049-475c-98cd-e23884f19c2b');
insert into vagas_competencias (vaga_id, competencia_id)
values ('21ac287c-6011-4c88-8728-ae980f7c3c46', '0f7b45b7-da73-4451-aaaa-890b1d80f02d');
insert into vagas_competencias (vaga_id, competencia_id)
values ('770185c7-10fc-433e-8ad2-75c9a33ea5ab', 'fb623f86-8049-475c-98cd-e23884f19c2b');
insert into vagas_competencias (vaga_id, competencia_id)
values ('313ff857-a6a0-43e8-a963-5a7c0546d4a7', '890abf3b-8098-4352-92dd-121f1e8fcd95');
insert into vagas_competencias (vaga_id, competencia_id)
values ('4bc1b3e4-4bb1-4005-a72b-a0f2bddd63cf', '890abf3b-8098-4352-92dd-121f1e8fcd95');
