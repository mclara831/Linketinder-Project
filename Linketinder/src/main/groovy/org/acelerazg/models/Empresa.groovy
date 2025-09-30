package org.acelerazg.models

class Empresa extends Pessoa {

    String cnpj

    Empresa(UUID id, String nome, String email, String linkedin, String enderecoId, String descricao, String senha, String cnpj) {
        super(id, nome, email, linkedin, enderecoId, descricao, senha)
        this.cnpj = cnpj
    }

    @Override
    public String toString() {
        return "\n\nEmpresa: " + super.toString() + "\nCNPF: " + cnpj;
    }
}
