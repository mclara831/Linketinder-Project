package org.acelerazg.models

import groovy.transform.ToString

import java.time.LocalDate

@ToString
class Candidato extends Pessoa {

    String sobrenome
    LocalDate dataNascimento
    String cpf

    Candidato(String id, String nome, String sobrenome, String email, String linkedin, String cpf, LocalDate dataNascimento, String enderecoId, String descricao, String senha) {
        super(id, nome, email, linkedin, enderecoId, descricao, senha)
        this.sobrenome = sobrenome
        this.dataNascimento = dataNascimento
        this.cpf = cpf
    }

    Candidato(String nome, String sobrenome, String email, String linkedin, String cpf, LocalDate dataNascimento, String enderecoId, String descricao, String senha) {
        super(nome, email, linkedin, enderecoId, descricao, senha)
        this.sobrenome = sobrenome
        this.dataNascimento = dataNascimento
        this.cpf = cpf
    }

    @Override
    String toString() {
        return "\n\nCandidato:\n" +
                "\tnome = " + nome +
                " " + sobrenome +
                "\n\temail = " + email +
                "\n\tcpf = " + cpf +
                "\n\tdata de nascimento = " + dataNascimento +
                "\n\tlinkedin = " + linkedin +
                "\n\tdescricao = " + descricao
    }
}