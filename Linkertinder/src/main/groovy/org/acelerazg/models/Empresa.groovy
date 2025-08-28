package org.acelerazg.models

class Empresa extends Pessoa {

    String cnpj
    String pais

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
        return "\n\nEmpresa: " + super.toString() + "\nCNPF: " + cnpj;
    }
}
