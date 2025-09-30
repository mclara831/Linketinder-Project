package org.acelerazg.models

abstract class Pessoa {

    String id
    String nome
    String email
    String linkedin
    String enderecoId
    String descricao
    String senha

    Pessoa(String id, String nome, String email, String linkedin, String enderecoId, String descricao, String senha) {
        this.id = id
        this.nome = nome
        this.email = email
        this.linkedin = linkedin
        this.enderecoId = enderecoId
        this.descricao = descricao
        this.senha = senha
    }

    Pessoa(String nome, String email, String linkedin, String enderecoId, String descricao, String senha) {
        this.nome = nome
        this.email = email
        this.linkedin = linkedin
        this.enderecoId = enderecoId
        this.descricao = descricao
        this.senha = senha
    }


    @Override
    String toString() {
        return "Pessoa{" +
                "id='" + id + '\'' +
                ", nome='" + nome + '\'' +
                ", email='" + email + '\'' +
                ", linkedin='" + linkedin + '\'' +
                ", enderecoId='" + enderecoId + '\'' +
                ", descricao='" + descricao + '\'' +
                ", senha='" + senha + '\'' +
                '}';
    }
}

