package org.acelerazg.repositories

import groovy.sql.GroovyRowResult
import org.acelerazg.models.Skill
import org.acelerazg.repositories.db.DatabaseConnection

class SkillRepository {
    DatabaseConnection sql

    SkillRepository() {
        sql = new DatabaseConnection()
    }

    List<Skill> findAll() {
        List<Skill> skills = new ArrayList<>()
        try {
            sql.getConnection().eachRow("SELECT * FROM competencias") { rs ->
                skills.add(new Skill(rs.id, rs.nome))
            }
            return skills
        } catch (Exception e) {
            println("[ERROR]: It wasn't possible to list all skills.")
        } finally {
            sql.getConnection().close()
        }
        return []
    }

    Skill createByName(String name) {
        String query = "INSERT INTO competencias(nome) VALUES (?)"
        Skill result = null
        try {
            sql.getConnection().execute(query, [name])
            result = findByName(name)
        } catch (Exception e) {
            println("[ERROR]: It wasn't possible to insert skill ${name}.")
        } finally {
            sql.getConnection().close()
        }
        return result
    }

    void createFullSkill(String id, String name) {
        String query = "INSERT INTO competencias(id, nome) VALUES (?, ?)"
        try {
            sql.getConnection().execute(query, [id, name])
        } catch (Exception e) {
            println("[ERROR]: It wasn't possible to insert skill ${name}.")
        } finally {
            sql.getConnection().close()
        }
    }

    Skill findByName(String name) {
        String query = "SELECT * FROM competencias where nome=?"
        try {
            GroovyRowResult rs = sql.getConnection().firstRow(query, [name])
            if (rs != null) {
                return mapRowToSkill(rs)
            }
        } catch (Exception e) {
            println("[ERROR]: It wasn't possible to find skill ${name}.")
        } finally {
            sql.getConnection().close()
        }
        return null
    }

    Skill findCompetenciaById(String id) {
        String query = "SELECT * FROM competencias where id=?"
        try {
            GroovyRowResult rs = sql.getConnection().firstRow(query, [id])
            if (rs != null) {
                return mapRowToSkill(rs)
            }
        } catch (Exception e) {
            println("[ERROR]: It wasn't possible to find skill ${id}.")
        } finally {
            sql.getConnection().close()
        }
        return null
    }

    Skill updateCompetenciaById(Skill skill) {
        String query = "UPDATE competencias SET nome=? WHERE id=?"
        Skill result = null
        try {
            sql.getConnection().executeInsert(query, [skill.name, skill.id])
            result = findByName(skill.name)
        } catch (Exception e) {
            println("[ERROR]: It wasn't possible to update skill ${skill.name}.")
        } finally {
            sql.getConnection().close()
        }
        return result
    }

    void deleteCompetenciaById(Skill skill) {
        String query = "DELETE from competencias WHERE id=?"
        try {
            sql.getConnection().executeInsert(query, [skill.id])
        } catch (Exception e) {
            println("[ERROR]: It wasn't possible to update skill ${skill.name}.")
        } finally {
            sql.getConnection().close()
        }
    }

    List<String> findSkillsByCandidate(String candidateId) {
        String query = "SELECT * FROM candidatos_competencias WHERE candidato_id=?"

        List<String> skills = new ArrayList<>()
        try {
            sql.getConnection().eachRow(query, [candidateId]) { rs ->
                Skill skill = findCompetenciaById(rs.competencia_id)
                skills.add(skill.name)
            }
        } catch (Exception e) {
            println("[ERROR]: It wasn't possible to skills from skill candidate.")
        } finally {
            sql.getConnection().close()
        }
        return skills
    }

    void addSkillsToCandidate(String candidateId, String skillId) {
        String query = "INSERT INTO candidatos_competencias(candidato_id, competencia_id) VALUES (?, ?)"
        try {
            sql.getConnection().execute(query, [candidateId, skillId])
        } catch (Exception e) {
            println("[ERROR]: It wasn't possible to add skill to candidate.")
        } finally {
            sql.getConnection().close()
        }
    }

    void removeSkillsFromCandidate(String candidateId) {
        String query = "DELETE FROM candidatos_competencias WHERE candidato_id=?"
        try {
            sql.getConnection().execute(query, [candidateId])
        } catch (Exception e) {
            println("[ERROR]: It wasn't possible to remove skills from candidate.")
        } finally {
            sql.getConnection().close()
        }
    }

    List<String> findSkillsByCompany(String companyId) {
        String query = "SELECT * FROM empresas_competencias WHERE empresa_id=?"

        List<String> skills = new ArrayList<>()
        try {
            sql.getConnection().eachRow(query, [companyId]) { rs ->
                Skill skill = findCompetenciaById(rs.competencia_id)
                skills.add(skill.name)
            }
        } catch (Exception e) {
            println("[ERROR]: It wasn't possible to find skills from company.")
        } finally {
            sql.getConnection().close()
        }
        return skills
    }

    void addSkillsToCompany(String companyId, String skillId) {
        String query = "INSERT INTO empresas_competencias(empresa_id, competencia_id) VALUES (?, ?)"
        try {
            sql.getConnection().execute(query, [companyId, skillId])
        } catch (Exception e) {
            println("[ERROR]: It wasn't possible to add skills to company.")
        } finally {
            sql.getConnection().close()
        }
    }

    void removeSkillsFromCompany(String companyId) {
        String query = "DELETE FROM empresas_competencias WHERE empresa_id=?"
        try {
            sql.getConnection().execute(query, [companyId])
        } catch (Exception e) {
            println("[ERROR]: It wasn't possible to remove skills from company.")
        } finally {
            sql.getConnection().close()
        }
    }

    List<String> findSkillsByJob(String jobId) {
        List<String> skils = new ArrayList<>()
        String query = "SELECT * FROM vagas_competencias WHERE vaga_id=?"
        try {
            sql.getConnection().eachRow(query, [jobId]) { rs ->
                Skill comp = findCompetenciaById(rs.competencia_id)
                skils.add(comp.name)
            }
        } catch (Exception e) {
            println("[ERROR]: It wasn't possible to find skills from job.")
        } finally {
            sql.getConnection().close()
        }
        return skils
    }

    void addSkillsToJob(String jobId, String skillId) {
        String query = "INSERT INTO vagas_competencias(vaga_id, competencia_id) VALUES (?, ?)"
        try {
            sql.getConnection().execute(query, [jobId, skillId])
        } catch (Exception e) {
            println("[ERROR]: It wasn't possible to add skills to job.")
        } finally {
            sql.getConnection().close()
        }
    }

    void removeSkillsFromJob(String jobId) {
        String query = "DELETE FROM vagas_competencias WHERE vaga_id=?"
        try {
            sql.getConnection().execute(query, [jobId])
        } catch (Exception e) {
            println("[ERROR]: It wasn't possible to remove skills from company.")
        } finally {
            sql.getConnection().close()
        }
    }

    private Skill mapRowToSkill(Object rs) {
        return new Skill(rs.id, rs.nome)
    }

}
