package org.acelerazg.services

import org.acelerazg.models.Skill
import org.acelerazg.repositories.SkillRepository

class SkillService {

    SkillRepository skillRepository

    SkillService() {
        this(new SkillRepository())
    }

    SkillService(SkillRepository skillRepository) {
        this.skillRepository = skillRepository
    }

    List<Skill> findAll() {
        return skillRepository.findAll()
    }

    Skill create(String skill) {
        skill = formatSkill(skill)
        Skill existing = skillRepository.findByName(skill)
        if (existing) {
            println("[AVISO]: Esta competência " + skill.toUpperCase() + " já existe em nossa base de dados!")
            return null
        }
        Skill created = skillRepository.createByName(skill)
        return created
    }

    void registerAList(String skillsList) {
        List<String> skills = skillsList.split(", ")
        skills.forEach { it -> create(it) }
    }

    boolean existsSkill(String skill) {
        skill = formatSkill(skill)
        Skill c = skillRepository.findByName(skill)
        return c != null
    }

    Skill update(String existingSkill, String updatedSkill) {
        updatedSkill = formatSkill(updatedSkill)
        existingSkill = formatSkill(existingSkill)
        if (existsSkill(updatedSkill)) {
            println("[AVISO]: Essa competência já existe em nossa base de dados!")
            return null
        }
        Skill found = skillRepository.findByName(existingSkill)
        found.name = updatedSkill
        return skillRepository.updateCompetenciaById(found)
    }

    void delete(String skill) {
        skill = formatSkill(skill)
        Skill existing = skillRepository.findByName(skill)
        skillRepository.deleteCompetenciaById(existing)
    }

    List<String> findSkillsByCandidate(String candidateId) {
        return skillRepository.findSkillsByCandidate(candidateId)
    }

    void addSkillsToCandidate(String candidateId, String skills) {
        addSkills(skills) { skill ->
            skillRepository.addSkillsToCandidate(candidateId, skill.id)
        }
    }

    void removeSkillsFromCandidate(String candidateId) {
        skillRepository.removeSkillsFromCandidate(candidateId)
    }

    List<String> findSkillsByCompany(String companyId) {
        return skillRepository.findSkillsByCompany(companyId)
    }

    void addSkillsToCompany(String companyId, String skills) {
        addSkills(skills) { skill ->
            skillRepository.addSkillsToCompany(companyId, skill.id)
        }
    }

    void removeSkillsFromCompany(String companyId) {
        skillRepository.removeSkillsFromCompany(companyId)
    }

    List<String> findSkillsByJob(String jobId) {
        return skillRepository.findSkillsByJob(jobId)
    }

    void addSkillsToJob(String jobId, String skills) {
        addSkills(skills) { skill ->
            skillRepository.addSkillsToJob(jobId, skill.id)
        }
    }

    void removeSkillsFromJob(String jobId) {
        skillRepository.removeSkillsFromJob(jobId)
    }

    private void addSkills(String skillList, Closure<Void> function) {
        List<String> skills = skillList.split(", ")
        skills.forEach { name ->
            String formatted = formatSkill(name)
            Skill skill = skillRepository.findByName(formatted)

            if (!skill) {
                skill = new Skill(UUID.randomUUID().toString(), formatted)
                skillRepository.createFullSkill(skill.id, skill.name)
            }
            function.call(skill)
        }
    }

    private String formatSkill(String skill) {
        skill = skill.trim()
        skill = skill[0].toUpperCase() + skill.substring(1).toLowerCase()
        return skill
    }

}
