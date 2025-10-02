package org.acelerazg.models

import java.time.LocalDate

class Vaga {
    String id
    String nome
    String descricao
    LocalDate criadaEm
    String enderecoId
    String empresaId

    Vaga(String id, String nome, String descricao, LocalDate criadaEm, String enderecoId, String empresaId) {
        this.id = id
        this.nome = nome
        this.descricao = descricao
        this.criadaEm = criadaEm
        this.enderecoId = enderecoId
        this.empresaId = empresaId
    }

    Vaga(String id, String nome, String descricao, String enderecoId, String empresaId) {
        this.id = id
        this.nome = nome
        this.descricao = descricao
        this.enderecoId = enderecoId
        this.empresaId = empresaId
    }

    Vaga(String nome, String descricao, String enderecoId, String empresaId) {
        this.id = id
        this.nome = nome
        this.descricao = descricao
        this.criadaEm = criadaEm
        this.enderecoId = enderecoId
        this.empresaId = empresaId
    }


    @Override
    String toString() {
        return "Vaga: " +
                "\n\tnome=" + nome +
                "\n\tdescricao=" + descricao +
                "\n\tcriada em=" + criadaEm
    }
}
