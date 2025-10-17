package org.acelerazg.services

import org.acelerazg.models.Company
import org.acelerazg.models.Vaga
import org.acelerazg.repositories.CompanyRepository
import org.acelerazg.repositories.VagaRepository

class VagaService {

    VagaRepository repository
    EnderecoService enderecoService
    SkillService competenciaService
    CompanyRepository empresaRepository

    VagaService() {
        this.repository = new VagaRepository()
        this.enderecoService = new EnderecoService()
        this.competenciaService = new SkillService()
        this.empresaRepository = new CompanyRepository()
    }

    List<Vaga> listarTodasVagas() {
        return repository.findAll()
    }

    void inserirNovaVaga(String nome, String descricao, String pais, String estado, String cep, String competencias, String cnpj) {
        String enderecoId = enderecoService.encontrarEndereco(pais, estado, cep)
        Company e = empresaRepository.findByCnpj(cnpj)
        String idVaga = UUID.randomUUID().toString()
        repository.createNewVaga(new Vaga(idVaga, nome, descricao, enderecoId, e.id))
        competenciaService.addSkillsToJob(idVaga, competencias)
    }

    void atualizarVagaPorId(Vaga v, String nome, String descricao, String pais, String estado, String cep, String competencias) {
        String enderecoId = enderecoService.encontrarEndereco(pais, estado, cep)
        v.setNome(nome)
        v.setDescricao(descricao)
        v.setEnderecoId(enderecoId)
        competenciaService.removeSkillsFromJob(v.id)
        competenciaService.addSkillsToJob(v.id, competencias)

        repository.updateVagaById(v)
    }

    void deletarVagaPorId(String id) {
        competenciaService.removeSkillsFromJob(id)
        repository.deleteById(id)
    }

    List<Vaga> buscarVagasDeEmpresa(String cnpj) {
        Company e = empresaRepository.findByCnpj(cnpj)
        return repository.findVagasByEmpresaId(e.id)
    }
}
