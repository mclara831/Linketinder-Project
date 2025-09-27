package org.acelerazg.cli

import org.acelerazg.models.Candidato
import org.acelerazg.models.Empresa

class UI {

    Scanner sc

    UI() {
        this.sc = new Scanner(System.in)
    }

    void menu() {
        println "\n\n========================= MENU ============================="
        println "1. Listar todas empresas"
        println "2. Cadastrar nova empresa"
        println "3. Listar todos candidatos"
        println "4. Cadastrar novo candidato"
        println "0. Sair"
        println "============================================================"
        print "Escolha uma opcao: "
    }

    Candidato cadastrarCandidato() {
        print "Digite o nome: "
        def nome = this.sc.nextLine()

        print "Digite a idade: "
        def idade = this.sc.nextInt()

        this.sc.nextLine()
        print "Digite o email: "
        def email = this.sc.nextLine()

        print "Digite o cpf: "
        def cpf = this.sc.nextLine()

        print "Digite o estado: "
        def estado = this.sc.nextLine()

        print "Digite o cep: "
        def cep = this.sc.nextLine()

        print "Digite uma descrição: "
        def descricao = this.sc.nextLine()

        print "Digite suas competencias (competencia1, contencia2,...): "
        def competencias = this.sc.nextLine()

        return new Candidato(nome: nome, idade: idade, cpf: cpf, email: email, cep: cep, estado: estado, descricao: descricao, competencias: competencias.split(","))
    }

    Empresa cadastrarEmpresa() {
        print "Digite o nome: "
        def nome = this.sc.nextLine()

        print "Digite o email: "
        def email = this.sc.nextLine()

        print "Digite o cnpj: "
        def cnpj = this.sc.nextLine()

        print "Digite o estado: "
        def estado = this.sc.nextLine()

        print "Digite o pais: "
        def pais = this.sc.nextLine()

        print "Digite o cep: "
        def cep = this.sc.nextLine()

        print "Digite uma descrição: "
        def descricao = this.sc.nextLine()

        print "Digite as competencias (competencia1, contencia2,...): "
        def competencias = this.sc.nextLine()

        return new Empresa(nome: nome, email: email, cnpj: cnpj, cep: cep, estado: estado, pais: pais, descricao: descricao, competencias: competencias.split(","))
    }
}
