package org.acelerazg.models

import groovy.transform.ToString

@ToString
class Candidato extends Pessoa {

    String cpf
    Integer idade

    @Override
    def salvarInformacoes() {
        return null
    }

    @Override
    def listarCompetencias() {
        return null
    }


    @Override
    public String toString() {
        return "\n\nCandidato: " +  super.toString() +
                "\nCPF=" + cpf +
                "\nidade=" + idade
    }
}