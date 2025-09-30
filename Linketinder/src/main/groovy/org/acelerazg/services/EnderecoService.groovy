package org.acelerazg.services

import org.acelerazg.models.Endereco
import org.acelerazg.repositories.EnderecoRepository

class EnderecoService {

    EnderecoRepository enderecoRepository

    EnderecoService() {
        this.enderecoRepository = new EnderecoRepository()
    }

    String encontrarEnderecoPorID(String id) {
        return enderecoRepository.findEnderecoFromId(id)
    }

    String encontrarEndereco(String pais, String estado, String cep) {
        Endereco e = new Endereco(pais, estado, cep)
        String resultado = enderecoRepository.findIdFromEndereco(e)
        if (!resultado) {
            enderecoRepository.createNewEndereco(e)
            resultado = enderecoRepository.findIdFromEndereco(e)
        }
        return resultado
    }
}
