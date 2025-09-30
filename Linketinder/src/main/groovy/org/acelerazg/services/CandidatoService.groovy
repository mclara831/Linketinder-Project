package org.acelerazg.services

import org.acelerazg.models.Candidato
import org.acelerazg.repositories.CandidatoRepository

import java.time.LocalDate

class CandidatoService {

    CandidatoRepository repository
    EnderecoService enderecoService

    CandidatoService() {
        this.repository = new CandidatoRepository()
        this.enderecoService = new EnderecoService()
    }

    List<Candidato> findAll() {
        return repository.findAll()
    }

    void inserirNovoCandidato(String nome, String sobrenome, String email, String linkedin, String cpf, LocalDate dataNascimento, String descricao, String senha,
                              String pais, String estado, String cep) {
        Candidato c = repository.findByCpf(cpf)
        if (!c) {
            String enderecoId = enderecoService.encontrarEndereco(pais, estado, cep)
            repository.createNewCandidato(new Candidato(nome, sobrenome, email, linkedin, cpf, dataNascimento, enderecoId, descricao, senha))
        } else {
            println "[AVISO]: Não é possível utilizar o cpf fornecido!"
        }
    }

    boolean cpfValido(String cpf) {
        Candidato c = repository.findByCpf(cpf)
        return c != null
    }

    void atualizarCandidatoPorCpf(String cpf, String nome, String sobrenome, String email, String linkedin, LocalDate dataNascimento, String descricao, String senha,
                                  String pais, String estado, String cep) {
        Candidato c = repository.findByCpf(cpf)
        if (c) {
            String enderecoId = enderecoService.encontrarEndereco(pais, estado, cep)
            c.setNome(nome)
            c.setSobrenome(sobrenome)
            c.setEmail(email)
            c.setLinkedin(linkedin)
            c.setDataNascimento(dataNascimento)
            c.setDescricao(descricao)
            c.setSenha(senha)
            c.setEnderecoId(enderecoId)
            repository.updateCandidatoById(c)
        } else {
            println "[AVISO]: Este CPF não está cadastrado em nossa base de dados!"
        }
    }

    void deletarCandidatoPorCpf(String cpf) {
        Candidato c = repository.findByCpf(cpf)
        if (c) {
            repository.deleteByCpf(cpf)
        } else {
            println "[AVISO]: Este CPF não está cadastrado em nossa base de dados!"
        }
    }
}
