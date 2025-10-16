package org.acelerazg.services

import org.acelerazg.models.Endereco
import org.acelerazg.repositories.EnderecoRepository
import org.acelerazg.utils.Utils

class EnderecoService {

    EnderecoRepository enderecoRepository

    EnderecoService() {
        this.enderecoRepository = new EnderecoRepository()
    }

    String encontrarEnderecoPorID(String id) {
        return enderecoRepository.findEnderecoFromId(id)
    }

    String find(Endereco adress) {
        String result = enderecoRepository.findIdFromEndereco(adress)
        if (!result) {
            String id = Utils.generateUUID()
            adress.id = id
            enderecoRepository.createNewEndereco(adress)
            return id
        }
        return result
    }
}
