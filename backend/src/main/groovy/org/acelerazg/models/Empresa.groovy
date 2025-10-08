package org.acelerazg.models

class Empresa extends Pessoa {

    String cnpj

    Empresa(String id, String nome, String email, String linkedin, String enderecoId, String descricao, String senha, String cnpj) {
        super(id, nome, email, linkedin, enderecoId, descricao, senha)
        this.cnpj = cnpj
    }

    Empresa(String nome, String email, String linkedin, String enderecoId, String descricao, String senha, String cnpj) {
        super(nome, email, linkedin, enderecoId, descricao, senha)
        this.cnpj = cnpj
    }

    @Override
    String toString() {
        return "\nEmpresa: " + nome +
                "\n\temail = " + email +
                "\n\tlinkedin = " + linkedin +
                "\n\tcnpj = " + cnpj +
                "\n\tdescricao = " + descricao
    }
}
