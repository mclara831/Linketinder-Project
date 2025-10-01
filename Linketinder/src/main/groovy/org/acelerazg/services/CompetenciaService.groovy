package org.acelerazg.services

import org.acelerazg.repositories.CompetenciaRepository

class CompetenciaService {

    CompetenciaRepository repository

    CompetenciaService() {
        this.repository = new CompetenciaRepository()
    }

    List<String> buscaCompetenciasDoCandidatos(String candidatoId) {
        return repository.findCompetenciasByCandidatoId(candidatoId)
    }

    void adicionarCompetenciasACandidato(String candidatoId, String competencias) {
        List<String> comps = competencias.split(", ")
        comps.forEach { it ->
            it.trim()[0].toUpperCase()
            String compId = repository.findCompetenciaIdByNome(it)
            if (compId == null) {
                repository.createNewCompetencias(it)
                compId = repository.findCompetenciaIdByNome(it)
            }
            repository.addCompetenciasToCandidato(candidatoId, compId)
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
            it = it.trim()
            it = it[0].toUpperCase() + it.substring(1).toLowerCase()
            String compId = repository.findCompetenciaIdByNome(it)
            if (compId == null) {
                repository.createNewCompetencias(it)
                compId = repository.findCompetenciaIdByNome(it)
            }
            repository.addCompetenciasToEmpresa(empresaId, compId)
        }
    }

    void removeCompetenciasDaEmpresa(String empresaId) {
        repository.removeCompetenciasFromEmpresa(empresaId)
    }

}
