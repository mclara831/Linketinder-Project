package org.acelerazg.services.skill

import org.acelerazg.models.Skill
import org.acelerazg.repositories.SkillRepository

class SkillService implements ISkillService {

    SkillRepository skillRepository

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
        return skillRepository.updateById(found)
    }

    void delete(String skill) {
        skill = formatSkill(skill)
        Skill existing = skillRepository.findByName(skill)
        skillRepository.deleteById(existing)
    }

    final String formatSkill(String skill) {
        skill = skill.trim()
        skill = skill[0].toUpperCase() + skill.substring(1).toLowerCase()
        return skill
    }

}
