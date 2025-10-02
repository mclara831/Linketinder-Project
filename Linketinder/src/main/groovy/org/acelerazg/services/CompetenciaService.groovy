package org.acelerazg.services

import org.acelerazg.models.Competencia
import org.acelerazg.repositories.CompetenciaRepository

class CompetenciaService {

    CompetenciaRepository repository

    CompetenciaService() {
        this.repository = new CompetenciaRepository()
    }

    List<Competencia> listarTodasCompetencias() {
        return repository.findAll()
    }

    void cadastrarNovaCompetencia(String competencia) {
        competencia = tratarCompetencia(competencia)
        Competencia c = repository.findCompetenciaIdByNome(competencia)
        if (!c) {
            repository.createNewCompetencias(competencia)
        } else {
            println("[AVISO]: Esta competência " + competencia.toUpperCase() + " já existe em nossa base de dados!")
        }
    }

    void cadastrarListaCompetencias(String listaCompetencias) {
        List<String> comps = listaCompetencias.split(", ")
        comps.forEach { it -> cadastrarNovaCompetencia(it) }
    }

    boolean existeCompetencia(String comp) {
        comp = tratarCompetencia(comp)
        Competencia c = repository.findCompetenciaIdByNome(comp)
        return c != null
    }

    void atualizarCompetencia(String compAntiga, String compNova) {
        compNova = tratarCompetencia(compNova)
        compAntiga = tratarCompetencia(compAntiga)
        if (existeCompetencia(compNova)) {
            println("[AVISO]: Essa competência já existe em nossa base de dados!")
            return
        }
        Competencia c = repository.findCompetenciaIdByNome(compAntiga)
        c.nome = compNova
        repository.updateCompetenciaById(c)
    }

    void deletarCompetencia(String comp) {
        comp = tratarCompetencia(comp)
        Competencia c = repository.findCompetenciaIdByNome(comp)
        repository.deleteCompetenciaById(c)
    }


    List<String> buscaCompetenciasDoCandidatos(String candidatoId) {
        return repository.findCompetenciasByCandidatoId(candidatoId)
    }

    void adicionarCompetenciasACandidato(String candidatoId, String competencias) {
        List<String> comps = competencias.split(", ")
        comps.forEach { it ->
            it = tratarCompetencia(it)
            Competencia comp = repository.findCompetenciaIdByNome(it)
            if (comp == null) {
                comp = new Competencia(UUID.randomUUID().toString(), it)
                repository.createNewCompetenciaComplete(comp.id, comp.nome)
            }
            repository.addCompetenciasToCandidato(candidatoId, comp.id)
        }
    }

    void removeCompetenciasDoCandidato(String candidatoId) {
        repository.removeCompetenciasFromCandidato(candidatoId)
    }

    List<String> buscaCompetenciasDaEmpresa(String empresaId) {
        return repository.findCompetenciasByEmpresaId(empresaId)
    }

    void adicionarCompetenciasAEmpresa(String empresaId, String competencias) {
        List<String> comps = competencias.split(", ")
        comps.forEach { it ->
            it = tratarCompetencia(it)
            Competencia comp = repository.findCompetenciaIdByNome(it)
            if (comp == null) {
                comp = new Competencia(UUID.randomUUID().toString(), it)
                repository.createNewCompetenciaComplete(comp.id, comp.nome)
            }
            repository.addCompetenciasToEmpresa(empresaId, comp.id)
        }
    }

    void removeCompetenciasDaEmpresa(String empresaId) {
        repository.removeCompetenciasFromEmpresa(empresaId)
    }

    List<String> buscaCompetenciasDaVaga(String vagaID) {
        return repository.findCompetenciasByVagaId(vagaID)
    }

    void adicionarCompetenciasAVaga(String vagaId, String competencias) {
        List<String> comps = competencias.split(", ")
        comps.forEach { it ->
            it = tratarCompetencia(it)
            Competencia comp = repository.findCompetenciaIdByNome(it)
            if (comp == null) {
                comp = new Competencia(UUID.randomUUID().toString(), it)
                repository.createNewCompetenciaComplete(comp.id, comp.nome)
            }
            repository.addCompetenciasToVaga(vagaId, comp.id)
        }
    }

    void removeCompetenciasDaVaga(String vagaId) {
        repository.removeCompetenciasFromVaga(vagaId)
    }

    private String tratarCompetencia(String comp) {
        comp = comp.trim()
        comp = comp[0].toUpperCase() + comp.substring(1).toLowerCase()
        return comp
    }

}
