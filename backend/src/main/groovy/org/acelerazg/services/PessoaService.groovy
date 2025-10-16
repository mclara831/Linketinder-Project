package org.acelerazg.services

import org.acelerazg.models.Candidate
import org.acelerazg.models.Company
import org.acelerazg.repositories.CandidateRepository

class PessoaService {

    List<Candidate> candidatos = new ArrayList<>()
    List<Company> empresas = new ArrayList<>()

    PessoaService() {
        carregarDados()
    }

    private void carregarDados() {
//        Empresa e1 = new Empresa(nome: "ZG Soluções",
//                email: "zgsolucoes@gmail.com",
//                cnpj: "00000000/00000",
//                pais: "Brasil",
//                estado: "Goiana",
//                cep: "00000-000",
//                descricao: "Somos uma empresa jovem, comprometida com a inovação e disposta a transformar o sistema financeiro da saúde no Brasil.",
//                competencias: ["Java", "Groovy", "Linux", "Angular"])
//        Empresa e2 = new Empresa(
//                nome: "TechNova",
//                email: "contato@technova.com",
//                cnpj: "11111111/11111",
//                pais: "Brasil",
//                estado: "São Paulo",
//                cep: "11111-111",
//                descricao: "Empresa especializada em soluções de software corporativo e consultoria tecnológica.",
//                competencias: ["Python", "React", "AWS", "Docker"]
//        )
//
//        Empresa e3 = new Empresa(
//                nome: "InovaData",
//                email: "info@inovadata.com",
//                cnpj: "22222222/22222",
//                pais: "Brasil",
//                estado: "Rio de Janeiro",
//                cep: "22222-222",
//                descricao: "Focada em análise de dados e inteligência artificial, ajudamos empresas a tomarem decisões baseadas em dados.",
//                competencias: ["Python", "R", "SQL", "Machine Learning"]
//        )
//
//        Empresa e4 = new Empresa(
//                nome: "CyberSeg",
//                email: "contato@cyberseg.com",
//                cnpj: "33333333/33333",
//                pais: "Brasil",
//                estado: "Minas Gerais",
//                cep: "33333-333",
//                descricao: "Especializada em segurança da informação e auditoria de sistemas corporativos.",
//                competencias: ["Cybersecurity", "Java", "Linux", "Penetration Testing"]
//        )
//
//        Empresa e5 = new Empresa(
//                nome: "WebSolutions",
//                email: "suporte@websolutions.com",
//                cnpj: "44444444/44444",
//                pais: "Brasil",
//                estado: "Paraná",
//                cep: "44444-444",
//                descricao: "Desenvolvimento de websites e aplicações web modernas, com foco em experiência do usuário e performance.",
//                competencias: ["HTML", "CSS", "JavaScript", "Angular", "Node.js"]
//        )
//
//        empresas.addAll(e1, e2, e3, e4, e5)
//
//        Candidato p1 = new Candidato(nome: "Maria Clara",
//                email: "maria@gmail.com",
//                idade: 20,
//                cpf: "000.000.000-01",
//                estado: "Minas Gerais",
//                cep: "00000-001",
//                descricao: "Sou graduante da UFOP pelo curso de Sistemas de Informação, no 4° período",
//                competencias: ["Java", "C", "Git", "Docker"]
//        )
//
//        Candidato p2 = new Candidato(
//                nome: "Lucas Pereira",
//                email: "lucas@gmail.com",
//                idade: 23,
//                cpf: "111.111.111-02",
//                estado: "São Paulo",
//                cep: "11111-002",
//                descricao: "Graduando em Engenharia de Computação, com foco em desenvolvimento web e aplicativos móveis.",
//                competencias: ["JavaScript", "React", "Node.js", "SQL"]
//        )
//
//        Candidato p3 = new Candidato(
//                nome: "Fernanda Souza",
//                email: "fernanda@gmail.com",
//                idade: 25,
//                cpf: "222.222.222-03",
//                estado: "Rio de Janeiro",
//                cep: "22222-003",
//                descricao: "Analista de dados com experiência em estatística e machine learning.",
//                competencias: ["Python", "R", "SQL", "Power BI"]
//        )
//
//        Candidato p4 = new Candidato(
//                nome: "Thiago Lima",
//                email: "thiago@gmail.com",
//                idade: 28,
//                cpf: "333.333.333-04",
//                estado: "Paraná",
//                cep: "33333-004",
//                descricao: "Desenvolvedor full stack apaixonado por tecnologias open-source e cloud computing.",
//                competencias: ["Java", "Spring Boot", "Angular", "AWS", "Docker"]
//        )
//
//        Candidato p5 = new Candidato(
//                nome: "Beatriz Santos",
//                email: "beatriz@gmail.com",
//                idade: 21,
//                cpf: "444.444.444-05",
//                estado: "Bahia",
//                cep: "44444-005",
//                descricao: "Estudante de Sistemas de Informação, interessada em desenvolvimento mobile e UX/UI.",
//                competencias: ["Flutter", "Dart", "Figma", "Git"]
//        )
        candidatos.addAll(new CandidateRepository().findAll())
    }

    void cadastrarCandidato(Candidate candidato) {

    }

    void cadastrarEmpresa(Company empresa) {

    }
}
