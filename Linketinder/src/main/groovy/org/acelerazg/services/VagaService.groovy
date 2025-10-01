package org.acelerazg.services

import org.acelerazg.models.Empresa
import org.acelerazg.models.Vaga
import org.acelerazg.repositories.EmpresaRepository
import org.acelerazg.repositories.VagaRepository

class VagaService {

    VagaRepository repository
    EnderecoService enderecoService
    CompetenciaService competenciaService
    EmpresaRepository empresaRepository

    VagaService() {
        this.repository = new VagaRepository()
        this.enderecoService = new EnderecoService()
        this.competenciaService = new CompetenciaService()
        this.empresaRepository = new EmpresaRepository()
    }

    List<Vaga> listarTodasVagas() {
        return repository.findAll()
    }

    void inserirNovaVaga(String nome, String descricao, String pais, String estado, String cep, String competencias, String cnpj) {
        String enderecoId = enderecoService.encontrarEndereco(pais, estado, cep)
        Empresa e = empresaRepository.findByCnpj(cnpj)
        String idVaga = UUID.randomUUID().toString()
        repository.createNewVaga(new Vaga(idVaga, nome, descricao, enderecoId, e.id))
        competenciaService.adicionarCompetenciasAVaga(idVaga, competencias)
    }

    void atualizarVagaPorId(Vaga v, String nome, String descricao, String pais, String estado, String cep, String competencias) {
        String enderecoId = enderecoService.encontrarEndereco(pais, estado, cep)
        v.setNome(nome)
        v.setDescricao(descricao)
        v.setEnderecoId(enderecoId)
        competenciaService.removeCompetenciasDaVaga(v.id)
        competenciaService.adicionarCompetenciasAVaga(v.id, competencias)

        repository.updateVagaById(v)
    }

    void deletarVagaPorId(String id) {
        competenciaService.removeCompetenciasDaVaga(id)
        repository.deleteById(id)
    }
}
