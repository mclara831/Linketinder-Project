package org.acelerazg.models

class Endereco {
    String id
    String pais
    String estado
    String cep

    Endereco(String id, String pais, String estado, String cep) {
        this.id = id
        this.pais = pais
        this.estado = estado
        this.cep = cep
    }

    Endereco(String pais, String estado, String cep) {
        this.id = id
        this.pais = pais
        this.estado = estado
        this.cep = cep
    }

    @Override
    String toString() {
        return "\tEndereco: " + cep + ", " + estado + ", " + pais
    }
}
