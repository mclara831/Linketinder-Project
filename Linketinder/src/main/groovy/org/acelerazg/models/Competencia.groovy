package org.acelerazg.models

class Competencia {
    String id
    String nome

    Competencia(String id, String nome) {
        this.id = id
        this.nome = nome
    }


    @Override
    String toString() {
        return "nome = " + nome
    }
}
