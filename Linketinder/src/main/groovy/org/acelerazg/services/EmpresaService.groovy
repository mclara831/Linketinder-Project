package org.acelerazg.services

import org.acelerazg.models.Empresa
import org.acelerazg.repositories.EmpresaRepository

class EmpresaService {

    EmpresaRepository repository
    EnderecoService enderecoService
    CompetenciaService competenciaService

    EmpresaService() {
        this.repository = new EmpresaRepository()
        this.enderecoService = new EnderecoService()
        this.competenciaService = new CompetenciaService()
    }

    List<Empresa> findAll() {
        return repository.findAll()
    }

    Empresa encontrarEmpresaPorId(String empresaId) {
        return repository.findEmpresaById(empresaId)
    }

    void inserirNovaEmpresa(String nome, String email, String linkedin, String cnpj, String descricao, String senha,
                            String pais, String estado, String cep, String competencias) {
        Empresa e = repository.findByCnpj(cnpj)
        if (!e) {
            String enderecoId = enderecoService.encontrarEndereco(pais, estado, cep)
            repository.createNewEmpresa(new Empresa(nome, email, linkedin, enderecoId, descricao, senha, cnpj))
            e = repository.findByCnpj(cnpj)
            competenciaService.adicionarCompetenciasAEmpresa(e.id, competencias)
        } else {
            println "[AVISO]: Não é possível utilizar o cnpj fornecido!"
        }
    }

    boolean cnpjValido(String cnpj) {
        Empresa e = repository.findByCnpj(cnpj)
        return e != null
    }

    void atualizarEmpresaPorCnpj(String cnpj, String nome, String email, String linkedin, String descricao, String senha,
                                 String pais, String estado, String cep, String competencias) {
        Empresa e = repository.findByCnpj(cnpj)
        if (e) {
            String enderecoId = enderecoService.encontrarEndereco(pais, estado, cep)
            e.setNome(nome)
            e.setEmail(email)
            e.setLinkedin(linkedin)
            e.setDescricao(descricao)
            e.setSenha(senha)
            e.setEnderecoId(enderecoId)

            competenciaService.removeCompetenciasDaEmpresa(e.id)
            competenciaService.adicionarCompetenciasAEmpresa(e.id, competencias)

            repository.updateEmpresaById(e)
        } else {
            println "[AVISO]: Este CPF não está cadastrado em nossa base de dados!"
        }
    }

    void deletarEmpresaPorCnpj(String cnpj) {
        Empresa e = repository.findByCnpj(cnpj)
        if (e) {
            competenciaService.removeCompetenciasDaEmpresa(e.id)
            repository.deleteByCnpj(cnpj)
        } else {
            println "[AVISO]: Este CPF não está cadastrado em nossa base de dados!"
        }
    }
}
