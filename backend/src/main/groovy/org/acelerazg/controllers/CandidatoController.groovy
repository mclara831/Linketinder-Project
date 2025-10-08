package org.acelerazg.controllers

import org.acelerazg.cli.UI
import org.acelerazg.models.Candidato
import org.acelerazg.services.CandidatoService
import org.acelerazg.services.CompetenciaService
import org.acelerazg.services.EnderecoService

import java.time.LocalDate

class CandidatoController {

    CandidatoService service
    EnderecoService enderecoService
    CompetenciaService competenciaService
    Scanner sc

    CandidatoController() {
        this.service = new CandidatoService()
        this.enderecoService = new EnderecoService()
        this.competenciaService = new CompetenciaService()
        sc = new Scanner(System.in)
    }

    void listarTodosCandidatos() {
        List<Candidato> candidatos = service.findAll()
        candidatos.each { it -> {
            println(it.toString())
            print( enderecoService.encontrarEnderecoPorID(it.enderecoId).toString())
            println( "\n\tCompetencias: " + competenciaService.buscaCompetenciasDoCandidatos(it.id).join(", "))
        }}
    }

    void cadastrarCandidato() {
        print "Digite o nome: "
        String nome = this.sc.nextLine()

        print "Digite o sobrenome: "
        String sobrenome = this.sc.nextLine()

        this.sc.nextLine()
        print "Digite o email: "
        String email = this.sc.nextLine()

        this.sc.nextLine()
        print "Digite o linkedin: "
        String linkedin = this.sc.nextLine()

        print "Digite o cpf: "
        String cpf = this.sc.nextLine()

        if(service.cpfValido(cpf)) {
            println "[AVISO]: Este CPF não pode ser utilizado!"
            return
        }

        print "Digite a data de nascimento: "
        LocalDate dataNascimento = UI.lerData()

        print "Digite o pais: "
        String pais = this.sc.nextLine()

        print "Digite o estado: "
        String estado = this.sc.nextLine()

        print "Digite o cep: "
        String cep = this.sc.nextLine()

        print "Digite uma descrição: "
        String descricao = this.sc.nextLine()

        print "Digite uma senha: "
        String senha = this.sc.nextLine()

        print "Digite suas competencias (competencia1, conpetencia2,...): "
        String competencias = sc.nextLine()

        service.inserirNovoCandidato(nome, sobrenome, email, linkedin, cpf, dataNascimento as LocalDate, descricao, senha, pais, estado, cep, competencias);
    }

    void atualizarCandidato() {
        print "Digite o cpf do candidato: "
        String cpf = this.sc.nextLine()

        if(!service.cpfValido(cpf)) {
            println "[AVISO]: Este CPF não está cadastrado em nossa base de dados!"
            return
        }

        print "Digite o nome: "
        String nome = this.sc.nextLine()

        print "Digite o sobrenome: "
        String sobrenome = this.sc.nextLine()

        this.sc.nextLine()
        print "Digite o email: "
        String email = this.sc.nextLine()

        this.sc.nextLine()
        print "Digite o linkedin: "
        String linkedin = this.sc.nextLine()

        print "Digite a data de nascimento: "
        LocalDate dataNascimento = UI.lerData()

        print "Digite o pais: "
        String pais = this.sc.nextLine()

        print "Digite o estado: "
        String estado = this.sc.nextLine()

        print "Digite o cep: "
        String cep = this.sc.nextLine()

        print "Digite uma descrição: "
        String descricao = this.sc.nextLine()

        print "Digite uma senha: "
        String senha = this.sc.nextLine()

        print "Digite suas competencias (competencia1, conpetencia2,...): "
        String competencias = sc.nextLine()

        service.atualizarCandidatoPorCpf(cpf, nome, sobrenome, email, linkedin, dataNascimento as LocalDate, descricao, senha, pais, estado, cep, competencias);
    }

    void deletarCandidato() {
        print "Digite o cpf do candidato: "
        String cpf = this.sc.nextLine()

        service.deletarCandidatoPorCpf(cpf)
    }
}
