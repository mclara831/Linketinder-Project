package cli

import models.Candidato
import models.Empresa

class UI {

    static final Scanner sc = new Scanner(System.in)

    static menu() {
        println "\n\n========================= MENU ============================="
        println "1. Listar todas empresas"
        println "2. Cadastrar nova empresa"
        println "3. Listar todos candadatos"
        println "4. Cadastrar novo candidato"
        println "0. Sair"
        println "============================================================"
        print "Escolha uma opcao: "
    }

    static cadastraPessoas(List<Candidato> candidatos, List<Empresa> empresas) {
        Empresa e1 = new Empresa(nome: "ZG Soluções",
                                email: "zgsolucoes@gmail.com",
                                cnpj: "00000000/00000",
                                pais: "Brasil",
                                estado: "Goiana",
                                cep: "00000-000",
                                descricao: "Somos uma empresa jovem, comprometida com a inovação e disposta a transformar o sistema financeiro da saúde no Brasil.",
                                competencias: ["Java", "Groovy", "Linux", "Angular"])
        Empresa e2 = new Empresa(
                nome: "TechNova",
                email: "contato@technova.com",
                cnpj: "11111111/11111",
                pais: "Brasil",
                estado: "São Paulo",
                cep: "11111-111",
                descricao: "Empresa especializada em soluções de software corporativo e consultoria tecnológica.",
                competencias: ["Python", "React", "AWS", "Docker"]
        )

        Empresa e3 = new Empresa(
                nome: "InovaData",
                email: "info@inovadata.com",
                cnpj: "22222222/22222",
                pais: "Brasil",
                estado: "Rio de Janeiro",
                cep: "22222-222",
                descricao: "Focada em análise de dados e inteligência artificial, ajudamos empresas a tomarem decisões baseadas em dados.",
                competencias: ["Python", "R", "SQL", "Machine Learning"]
        )

        Empresa e4 = new Empresa(
                nome: "CyberSeg",
                email: "contato@cyberseg.com",
                cnpj: "33333333/33333",
                pais: "Brasil",
                estado: "Minas Gerais",
                cep: "33333-333",
                descricao: "Especializada em segurança da informação e auditoria de sistemas corporativos.",
                competencias: ["Cybersecurity", "Java", "Linux", "Penetration Testing"]
        )

        Empresa e5 = new Empresa(
                nome: "WebSolutions",
                email: "suporte@websolutions.com",
                cnpj: "44444444/44444",
                pais: "Brasil",
                estado: "Paraná",
                cep: "44444-444",
                descricao: "Desenvolvimento de websites e aplicações web modernas, com foco em experiência do usuário e performance.",
                competencias: ["HTML", "CSS", "JavaScript", "Angular", "Node.js"]
        )

        empresas.addAll(e1, e2, e3, e4, e5)

        Candidato p1 = new Candidato(nome: "Maria Clara",
                                email: "maria@gmail.com",
                                idade: 20,
                                cpf: "000.000.000-01",
                                estado: "Minas Gerais",
                                cep: "00000-001",
                                descricao: "Sou graduante da UFOP pelo curso de Sistemas de Informação, no 4° período",
                                competencias: ["Java", "C", "Git", "Docker"]
        )

        Candidato p2 = new Candidato(
                nome: "Lucas Pereira",
                email: "lucas@gmail.com",
                idade: 23,
                cpf: "111.111.111-02",
                estado: "São Paulo",
                cep: "11111-002",
                descricao: "Graduando em Engenharia de Computação, com foco em desenvolvimento web e aplicativos móveis.",
                competencias: ["JavaScript", "React", "Node.js", "SQL"]
        )

        Candidato p3 = new Candidato(
                nome: "Fernanda Souza",
                email: "fernanda@gmail.com",
                idade: 25,
                cpf: "222.222.222-03",
                estado: "Rio de Janeiro",
                cep: "22222-003",
                descricao: "Analista de dados com experiência em estatística e machine learning.",
                competencias: ["Python", "R", "SQL", "Power BI"]
        )

        Candidato p4 = new Candidato(
                nome: "Thiago Lima",
                email: "thiago@gmail.com",
                idade: 28,
                cpf: "333.333.333-04",
                estado: "Paraná",
                cep: "33333-004",
                descricao: "Desenvolvedor full stack apaixonado por tecnologias open-source e cloud computing.",
                competencias: ["Java", "Spring Boot", "Angular", "AWS", "Docker"]
        )

        Candidato p5 = new Candidato(
                nome: "Beatriz Santos",
                email: "beatriz@gmail.com",
                idade: 21,
                cpf: "444.444.444-05",
                estado: "Bahia",
                cep: "44444-005",
                descricao: "Estudante de Sistemas de Informação, interessada em desenvolvimento mobile e UX/UI.",
                competencias: ["Flutter", "Dart", "Figma", "Git"]
        )

        candidatos.addAll(p1, p2, p3, p4, p5)
    }

    static cadastrarCandidato() {
        print "Digite o nome: "
        def nome = sc.nextLine()
        
        print "Digite a idade: "
        def idade = sc.nextInt()

        sc.nextLine()
        print "Digite o email: "
        def email = sc.nextLine()
        
        print "Digite o cpf: "
        def cpf = sc.nextLine()
        
        print "Digite o estado: "
        def estado = sc.nextLine()
        
        print "Digite o cep: "
        def cep = sc.nextLine()

        print "Digite uma descricao: "
        def descricao = sc.nextLine()

        print "Digite suas competencias (competencia1, contencia2,...): "
        def competencias = sc.nextLine()
        
        return new Candidato(nome: nome, idade: idade, cpf: cpf, email: email, cep: cep, estado: estado, descricao: descricao, competencias: competencias.split(","))
    }

    static cadastrarEmpresa() {
        print "Digite o nome: "
        def nome = sc.nextLine()

        print "Digite o email: "
        def email = sc.nextLine()

        print "Digite o cnpj: "
        def cnpj = sc.nextLine()

        print "Digite o estado: "
        def estado = sc.nextLine()
        
        print "Digite o pais: "
        def pais = sc.nextLine()

        print "Digite o cep: "
        def cep = sc.nextLine()

        print "Digite uma descricao: "
        def descricao = sc.nextLine()

        print "Digite as competencias (competencia1, contencia2,...): "
        def competencias = sc.nextLine()

        return new Empresa(nome: nome, email: email,cnpj: cnpj, cep: cep, estado: estado, pais: pais, descricao: descricao, competencias: competencias.split(","))
    }

}
