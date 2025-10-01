package org.acelerazg.repositories

import groovy.sql.GroovyRowResult
import org.acelerazg.models.Competencia
import org.acelerazg.repositories.db.DatabaseConnection

class CompetenciaRepository {
    DatabaseConnection sql

    CompetenciaRepository() {
        sql = new DatabaseConnection()
    }

    List<Competencia> findAll() {
        List<Competencia> competencias = new ArrayList<>()

        sql.getConnection().eachRow("SELECT * FROM competencias") { rs ->
            competencias.add(new Competencia(rs.id, rs.nome))
        }

        sql.closeConnection()
        return competencias
    }

    void createNewCompetencias(String nome) {
        sql.getConnection()
                .execute("""INSERT INTO competencias(nome) VALUES (?)""",
                        [nome])
    }

    String findCompetenciaIdByNome(String nome) {
        GroovyRowResult rs = sql.getConnection()
                .firstRow("""SELECT * FROM competencias where nome=?""",
                        [nome])
        if (rs != null) {
            return rs.id
        }
        return null
    }

    String findCompetenciaIdById(String id) {
        GroovyRowResult rs = sql.getConnection()
                .firstRow("""SELECT * FROM competencias where id=?""",
                        [id])
        if (rs != null) {
            return rs.nome
        }
        return null
    }

    List<String> findCompetenciasByCandidatoId(String candidatoId) {
        List<String> competencias = new ArrayList<>()
        sql.getConnection().eachRow("SELECT * FROM candidatos_competencias WHERE candidato_id=?", [candidatoId]) { rs ->
            String comp = findCompetenciaIdById(rs.competencia_id)
            competencias.add(comp)
        }
        return competencias
    }

    void addCompetenciasToCandidato(String candidatoId, String competenciaId) {
        sql.getConnection()
                .execute("""INSERT INTO candidatos_competencias(candidato_id, competencia_id) VALUES (?, ?)""",
                        [candidatoId, competenciaId])
    }

    void removeCompetenciasFromCandidato(String candidatoId) {
        sql.getConnection()
                .execute("""DELETE FROM candidatos_competencias WHERE candidato_id=?""",
                        [candidatoId])
    }

    List<String> findCompetenciasByEmpresaId(String empresaId) {
        List<String> competencias = new ArrayList<>()
        sql.getConnection().eachRow("SELECT * FROM empresas_competencias WHERE empresa_id=?", [empresaId]) { rs ->
            String comp = findCompetenciaIdById(rs.competencia_id)
            competencias.add(comp)
        }
        return competencias
    }

    void addCompetenciasToEmpresa(String empresaId, String competenciaId) {
        sql.getConnection()
                .execute("""INSERT INTO empresas_competencias(empresa_id, competencia_id) VALUES (?, ?)""",
                        [empresaId, competenciaId])
    }

    void removeCompetenciasFromEmpresa(String empresaId) {
        sql.getConnection()
                .execute("""DELETE FROM empresas_competencias WHERE empresa_id=?""",
                        [empresaId])
    }

}
