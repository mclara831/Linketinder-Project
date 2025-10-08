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
        return competencias
    }

    void createNewCompetencias(String nome) {
        sql.getConnection().execute("""INSERT INTO competencias(nome) VALUES (?)""", [nome])
    }

    void createNewCompetenciaComplete(String id, String nome) {
        sql.getConnection().execute("""INSERT INTO competencias(id, nome) VALUES (?, ?)""", [id, nome])
    }

    Competencia findCompetenciaIdByNome(String nome) {
        GroovyRowResult rs = sql.getConnection().firstRow("""SELECT * FROM competencias where nome=?""", [nome])
        if (rs != null) {
            return new Competencia(rs.id, rs.nome)
        }
        return null
    }

    Competencia findCompetenciaById(String id) {
        GroovyRowResult rs = sql.getConnection().firstRow("""SELECT * FROM competencias where id=?""", [id])
        if (rs != null) {
            return new Competencia(rs.id, rs.nome)
        }
        return null
    }

    void updateCompetenciaById(Competencia competencia) {
        sql.getConnection().executeInsert(""" UPDATE competencias SET nome=? WHERE id=?""", [competencia.nome, competencia.id])
    }

    void deleteCompetenciaById(Competencia competencia) {
        sql.getConnection().executeInsert(""" DELETE from competencias WHERE id=?""", [competencia.id])
    }

    List<String> findCompetenciasByCandidatoId(String candidatoId) {
        List<String> competencias = new ArrayList<>()
        sql.getConnection().eachRow("SELECT * FROM candidatos_competencias WHERE candidato_id=?", [candidatoId]) { rs ->
            Competencia comp = findCompetenciaById(rs.competencia_id)
            competencias.add(comp.nome)
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
            Competencia comp = findCompetenciaById(rs.competencia_id)
            competencias.add(comp.nome)
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

    List<String> findCompetenciasByVagaId(String vagaId) {
        List<String> competencias = new ArrayList<>()
        sql.getConnection().eachRow("SELECT * FROM vagas_competencias WHERE vaga_id=?", [vagaId]) { rs ->
            Competencia comp = findCompetenciaById(rs.competencia_id)
            competencias.add(comp.nome)
        }
        return competencias
    }

    void addCompetenciasToVaga(String vagaId, String competenciaId) {
        sql.getConnection()
                .execute("""INSERT INTO vagas_competencias(vaga_id, competencia_id) VALUES (?, ?)""",
                        [vagaId, competenciaId])
    }

    void removeCompetenciasFromVaga(String vagaId) {
        sql.getConnection()
                .execute("""DELETE FROM vagas_competencias WHERE vaga_id=?""",
                        [vagaId])
    }

}
