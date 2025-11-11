package org.acelerazg.repositories

import org.acelerazg.models.Skill
import org.acelerazg.repositories.db.BaseRepository
import org.acelerazg.repositories.db.connection.DatabaseConnection

class SkillRepository extends BaseRepository<Skill> {

    SkillRepository(DatabaseConnection sql) {
        super(sql)
    }

    List<Skill> findAll() {
        String query = "SELECT * FROM competencias"
        return findAllRows(query)
    }

    Skill findById(String id) {
        String query = "SELECT * FROM competencias where id=?"
        findOne(query, [id])
    }

    Skill findByName(String name) {
        String query = "SELECT * FROM competencias where nome=?"
        findOne(query, [name])
    }

    Skill createByName(String name) {
        String query = "INSERT INTO competencias(nome) VALUES (?)"
        executeUpdate(query, [name])
        return findByName(name)
    }

    void createFullSkill(String id, String name) {
        String query = "INSERT INTO competencias(id, nome) VALUES (?, ?)"
        executeUpdate(query, [id, name])
    }

    Skill updateById(Skill skill) {
        String query = "UPDATE competencias SET nome=? WHERE id=?"
        executeUpdate(query, [skill.name, skill.id])
        return findByName(skill.name)
    }

    void deleteById(Skill skill) {
        String query = "DELETE from competencias WHERE id=?"
        executeDelete(query, [skill.id])
    }

    List<Skill> findSkills(String query, String entityId) {
        List<Skill> skills = []
        try {
            sql.getConnection().eachRow(query, [entityId]) { row ->
                Skill skill = findById(row.competencia_id)
                skills.add(skill)
            }
        } catch (Exception e) {
            logError("Failed to execute findAll for ${getTableName()}", e)
        } finally {
            sql.getConnection().close()
        }
        return skills
    }

    List<Skill> findSkillsByCandidate(String candidateId) {
        String query = "SELECT * FROM candidatos_competencias WHERE candidato_id=?"
        return findSkills(query, candidateId)
    }

    void addSkillsToCandidate(String candidateId, String skillId) {
        String query = "INSERT INTO candidatos_competencias(candidato_id, competencia_id) VALUES (?, ?)"
        executeUpdate(query, [candidateId, skillId])
    }

    void removeSkillsFromCandidate(String candidateId) {
        String query = "DELETE FROM candidatos_competencias WHERE candidato_id=?"
        executeDelete(query, [candidateId])
    }

    List<Skill> findSkillsByCompany(String companyId) {
        String query = "SELECT * FROM empresas_competencias WHERE empresa_id=?"
        return findSkills(query, companyId)
    }

    void addSkillsToCompany(String companyId, String skillId) {
        String query = "INSERT INTO empresas_competencias(empresa_id, competencia_id) VALUES (?, ?)"
        executeUpdate(query, [companyId, skillId])
    }

    void removeSkillsFromCompany(String companyId) {
        String query = "DELETE FROM empresas_competencias WHERE empresa_id=?"
        executeDelete(query, [companyId])
    }

    List<Skill> findSkillsByJob(String jobId) {
        String query = "SELECT * FROM vagas_competencias WHERE vaga_id=?"
        return findSkills(query, jobId)
    }

    void addSkillsToJob(String jobId, String skillId) {
        String query = "INSERT INTO vagas_competencias(vaga_id, competencia_id) VALUES (?, ?)"
        executeUpdate(query, [jobId, skillId])
    }

    void removeSkillsFromJob(String jobId) {
        String query = "DELETE FROM vagas_competencias WHERE vaga_id=?"
        executeDelete(query, [jobId])
    }

    @Override
    protected String getTableName() {
        return "competencias"
    }

    @Override
    protected Skill mapRowToEntity(Object row) {
        return new Skill(row.id, row.nome)
    }
}
